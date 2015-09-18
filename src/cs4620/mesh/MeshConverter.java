package cs4620.mesh;

import java.util.ArrayList;

import egl.NativeMem;
import egl.math.Vector3;
import egl.math.Vector3i;

/**
 * Performs Normals Reconstruction Upon A Mesh Of Positions
 * @author Cristian
 *
 */
public class MeshConverter {
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Have Sharp Creases
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Lie Normal To Faces
	 */
	public static MeshData convertToFaceNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();

		// Notice
		System.out.println("This Feature Has Been Removed For The Sake Of Assignment Consistency");
		System.out.println("This Feature Will Be Added In A Later Assignment");
		
		// Please Do Not Fill In This Function With Code
		
		// After You Turn In Your Assignment, Chuck Norris Will
		// Substitute This Function With His Fiery Will Of Steel
		
		// TODO#A1 SOLUTION START
				
		// #SOLUTION END

		return data;
	}
	/**
	 * Reconstruct A Mesh's Normals So That It Appears To Be Smooth
	 * @param positions List Of Positions
	 * @param tris List Of Triangles (A Group Of 3 Values That Index Into The Positions List)
	 * @return A Mesh With Normals That Extrude From Vertices
	 */
	public static MeshData convertToVertexNormals(ArrayList<Vector3> positions, ArrayList<Vector3i> tris) {
		MeshData data = new MeshData();
		// #A1 SOLUTION START
		
		int vCount= positions.size();
		int tCount= tris.size();
		data.vertexCount = vCount;
		data.indexCount = tCount * 3;
		// Create Storage Spaces
		data.positions = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.uvs = NativeMem.createFloatBuffer(data.vertexCount * 2);
		data.normals = NativeMem.createFloatBuffer(data.vertexCount * 3);
		data.indices = NativeMem.createIntBuffer(data.indexCount);
		//duplicate positions
		for (int i=0;i<vCount;i++){
			data.positions.put(new float[]{
					positions.get(i).x, positions.get(i).y, positions.get(i).z
			});
		}
		//duplicate indices
		for (int i=0;i<tCount;i++){
			data.indices.put(tris.get(i).x);
			data.indices.put(tris.get(i).y);
			data.indices.put(tris.get(i).z);
		}
		//duplicate tris
		float[][] vnormal=new float[vCount][3];
		for (int i = 0; i < vCount; i++){
			for (int j = 0; j < 3; j++){
				vnormal[i][j]=0;
			}		
		}
	
		// calculate the normal of the triangles
		for (int i = 0; i < tCount; i++){	
			float[] va= {positions.get(tris.get(i).x).x , positions.get(tris.get(i).x).y , positions.get(tris.get(i).x).z};
			float[] vb= {positions.get(tris.get(i).y).x , positions.get(tris.get(i).y).y , positions.get(tris.get(i).y).z};
			float[] vc= {positions.get(tris.get(i).z).x , positions.get(tris.get(i).z).y , positions.get(tris.get(i).z).z};

			
			if (va.length != 3 || vb.length != 3 || vc.length != 3) {
				System.out.println("Input argument should be 3-dimension vector");
				return null;
			}
			float[] vab = new float[] {vb[0]-va[0], vb[1]-va[1], vb[2]-va[2]};
			float[] vac = new float[] {vc[0]-va[0], vc[1]-va[1], vc[2]-va[2]};
			float[] n = new float[] {
					vab[1]*vac[2]-vab[2]*vac[1], 
					vab[2]*vac[0]-vab[0]*vac[2], 
					vab[0]*vac[1]-vab[1]*vac[0]
			};
			
			float mode = (float) Math.sqrt(n[0]*n[0]+n[1]*n[1]+n[2]*n[2]);	
			// normal of the ith triangle.
			vnormal[tris.get(i).x][0] = vnormal[tris.get(i).x][0] + n[0]/mode ;
			vnormal[tris.get(i).x][1] = vnormal[tris.get(i).x][1] + n[1]/mode ;
			vnormal[tris.get(i).x][2] = vnormal[tris.get(i).x][2] + n[2]/mode ;
			
			vnormal[tris.get(i).y][0] = vnormal[tris.get(i).y][0] + n[0]/mode ;
			vnormal[tris.get(i).y][1] = vnormal[tris.get(i).y][1] + n[1]/mode ;
			vnormal[tris.get(i).y][2] = vnormal[tris.get(i).y][2] + n[2]/mode ;

			vnormal[tris.get(i).z][0] = vnormal[tris.get(i).z][0] + n[0]/mode ;
			vnormal[tris.get(i).z][1] = vnormal[tris.get(i).z][1] + n[1]/mode ;
			vnormal[tris.get(i).z][2] = vnormal[tris.get(i).z][2] + n[2]/mode ;
			
		}
		// add the normal of the ith triangle to the vertices' normals it contains
		for (int i = 0; i < vCount; i++){
				float m = (float) Math.sqrt(vnormal[i][0]*vnormal[i][0]+vnormal[i][1]*vnormal[i][1]+vnormal[i][2]*vnormal[i][2]);
				data.normals.put(new float[]{
						vnormal[i][0]/m, vnormal[i][1]/m, vnormal[i][2]/m
				});		
			
		}		
		// #SOLUTION END
		return data;
	}
}













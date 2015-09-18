package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Sphere Mesh
 * @author Siyu Zhu (sz432)
 * @author Ziyu Dong (zd95)
 *
 */
public class MeshGenSphere extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		// #A1 SOLUTION START

		// Calculate Vertex And Index Count
		int m = opt.divisionsLatitude;   // division for latitude
		int n = opt.divisionsLongitude;   // division for longitude
		outData.vertexCount = (n+1)*(m+1);  //only the two poles and the East/West 180degree points are duplicated
		outData.indexCount =  n*m*6;
		
		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);	
		
		// Create The Vertices
	
		for (int i = 0; i< m + 1 ;i++){
			for (int t = 0; t < n + 1; t++){
				outData.positions.put(new float[]{
						(float) Math.sin(Math.PI/m * i)* (float) Math.sin(Math.PI/n * 2 * t),(float) Math.cos(Math.PI/m * i), (float) Math.sin(Math.PI/m * i) * (float) Math.cos(Math.PI/n *t *2 )
				});
			};
		};
		//Add normals 
		for (int i = 0; i< m + 1 ;i++){
			for (int t = 0; t < n + 1; t++){
				outData.normals.put(new float[]{
						(float) Math.sin(Math.PI/m * i)* (float) Math.sin(Math.PI/n * 2 * t),(float) Math.cos(Math.PI/m * i), (float) Math.sin(Math.PI/m * i) * (float) Math.cos(Math.PI/n *t *2 )
				});
			}
		}	// For sphere, normal = coordinates  
		//Add uvs
		for (int i = 0; i< m + 1 ;i++){
			for (int t = 0; t < n + 1; t++){
				outData.uvs.put(new float[]{
						(float) t / (float) n , 1-(float) i / (float) m 
				});
			};
		};	
		// Create The Indices
		for (int rc = 0; rc< m; rc++){
			for (int t = 0; t < n; t++ ){
				outData.indices.put( rc*(n+1) + t );
				outData.indices.put( (rc+1)*(n+1) + t);
				outData.indices.put((rc+1)*(n+1) + 1 + t);
				outData.indices.put( rc*(n+1) + t);
				outData.indices.put( (rc+1)*(n+1) + 1 + t);
				outData.indices.put( rc*(n+1) + 1 + t);
			}
		}// put indices by rectangle 
		// #SOLUTION END
	}
}

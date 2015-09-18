package cs4620.mesh.gen;

import cs4620.mesh.MeshData;
import egl.NativeMem;

/**
 * Generates A Cylinder Mesh
 * @author Siyu Zhu (sz432)
 * @author Ziyu Dong (zd95)
 */
public class MeshGenCylinder extends MeshGenerator {
	@Override
	public void generate(MeshData outData, MeshGenOptions opt) {
		
		//#A1 SOLUTION START

		//----------------Common Algorithm
		
		// Calculate Vertex And Index Count
		int m = 3;  // Cylinder divided to 3 parts by altitude _ upper and lower round and side face
		int n = opt.divisionsLongitude;	  // longitude division set to n
		
		outData.vertexCount = 2*m*(n+1);   //duplicate the points of upper and lower round for n+1 time      
		outData.indexCount =  n*m*6;                   
		
		// Create Storage Spaces
		outData.positions = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.uvs = NativeMem.createFloatBuffer(outData.vertexCount * 2);
		outData.normals = NativeMem.createFloatBuffer(outData.vertexCount * 3);
		outData.indices = NativeMem.createIntBuffer(outData.indexCount);	

		//Create positions for Vertices
		for (int i = 0; i< m ;i++){		
				for (int t = 0; t < n + 1; t++){
					outData.positions.put(new float[]{
							(float) Math.sin(Math.PI/n * t *2) *  (float) Math.signum( i % 3 ) , (float) Math.signum(1.5 - (double) i ), (float) Math.cos(Math.PI/n * t * 2) * (float) Math.signum( i % 3 )							
					}); //signum used to distinguish circumstances among 3 faces
				}      
				for (int t = 0;t <n + 1; t++){
					outData.positions.put(new float[]{
					(float) Math.sin(Math.PI/n * t *2) *  (float) Math.signum( (i+1) % 3 ) , (float) Math.signum(1.5 - (double) (i+1) ), (float) Math.cos(Math.PI/n * t * 2) * (float) Math.signum( (i+1) % 3 )
				});
				}
		}//Put the positions by Rectangles along the longitude
			
		// Add Normals For 6 Faces
		for (int i = 0; i< 2 ;i++){	
			for (int t = 0; t < n + 1; t++){
				outData.normals.put(new float[]{
						0, 1, 0
				});
			}
		}// Upper round
	
		for (int i = 0; i< 2 ;i++){	
			for (int t = 0; t < n + 1; t++){
				outData.normals.put(new float[]{
					(float) Math.sin(Math.PI/n * t *2), 0, (float) Math.cos(Math.PI/n * t *2)
				});
			}
		}//Side face
		
		for (int i = 0; i< 2 ;i++){	
			for (int t = 0; t < n + 1; t++){
				outData.normals.put(new float[]{
					0, -1 ,0
				});
			}
		} //Lower round 
		
		
		// Add UV Coordinates		
		for (int t = 0; t < n + 1; t++){
			outData.uvs.put(new float[]{
					0.75f, 0.75f
										});
		}//the center of the upper round 	
		for (int t = 0; t < n + 1; t++){
			outData.uvs.put(new float[]{
					0.75f + (float) (Math.sin(Math.PI/n * t *2)* 0.25 ),  0.75f - (float)( Math.cos(Math.PI/n * t *2)*0.25)
					});
		}//the vertices on the rim of the upper round	
		for (int t = 0; t < n + 1; t++){
			
			outData.uvs.put(new float[]{
					(float) t/(float)n  ,  0.5f
			});	
		}//the upper rim of the side cylinder 
		for (int t = 0; t < n + 1; t++){			
				outData.uvs.put(new float[]{
						(float) t/(float)n , 0 
				});
			
		}//the lower rim of the side cylinder 
		for (int t = 0; t < n + 1; t++){
			outData.uvs.put(new float[]{
					0.25f- (float)(Math.sin(Math.PI/n * t *2)*0.25), 0.75f - (float) (Math.cos(Math.PI/n * t *2)*0.25)
			});
		}//the vertices on the rim of the lower round
		for (int t = 0; t < n + 1; t++){
			outData.uvs.put(new float[]{
					0.25f, 0.75f
										});
		}//the center of the lower round 
	
		// Create The Indices
		for (int rc = 0; rc< 2*m; rc++){
			if (rc%2 == 0){                   // 1 rectangle per 2 rc count
				for (int t = 0; t < n; t++ ){
					outData.indices.put( rc*(n+1) + t );
					outData.indices.put( (rc+1)*(n+1) + t);
					outData.indices.put((rc+1)*(n+1) + 1 + t);
					outData.indices.put( rc*(n+1) + t);
					outData.indices.put( (rc+1)*(n+1) + 1 + t);
					outData.indices.put( rc*(n+1) + 1 + t);
				}
			}
		}// put the indices by Rectanges(2 triangles per time), the start and end points are duplicated.
		
		// #SOLUTION END
	}
}

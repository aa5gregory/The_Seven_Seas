package game;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import sage.model.loader.OBJLoader;
import sage.scene.TriMesh;

public class CreateAvatar extends TriMesh {
	private Matrix3D dimondMatrix = this.getLocalTranslation();
	private TriMesh avatar;
	private static float[] vrt = new float[]{0,1,0,-1,-1,1,1,-1,1,1,-1,-1,-1,-1,-1,0,-3,0};
	private static float[] color = new float[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
	private static int[] triangles = new int[]{0,1,2,0,2,3,0,3,4,0,4,1,1,4,2,4,3,2,5,1,2,5,2,3,5,3,4,5,1,4};
	private FloatBuffer vertBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrt);
	private FloatBuffer colorBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(color);
	private IntBuffer triangleBuf = com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
	private boolean init = false;
	private int score = 0;
	
	public CreateAvatar(GameTwo g, int x, int y, int z){

		//this.setVertexBuffer(vertBuf);
		//this.setColorBuffer(colorBuf);
		//this.setIndexBuffer(triangleBuf);
		OBJLoader loader = new OBJLoader();
		avatar = loader.loadModel("./src/objects/boat.obj");
		//Matrix3D dimondMatrix = this.getLocalTranslation();
		dimondMatrix.translate(x, y, z);
		dimondMatrix.scale(0.3,0.3,0.3);
		avatar.setLocalTranslation(dimondMatrix);
		avatar.updateLocalBound();
		avatar.updateWorldBound();
	}
	public boolean getInit(){
		return init;
	}
	public void setInit(boolean init){
		this.init = init;
	}
	public int getScore(){
		return score;
	}
	public void incScore(){
		score = score + 1;
	}
	public CreateAvatar getAvatar(){
		return (CreateAvatar)avatar;
	}
}

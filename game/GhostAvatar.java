package game;

import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.UUID;

import sage.scene.TriMesh;

public class GhostAvatar extends TriMesh {
	private Matrix3D dimondMatrix = this.getLocalTranslation();
	private static float[] vrt = new float[]{0,1,0,-1,-1,1,1,-1,1,1,-1,-1,-1,-1,-1,0,-3,0};
	private static float[] color = new float[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
	private static int[] triangles = new int[]{0,1,2,0,2,3,0,3,4,0,4,1,1,4,2,4,3,2,5,1,2,5,2,3,5,3,4,5,1,4};
	private FloatBuffer vertBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrt);
	private FloatBuffer colorBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(color);
	private IntBuffer triangleBuf = com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
	private boolean init = false;
	private int score = 0;
	private UUID id;
	
	public GhostAvatar(GameTwo g, UUID id, int x, int y, int z){

		this.id = id;
		this.setVertexBuffer(vertBuf);
		this.setColorBuffer(colorBuf);
		this.setIndexBuffer(triangleBuf);
		
		//Matrix3D dimondMatrix = this.getLocalTranslation();
		dimondMatrix.translate(x, y, z);
		dimondMatrix.scale(0.3,0.3,0.3);
		this.setLocalTranslation(dimondMatrix);
		this.updateLocalBound();
		this.updateWorldBound();
		g.addToSkyBoxScene(this);
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
	public GhostAvatar getAvatar(){
		return this;
	}
	public UUID getID(){
		return id;
	}
}

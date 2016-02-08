package game;

import graphicslib3D.Matrix3D;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import sage.event.IEventListener;
import sage.event.IGameEvent;
import sage.scene.TriMesh;

public class CreateTreasureBox extends TriMesh implements IEventListener {
	
	private static float[] vrt = new float[]{-1,1,-1,1,1,-1,-1,-1,-1,1,-1,-1,-1,1,1,1,1,1,-1,-1,1,1,-1,1};
	private static float[] color = new float[]{0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1};
	private static int[] triangles = new int[]{0,1,2,1,2,3,4,5,6,5,6,7,0,4,6,2,6,0,1,5,7,3,7,1,6,7,2,2,3,7,4,5,0,0,1,5};
	private float scaleX = 1.1f;
	private float scaleY = 1.2f;
	private float scaleZ = 1.1f;
	
	public CreateTreasureBox(GameTwo g, int x, int y, int z){
		
		FloatBuffer vertBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(vrt);
		FloatBuffer colorBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(color);
		IntBuffer triangleBuf = com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);
		Matrix3D treasureMatrix = this.getLocalTranslation();
		this.setVertexBuffer(vertBuf);
		this.setColorBuffer(colorBuf);
		this.setIndexBuffer(triangleBuf);
		treasureMatrix.scale(scaleX, scaleY, scaleZ);
		treasureMatrix.translate(x, y, z);
		this.setLocalTranslation(treasureMatrix);
		g.addGameWorldObject(this);
		
	}
	
	
	public boolean handleEvent(IGameEvent arg0) {
		
		return false;
	}	
	protected void reScale(){
		Matrix3D treasureMatrix = this.getLocalTranslation();
		scaleX = scaleX * 1.001f;
		scaleY = scaleY * 1.001f;
		scaleZ = scaleZ * 1.001f;
		treasureMatrix.scale(scaleX, scaleY, scaleZ);
		this.setLocalTranslation(treasureMatrix);
	}
}


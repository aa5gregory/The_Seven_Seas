package game;

import graphicslib3D.Matrix3D;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import sage.event.IEventListener;
import sage.event.IGameEvent;
import sage.scene.SceneNode;
import sage.scene.TriMesh;

public class BoxOfDoom extends TriMesh {
	
	private static float[] vrt = new float[]{-1,1,-1,1,1,-1,-1,-1,-1,1,-1,-1,-1,1,1,1,1,1,-1,-1,1,1,-1,1};
	private static float[] color = new float[]{0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1,0.6f,0.6f,0.6f,1};
	private static float[] colorFlash = new float[]{0.878f, 1.000f, 1.000f,1,0.878f, 1.000f, 1.000f, 1,0.878f,1.000f, 1.000f,1,0.878f, 1.000f, 1.000f,1,0.878f, 1.000f, 1.000f,1,0.878f, 1.000f, 1.000f,1,0.878f, 1.000f, 1.000f,1,0.878f, 1.000f, 1.000f,1};
	private static int[] triangles = new int[]{0,1,2,1,2,3,4,5,6,5,6,7,0,4,6,2,6,0,1,5,7,3,7,1,6,7,2,2,3,7,4,5,0,0,1,5};
	private float scaleX = 0.2f;
	private float scaleY = 0.2f;
	private float scaleZ = 0.2f;
	private boolean isFlash = true;
	
	public BoxOfDoom(GameTwo g, int x, int y, int z){
		
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
		returnThis();
		
	}
	private SceneNode returnThis(){
		return this;
	}
	public void changeColor(){
		if(isFlash){
			FloatBuffer colorBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(colorFlash);
			this.setColorBuffer(colorBuf);
			isFlash = false;
		}else{
			FloatBuffer colorBuf = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(color);
			this.setColorBuffer(colorBuf);
			isFlash = true;
		}
	}
}


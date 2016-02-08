package game;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;

import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import sage.model.loader.ogreXML.OgreXMLParser;
import sage.scene.Group;
import sage.scene.Model3DTriMesh;
import sage.scene.SceneNode;
import sage.scene.TriMesh;
import sage.scene.shape.Sphere;
import sage.model.loader.ogreXML.*;
import sage.scene.state.TextureState;
import sage.terrain.TerrainBlock;
import sage.texture.Texture;
import sage.texture.*;
import sage.renderer.IRenderer;
import sage.renderer.*;
import sage.display.*;
import sage.scene.state.*;

public class GhostNPC {
	private int id;
	
	private Sphere npc;
	//private Model3DTriMesh npc;
   TextureState narwhalTS;
   Model3DTriMesh nar;
   private  Group model;
	
	public GhostNPC(GameTwo g, int id, int x, int y, int z){

		this.id = id;
		OgreXMLParser loader = new OgreXMLParser();
		try
      {
			model = loader.loadModel("./animation/narwhal.mesh.xml", "./animation/narwhal.mtl", "./animation/narwhal.skeleton.xml");
		   model.updateGeometricState(0, true);
			java.util.Iterator<SceneNode> modelIterator = model.iterator();
			nar = (Model3DTriMesh) modelIterator.next();
		}
      catch (Exception e)
      {
			e.printStackTrace();
			System.out.println("Model did not load");
		}
		//this.updateLocalBound();
		//this.updateWorldBound();
		
		//npc = new Sphere();
		nar.updateLocalBound();
		nar.updateWorldBound();
		nar.translate(x, y, z);
       nar.rotate(90, new Vector3D(0.0f,1.0f,0.0f));
	    nar.scale(.05f, .05f, .05f);
		g.addGameWorldObject(nar);
      
      Texture narTexture = TextureManager.loadTexture2D("./materials/norwals.png");
      narTexture.setApplyMode(sage.texture.Texture.ApplyMode.Replace);
      narwhalTS = (TextureState) g.getDisplay().getRenderer().createRenderState(RenderState.RenderStateType.Texture);
      narwhalTS.setTexture(narTexture, 0);
      narwhalTS.setEnabled(true);
      nar.setRenderState(narwhalTS);
      nar.updateRenderStates();
     // nar.startAnimation("my_animation");
	}
	public void updateLocation(Vector3D pos){
		Vector3D hold = nar.getLocalTranslation().getCol(3);
		hold.setX(pos.getX());
		hold.setY(pos.getY());
		hold.setZ(pos.getZ());
		nar.getLocalTranslation().setCol(3, hold);
	}
	public Vector3D getLocation(){
		return nar.getLocalTranslation().getCol(3);
	}
	public Model3DTriMesh getNPC(){
		return nar;
	}
   
   public void update(float elapsedTimeMS)
   {
      nar.updateAnimation(elapsedTimeMS);
   }
}

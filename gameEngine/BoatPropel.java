package gameEngine;

import game.GameTwo;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.camera.ICamera;
import sage.scene.Controller;
import sage.scene.SceneNode;
import sage.scene.TriMesh;
import sage.terrain.TerrainBlock;

public class BoatPropel extends Controller{
	private	SceneNode avatar;
	private GameTwo game;
	private ICamera cam;
	private Speed speed;
	private TerrainBlock water;
	private TerrainBlock earth;
	
	
	public BoatPropel(SceneNode avatar, GameTwo game, Camera3PointController cam, TerrainBlock water, TerrainBlock earth, Speed speed){
		this.avatar = avatar;
		this.game = game;
		this.cam = cam.getCamera();
		this.speed = speed;
		//terrain
	    this.water = water;
	    this.earth = earth;
	}
	
	public void update(double time) {
		Vector3D rightAxis = cam.getViewDirection().normalize();
		Vector3D holdDir =  avatar.getWorldTranslation().getCol(3); 
		Vector3D finalDir = new Vector3D();
		finalDir = holdDir.add(rightAxis.mult(speed.getSpeed()));
		
		//Moves ship up and down
		float waterHeight = water.getHeight((float)finalDir.getX(),(float)finalDir.getZ());
	    float desiredHeight = waterHeight + (float)water.getOrigin().getY() + 0.5f;
	    
	    float earthHeight = earth.getHeight((float)finalDir.getX(),(float)finalDir.getZ());
	    
	    if(earthHeight > 0){
	    	speed.setSpeed(0.0f);
	    	holdDir = finalDir.add(rightAxis.mult(0.9));
	    	float holdEarthHeight = earth.getHeight((float)holdDir.getX(),(float)holdDir.getZ());
	    	if(holdEarthHeight <= 0){
	    		speed.changeSpeed();
	    	}
	    }
	   
		avatar.getLocalTranslation().setElementAt(0, 3, finalDir.getX());
		avatar.getLocalTranslation().setElementAt(1, 3, desiredHeight);
		avatar.getLocalTranslation().setElementAt(2, 3, finalDir.getZ());
		
		
		// Sends our only if connecte to server
		if(game.getIsConnectedToServer()){
			game.getThisClient().sendMoveMessage(avatar.getWorldTranslation().getCol(3));
		}

	}
}

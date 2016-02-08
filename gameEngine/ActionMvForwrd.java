package gameEngine;

import game.*;
//import graphicslib3D.Matrix3D;
//import graphicslib3D.Point3D;
//import graphicslib3D.Vector3D;
import net.java.games.input.Event;
//import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class ActionMvForwrd extends AbstractInputAction {
	//private GameTwo game;
	//private	SceneNode avatar;
	private Speed speed;
	//private Camera3PointController controller;
	//private ICamera cam;
	//private static final String wKey = "Event: component = W | value = 1.0";
	private static final String sKey = "Event: component = S | value = 1.0";
	private static final String Axis = "Y Axis";
	
	public ActionMvForwrd(SceneNode node, Speed speed, Camera3PointController camController, GameTwo g){
		//game = g;
		//avatar = node;
		this.speed = speed;
		//controller = camController;
		//cam = controller.getCamera();
	}
	
	
	public void performAction(float time, Event e) {
		/*
		Vector3D rightAxis = cam.getViewDirection().normalize();
		Vector3D dir =  avatar.getWorldTranslation().getCol(3); 
		Vector3D finalDir = new Vector3D();
		*/
		if(e.toString().equals(sKey) ||(e.getComponent().toString().equals(Axis) && e.getValue() > 0.25)){
			speed.changeSpeed();
		}
		/*
		if (e.toString().equals(wKey) || e.getValue() < -0.25){
			finalDir = dir.add(rightAxis.mult(speed.getSpeed()* time));
			move(dir, finalDir);
			resetCameraElevationAzmith();
		}
		*/
	}
	/*
	private void resetCameraElevationAzmith(){
		controller.setAzimuth(controller.getAzimuth(false), false);
		controller.setElevation(controller.getElevation(false), false);
	}
	private void move(Vector3D dir, Vector3D finalDir){
		float newX = (float)-dir.getX() + (float)finalDir.getX();
		float newY = (float)-dir.getY() + (float)dir.getY();
		float newZ = (float)-dir.getZ() + (float)finalDir.getZ();
		dir.setX(newX);
		dir.setY(newY);
		dir.setZ(newZ);
		avatar.translate(newX, newY, newZ);
	
		if(game.isServerAlive()){
			game.getThisClient().sendMoveMessage(dir);
		}
	}
	*/
}

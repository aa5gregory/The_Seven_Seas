package gameEngine;

import game.*;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class ActionLeftRight extends AbstractInputAction{

	private	SceneNode avatar;
	private float currentSpeed;
	private Camera3PointController controller;
	private GameTwo game;
	private ICamera cam;
	private static final String aKey = "Event: component = A | value = 1.0";
	private static final String dKey = "Event: component = D | value = 1.0";
	
	public ActionLeftRight(SceneNode node, float speed, Camera3PointController camController, GameTwo g){
		game = g;
		avatar = node;
		currentSpeed = speed;
		controller = camController;
		cam = controller.getCamera();
	}
	
	public void performAction(float time, Event e) {
		/*
		Vector3D rightAxis = cam.getRightAxis().normalize();
		Vector3D dir =  avatar.getLocalTranslation().getCol(3);
		Vector3D finalDir = new Vector3D();
		if(e.toString().equals(dKey)|| (e.getValue() > 0.25 && e.getComponent().toString().equals("X Axis"))){
			finalDir = dir.add(rightAxis.mult(currentSpeed * time));
			move(dir,finalDir);
			resetCameraElevationAzmith();
		}
		else if (e.toString().equals(aKey)||e.getValue() < -0.25){
			
			finalDir = dir.minus(rightAxis.mult(currentSpeed * time));
			move(dir, finalDir);
			resetCameraElevationAzmith();
		}
	}
	private void resetCameraElevationAzmith(){
		controller.setAzimuth(controller.getAzimuth(false), false);
		controller.setElevation(controller.getElevation(false), false);
		
	}
	private void move(Vector3D dir, Vector3D finalDir){
		float newX = (float)-dir.getX() + (float)finalDir.getX();
		float newY = (float)-dir.getY() + (float)finalDir.getY();
		float newZ = (float)-dir.getZ() + (float)finalDir.getZ();
		dir.setX(newX);
		dir.setY(newY);
		dir.setZ(newZ);
		avatar.translate(newX, newY, newZ);
		
		if(game.isServerAlive()){
			game.getThisClient().sendMoveMessage(dir);
		}
		*/
	}
}
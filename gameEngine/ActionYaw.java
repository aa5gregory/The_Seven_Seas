package gameEngine;

import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class ActionYaw extends AbstractInputAction {

	private Camera3PointController controller;
	private float rotAmount = 0.1f;
	private static final String leftKey = "Event: component = Left | value = 1.0";
	private static final String rightKey = "Event: component = Right | value = 1.0";
	
	public ActionYaw(Camera3PointController c){
		controller = c;
	}
	
	public void performAction(float time, Event e) {
		
		float camAzimuth = controller.getAzimuth(false);
		
		if(e.toString().equals(rightKey) || e.getValue() < -0.25){
			camAzimuth += -rotAmount;
			camAzimuth = camAzimuth % 360;
			controller.setAzimuth(camAzimuth,false);
			controller.getAvatar().rotate(-rotAmount, new Vector3D(0.0,1.0,0.0));
			
		} else if (e.toString().equals(leftKey) || (e.getValue() > 0.25 && e.getComponent().toString().equals("X Rotation"))){
			camAzimuth += rotAmount;
			camAzimuth = camAzimuth % 360;
			controller.setAzimuth(camAzimuth,false);
			controller.getAvatar().rotate(rotAmount, new Vector3D(0.0,1.0,0.0));
		}	
	}
}


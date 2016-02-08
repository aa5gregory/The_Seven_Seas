package gameEngine;


import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;


public class ActionPitch extends AbstractInputAction{
	private Camera3PointController controller;
	private static final String upKey = "Event: component = Up | value = 1.0";
	private static final String downKey = "Event: component = Down | value = 1.0";
	
	public ActionPitch (Camera3PointController controller){
		this.controller = controller;
	}
	
	public void performAction(float time, Event e) {
		float rotAmount;
		float camElevation = controller.getElevation(false);
		if(e.toString().equals(downKey) || (e.getValue() > 0.25 && e.getComponent().toString().equals("Y Rotation"))){
			rotAmount = -0.3f;
		} else if(e.toString().equals(upKey)||e.getValue() < -0.25){
			rotAmount = 0.3f;
		} else {
			rotAmount = 0.0f;
		}
		camElevation += rotAmount;
		if(camElevation >= 90){
			camElevation = 89;
		}else if(camElevation < -90) {
			camElevation = 0;
		}
		//camElevation = camElevation % 180;
		controller.setElevation(camElevation,false);	
	}
}

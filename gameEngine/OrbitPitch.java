package gameEngine;


import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;


public class OrbitPitch extends AbstractInputAction{
	private Camera3PointController controller;
	private static final String iKey = "Event: component = I | value = 1.0";
	private static final String kKey = "Event: component = K | value = 1.0";
	
	public OrbitPitch (Camera3PointController controller){
		this.controller = controller;
	}
	
	public void performAction(float time, Event e) {
		float rotAmount;
		float camElevation = controller.getElevation(true);
		if(e.toString().equals(kKey) || e.toString().equals("Event: component = Button 4 | value = 1.0")){
			rotAmount = -0.3f;
		} else if(e.toString().equals(iKey)||e.toString().equals("Event: component = Button 5 | value = 1.0")){
			rotAmount = 0.3f;
		} else {
			rotAmount = 0.0f;
		}
		camElevation += rotAmount;
		if(camElevation >= 90){
			camElevation = 89;
		}else if(camElevation < 0) {
			camElevation = 0;
		}
		//camElevation = camElevation % 180;
		controller.setElevation(camElevation,true);	
	}
}

package gameEngine;

import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;
import sage.scene.SceneNode;

public class OrbitAroundAction extends AbstractInputAction {
	
	private Camera3PointController controller;
	
	private static final String jKey = "Event: component = J | value = 1.0";
	private static final String ley = "Event: component = L | value = 1.0";
	
	public OrbitAroundAction(Camera3PointController controller){
		this.controller = controller;
		
	}
	public void performAction(float time, Event e) {
		float rotAmount ;
		float camAzimuth = controller.getAzimuth(true);
		if(e.toString().equals(jKey) || e.getValue() <  -0.25){
			rotAmount = -0.3f;
		} else if(e.toString().equals(ley)|| (e.getValue() > 0.25 && e.getValue() < 1.0)){
			rotAmount = 0.3f;
		} else {
			rotAmount = 0.0f;
		}
		camAzimuth +=rotAmount;
		camAzimuth = camAzimuth % 360;
		controller.setAzimuth(camAzimuth,true);
	}
}


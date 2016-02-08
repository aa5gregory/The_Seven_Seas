package gameEngine;

import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class OrbitUpandDown extends AbstractInputAction {
	
	private Camera3PointController controller;
	
	private static final String iKey = "Event: component = I | value = 1.0";
	private static final String kKey = "Event: component = K | value = 1.0";
	
	public OrbitUpandDown(Camera3PointController controller){
		this.controller = controller;
		
	}
	public void performAction(float time, Event e) {
		float rotAmount ;
		float camAzimuth = controller.getAzimuth(true);
		if(e.toString().equals(iKey) || e.getValue() <  -0.2){
			rotAmount = -0.3f;
		} else if(e.toString().equals(kKey)|| (e.getValue() > 0.2 && e.getValue() < 1.0)){
			rotAmount = 0.3f;
		} else {
			rotAmount = 0.0f;
		}
		camAzimuth +=rotAmount;
		camAzimuth = camAzimuth % 360;
		controller.setAzimuth(camAzimuth,true);
	}
}


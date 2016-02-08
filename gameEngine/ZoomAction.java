package gameEngine;

import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class ZoomAction extends AbstractInputAction {

	private Camera3PointController controller;
	private static final String qKey = "Event: component = Q | value = 1.0";
	private static final String eKey = "Event: component = E | value = 1.0";
	
	public ZoomAction(Camera3PointController controller){
		this.controller = controller;
	}
	public void performAction(float time, Event e) {
		float zoomAmount;
		float camDistFromTarget = controller.getCamDistFromTarget();
		if(camDistFromTarget >= 1.25 && camDistFromTarget <= 7){
			if(e.toString().equals(qKey) || e.toString().equals("Event: component = Button 0 | value = 1.0")){
				zoomAmount = -0.01f;
			} else if(e.toString().equals(eKey)||e.toString().equals("Event: component = Button 1 | value = 1.0")){
				zoomAmount = 0.01f;
			} else {
				zoomAmount = 0.0f;
			}
			camDistFromTarget +=zoomAmount;
		}
		if(camDistFromTarget > 7){
			camDistFromTarget = 7f;
		}
		if(camDistFromTarget < 1.25){
			camDistFromTarget = 1.25f;
		}
		controller.setCamDistFromTarget(camDistFromTarget);
	}
}

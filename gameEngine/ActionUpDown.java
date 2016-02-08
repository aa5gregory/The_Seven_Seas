package gameEngine;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import net.java.games.input.Event;
import sage.camera.ICamera;
import sage.input.action.AbstractInputAction;

public class ActionUpDown extends AbstractInputAction {

	private ICamera camera;
	private static final String qKey = "Event: component = Q | value = 1.0";
	private static final String eKey = "Event: component = E | value = 1.0";
	
	public ActionUpDown(ICamera c){
		camera = c;
	}
	
	public void performAction(float time, Event e) {
		
		float moveAmount = (float) 0.006;
		
		Vector3D upAxis = camera.getUpAxis().normalize();
		Vector3D curLocVector = new Vector3D(camera.getLocation());
		Vector3D newLocVector = new Vector3D();
		
		if(e.toString().equals(qKey)||e.getValue() < -0.25){
			newLocVector = curLocVector.minus(upAxis.mult(moveAmount * time));
		}
		else if (e.toString().equals(eKey)|| (e.getValue() > 0.25 && e.getValue() < 1.0)){
			newLocVector = curLocVector.add(upAxis.mult(moveAmount * time));
		}
		else {
			newLocVector = curLocVector;
		}
		double newX = newLocVector.getX();
		double newY = newLocVector.getY();
		double newZ = newLocVector.getZ();
		Point3D newCamLoc = new Point3D(newX,newY,newZ);
		camera.setLocation(newCamLoc);
	}
}

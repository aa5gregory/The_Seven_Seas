package gameEngine;

import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class Speed  {

	private float speed =  0.0f;
	
	public float getSpeed(){
		return speed;
	}
	public void setSpeed(float newSpeed){
		speed = newSpeed;
	}
	public void changeSpeed(){
		boolean speedNotChanged = true;
		if(speed == 0.0f && speedNotChanged){
			speed = (0.02f);
			speedNotChanged = false;
		}
		if(speed == 0.02f && speedNotChanged){
			speed = (0.06f);
			speedNotChanged = false;
		}
		if(speed == 0.06f && speedNotChanged){
			speed = (0.0f);
			speedNotChanged = false;
		}
	}
}

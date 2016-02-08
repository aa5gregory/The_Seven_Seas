package game;

import sage.scene.Controller;
import sage.scene.Group;
import sage.scene.SceneNode;

public class ChangeColor extends Controller {
	private Group colorGroup;
	private double cycleTime = 1000;
	private double totalTime;
	
	public ChangeColor(Group color){
		this.colorGroup = color;
	}
	public void update(double time) {
		totalTime = totalTime + time;
		if(totalTime > cycleTime){
			totalTime = 0.0;
			for(SceneNode s: colorGroup){
				if(s instanceof BoxOfDoom){
					BoxOfDoom doom = (BoxOfDoom) s;
					doom.changeColor();
				}
			}
		}
	}
}

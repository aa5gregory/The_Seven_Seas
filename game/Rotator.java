package game;

import graphicslib3D.Vector3D;
import sage.scene.Controller;
import sage.scene.Group;
import sage.scene.SceneNode;

public class Rotator extends Controller {
	private double rotation = .09f;
	private Group group;
	
	public Rotator(Group group){
		this.group = group;
	}
	
	public void update(double time) {
		float roatationAmount = (float) (rotation * time);
		for(SceneNode s: group){
			s.rotate(roatationAmount, new Vector3D(0,1,0));
		}
	}

}

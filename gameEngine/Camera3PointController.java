package gameEngine;

import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.*;
import graphicslib3D.Matrix3D;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.camera.ICamera;
import sage.input.IInputManager;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.SceneNode;
import sage.util.MathUtils;

public class Camera3PointController {
	private ICamera cam;
	private SceneNode target;
	private float camAzimuth;
	private float lastAzimuth = 180;
	private float lastElevation = 20.0f;
	private float camElevation;
	private float camDistFromTarget;
	private Point3D targPos;
	private Vector3D worldUpVec;
	
	public Camera3PointController(ICamera cam, SceneNode target){
		this.cam = cam;
		this.target = target;
		worldUpVec = new Vector3D(0,1,0);
		camDistFromTarget = 1.4f;
		camAzimuth = 180;
		camElevation = 18.0f;
		update(0.0f);
	}
	public void update(float time){
		updateTarget();
		updateCameraPosition();
		cam.lookAt(targPos, worldUpVec);
	}
	private void updateTarget(){
		targPos = new Point3D(target.getWorldTranslation().getCol(3));
	}
	private void updateCameraPosition(){
		double theta = camAzimuth;
		double phi = camElevation;
		double r = camDistFromTarget;
		Point3D relativePos = MathUtils.sphericalToCartesian(theta,phi,r);
		Point3D finalCamLoc = relativePos.add(targPos);
		cam.setLocation(finalCamLoc);
	}
	public float getAzimuth(boolean isOrbit){
		if(isOrbit){
			return  camAzimuth;
		}else{
			return lastAzimuth;
		}
	}
	protected void setAzimuth(float azimuth, boolean isOrbit){
		if(isOrbit){
			camAzimuth = azimuth;
		}else{
			camAzimuth = azimuth;
			lastAzimuth = azimuth; 
		}
	}
	
	protected float getElevation(boolean isElevated){
		if(isElevated){
			return  camElevation;
		}else{
			return lastElevation;
		}
	}
	protected void setElevation(float elevation, boolean isElevated){
		if(isElevated){
			camElevation = elevation;
		}else{
			camElevation = elevation;
			lastElevation = elevation; 
		}
	}
	
	protected float getCamDistFromTarget(){
		return camDistFromTarget;
	}
	protected void setCamDistFromTarget(float distance){
		camDistFromTarget = distance;
	}
	protected ICamera getCamera(){
		return  cam;
	}
	public SceneNode getAvatar(){
		return target;
	}
}
	

package game;

import graphicslib3D.Point3D;

public class NPC {
	private Point3D location = new Point3D();
	private boolean stopRunning = false;
	private int bound = 3000;
	private int count = 0;
	private boolean inc = true;
	
	public NPC(int x, int y, int z ){
		location.setX(x);
		location.setY(y);
		location.setZ(z);
	}
	public void updateLocation(){
		/*if(stopRunning ){
			if(inc){
				location.setX(location.getX()+.01f);
				location.setZ(location.getZ()+.01f);
				count++;
				if(count == bound){
					inc = false;
				}
			}else{
				location.setX(location.getX()-.01f);
				location.setZ(location.getZ()-.01f);
				count--;
				if(count == 0){
					inc = true;
				}
			}
		}*/
	}
	public Point3D getLocation(){
		return location;
	}
	public void stopRunning(){
		if(inc){
			location.setX(location.getX()+.01f);
			location.setZ(location.getZ()+.01f);
			count++;
			if(count == bound){
				inc = false;
			}
		}else{
			location.setX(location.getX()-.01f);
			location.setZ(location.getZ()-.01f);
			count--;
			if(count == 0){
				inc = true;
			}
		}
	}
	public void runTowardAvatar(){
		
	}
}

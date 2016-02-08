package gameEngine;

import java.util.ArrayList;

import game.GameTwo;
import game.GhostNPC;
import game.NPC;
import graphicslib3D.Vector3D;

public class NPCcontroller {
	private GameTwo game;
	private ArrayList<NPC> NPClist = new ArrayList<NPC>();
	private boolean nearFlag = false;
	private int radius = 10;
   private GhostNPC g;
	
	public NPCcontroller(){
		
	}
	public void updateNPCs(){
		for (int i = 0; i<NPClist.size(); i++){
			NPClist.get(i).updateLocation();
		}
	}
	public void setupNPCs(){
		NPC hold = new NPC(10,1,10);
		NPClist.add(hold);
	}
	public int getNumOfNPCs(){
		return NPClist.size();
	}
	public NPC getNPC(int i){
		return NPClist.get(i);
	}
	public void setNearFlag(boolean flag){
		nearFlag = flag;
	}
	public boolean getNearFlag(){
		return nearFlag;
	}
	public void sendAvatarPos(Vector3D pos){
		NPC npc = NPClist.get(0);
		if(pos.getX() > npc.getLocation().getX()-radius){
			npc.getLocation().setX(npc.getLocation().getX() + 1);
			
		}
		if(pos.getX() < npc.getLocation().getX()+radius){
			npc.getLocation().setX(npc.getLocation().getX() - 1);
			
		}
		if(pos.getZ() > npc.getLocation().getZ()-radius){
			npc.getLocation().setZ(npc.getLocation().getZ() + 1);
			
		}
		if(pos.getZ() < npc.getLocation().getZ()+radius){
			npc.getLocation().setZ(npc.getLocation().getZ() - 1);
			
		}
	}
}

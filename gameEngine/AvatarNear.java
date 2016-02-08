package gameEngine;

import game.NPC;
import graphicslib3D.Point3D;
import sage.ai.behaviortrees.BTCondition;

public class AvatarNear extends BTCondition {
	private TCPGameServer server;
	private NPCcontroller npcc;
	private NPC npc;
	private long lastUpdateTime;
	
	public AvatarNear(TCPGameServer s, NPCcontroller c, NPC n, boolean toNegate){
		super(toNegate);
		server = s;
		npcc = c;
		npc = n;
		lastUpdateTime = System.nanoTime();
	}
	@Override
	protected boolean check() {
		Point3D npcPoint = new Point3D(npc.getLocation().getX(),npc.getLocation().getY(),npc.getLocation().getZ());
		server.sendCheckForAvatarNear();
		return npcc.getNearFlag();
	}
}

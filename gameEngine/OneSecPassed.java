package gameEngine;

import game.NPC;
import sage.ai.behaviortrees.BTCondition;

public class OneSecPassed extends BTCondition {
	private NPCcontroller npcc;
	private NPC npc;
	private long lastUpdateTime;
	
	public OneSecPassed(NPCcontroller c, NPC n, boolean toNegate){
		super(toNegate);
		npcc = c;
		npc = n;
		lastUpdateTime = System.nanoTime();
	}
	protected boolean check() {
		float elapsedTimeMilli = (System.nanoTime() - lastUpdateTime)/(1000000000.0f);
		if(elapsedTimeMilli>=0.010f){
			lastUpdateTime = System.nanoTime();
			npcc.setNearFlag(false);
			return true;
		}else{
			return false;
		}
	}
}

package gameEngine;

import game.NPC;
import sage.ai.behaviortrees.BTAction;
import sage.ai.behaviortrees.BTCondition;
import sage.ai.behaviortrees.BTStatus;

public class StopRunning extends BTAction {
	private NPC npc;
	
	public StopRunning(NPC n){
		npc = n;
	}
	protected BTStatus update(float elaspsedTime) {
		npc.stopRunning();
		return BTStatus.BH_SUCCESS;
	}
}

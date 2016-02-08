package gameEngine;

import game.NPC;
import sage.ai.behaviortrees.BTAction;
import sage.ai.behaviortrees.BTStatus;

public class RunTowardAvatar extends BTAction {
	private NPC npc;

	public RunTowardAvatar(NPC npc){
		this.npc = npc;
	}
	protected BTStatus update(float elapsedTime) {
		npc.runTowardAvatar();
		return BTStatus.BH_SUCCESS;
	}
}

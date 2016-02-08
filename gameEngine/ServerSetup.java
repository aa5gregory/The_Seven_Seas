package gameEngine;

import game.GameTwo;

import java.io.IOException;
import java.util.Scanner;

import sage.ai.behaviortrees.BTCompositeType;
import sage.ai.behaviortrees.BTSequence;
import sage.ai.behaviortrees.BehaviorTree;

public class ServerSetup {
	private  boolean validInput = false;
	private  long startTime;
	private  long lastUpdateTime;
	private  NPCcontroller npcCtrl;
	private  TCPGameServer myServer = null;
	private BehaviorTree bt = new BehaviorTree(BTCompositeType.SELECTOR);

	public ServerSetup(){
		Scanner in = new Scanner(System.in);
		while(!validInput){
			System.out.println("Would you like start a game server? If you would like to start a server type \"yes\" else type \"no\". ");
			String server = in.next();
			if(server.equals("yes")){
				//NPC
				startTime = System.nanoTime();
				lastUpdateTime = startTime;
				npcCtrl = new NPCcontroller();
				
				validInput = true;
				try {
					// Start Sever
					myServer = new TCPGameServer(6000,npcCtrl);
					// Start NPC Control Loop
					npcCtrl.setupNPCs();
					setupBehaviorTree();
					npcLoop();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(server.equals("no")){
				validInput = true;
			}
		}
	}
	public void npcLoop(){
		while(true){
			long frameStartTime = System.nanoTime();
			float elapMilSec = (frameStartTime - lastUpdateTime)/1000000000.0f;
			if(elapMilSec >=0.010f){
				lastUpdateTime = frameStartTime;
				npcCtrl.updateNPCs();
				myServer.sendNPCinfoToAll();
				bt.update(elapMilSec);
			}
			//Thread.yield();
		}
	}
	public void setupBehaviorTree(){
		bt.insertAtRoot(new BTSequence(10));
		bt.insertAtRoot(new BTSequence(20));
		bt.insert(10, new OneSecPassed(npcCtrl,npcCtrl.getNPC(0),false));
		bt.insert(10, new StopRunning(npcCtrl.getNPC(0)));
		bt.insert(20, new AvatarNear(myServer,npcCtrl,npcCtrl.getNPC(0),false));
		bt.insert(20, new RunTowardAvatar(npcCtrl.getNPC(0)));
	}
}

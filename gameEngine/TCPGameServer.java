package gameEngine;

import game.GameTwo;
import graphicslib3D.Vector3D;

import java.io.IOException;
import java.net.*;
import java.util.UUID;

import sage.networking.server.*;

public class TCPGameServer extends GameConnectionServer<UUID> {
	private NPCcontroller npcCtrl;
	
	public TCPGameServer(int localPort, NPCcontroller npcCtrl) throws IOException {
		super(localPort, ProtocolType.TCP);
		System.out.println("Server is alive!");
		this.npcCtrl = npcCtrl;
		
		
	}
	public void acceptClient(IClientInfo clientInfo, Object incomingObject){
		String message = (String) incomingObject;
		String[] messageTokens = message.split(",");
		
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("join")){
				UUID clientID = UUID.fromString(messageTokens[1]);
				addClient(clientInfo, clientID);
				sendJoinedMessage(clientID, true);
			}
		}
	}
	public void sendNPCinfoToAll(){
		for(int i=0; i<npcCtrl.getNumOfNPCs(); i++){
			try{
				String message = new String("mnpc," + Integer.toString(i));
				message += "," + ((int)npcCtrl.getNPC(i).getLocation().getX());
				message += "," + ((int)npcCtrl.getNPC(i).getLocation().getY());
				message += "," + ((int)npcCtrl.getNPC(i).getLocation().getZ());
				sendPacketToAll(message);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public void processPacket(Object object, InetAddress sernderIP, int sendPort){
		String message =(String) object;
		String[] messageTokens = message.split(",");
		
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("bye")){
				UUID clientID = UUID.fromString(messageTokens[1]);
				sendByeMessages(clientID);
				removeClient(clientID);
			}
		}
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("create")){
				UUID ghostID = UUID.fromString(messageTokens[1]);
				String [] pos = {messageTokens[2], messageTokens[3], messageTokens[4]};
				sendCreateMessages(ghostID, pos);
				sendWantsDetailsMessage(ghostID);
			}
		}
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("dsfr")){
				UUID asker = UUID.fromString(messageTokens[1]);
				UUID teller = UUID.fromString(messageTokens[2]);
				String [] pos = {messageTokens[3], messageTokens[4], messageTokens[5]};
				sendDetailsFor(asker, teller,pos);
			}
		}
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("move")){
				UUID clientID = UUID.fromString(messageTokens[1]);
				String [] pos = {messageTokens[2], messageTokens[3], messageTokens[4]};
				sendMoveMessages(clientID, pos);
			}
		}
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("needNPC")){
				UUID clientID = UUID.fromString(messageTokens[1]);
				sendNPCInfo(clientID);
			}
		}
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("collide")){
				
			}
		}
		if(messageTokens.length > 0){
			if (messageTokens[0].equals("avatar info")){
				int x = Integer.parseInt(messageTokens[1]);
				System.out.println("X is " + x);
			Vector3D pos = new Vector3D(Integer.parseInt(messageTokens[1]),Integer.parseInt(messageTokens[2]), Integer.parseInt(messageTokens[3]));
				npcCtrl.sendAvatarPos(pos);
			}
		}
	}
	public void sendWantsDetailsMessage(UUID clientID) {
		try{
			String message = new String("wants," + clientID.toString());
			forwardPacketToAll(message, clientID);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void sendDetailsFor(UUID asker, UUID teller, String[] pos){
		try{
			String message = new String("dsfr," + teller.toString());
			message += ","+pos[0];
			message += ","+pos[1];
			message += ","+pos[2];
			sendPacket(message, asker);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void sendJoinedMessage(UUID clientID, boolean sucess){
		try{
			String message = new String("joined,");
			if(sucess){
				message += "success";
			}else{
				message += "failure";
			}
			sendPacket(message, clientID);
		}catch(IOException e){
			e.printStackTrace();
		}
			
	}
	public void sendCreateMessages(UUID clientID, String[] pos){
		try{
			String message = new String("create," + clientID.toString());
			message += ","+pos[0];
			message += ","+pos[1];
			message += ","+pos[2];
			forwardPacketToAll(message, clientID);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void sendMoveMessages(UUID clientID, String[] pos){
		try{
			String message = new String("move," + clientID.toString());
			message += ","+pos[0];
			message += ","+pos[1];
			message += ","+pos[2];
			forwardPacketToAll(message, clientID);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void sendByeMessages(UUID clientID){
		try{
			String message = new String("bye," + clientID.toString());
			forwardPacketToAll(message, clientID);
			
		}catch(IOException e){
			
			e.printStackTrace();
		}
	}
	public void sendNPCInfo(UUID clientID ){
		try{
			for(int i=0; i<npcCtrl.getNumOfNPCs();i++){
				String message = new String("NPCinfo," + Integer.toString(i));
				message += "," + ((int)npcCtrl.getNPC(i).getLocation().getX());
				message += "," + ((int)npcCtrl.getNPC(i).getLocation().getY());
				message += "," + ((int)npcCtrl.getNPC(i).getLocation().getZ());
				sendPacket(message, clientID);
			}
		}catch(IOException e){
			
			e.printStackTrace();
		}
	}
	public void sendCheckForAvatarNear(){
		String message = new String("avatar near,");
		try {
			sendPacketToAll(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

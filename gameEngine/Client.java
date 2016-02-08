package gameEngine;

import game.*;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Vertex3D;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import sage.networking.client.GameConnectionClient;

public class Client extends GameConnectionClient {
	
	 private GameTwo game;
	 private UUID id;
	 private Vector<GhostAvatar> ghostAvatars;
	 private Vector<GhostNPC> ghostNPC;


	public Client(InetAddress remoteAddr, GameTwo game)throws IOException {
		 super(remoteAddr, 6000, ProtocolType.TCP);
		 this.game = game;
		 this.id = UUID.randomUUID();
		 this.ghostAvatars = new Vector<GhostAvatar>();
		 this.ghostNPC = new Vector<GhostNPC>();
		 System.out.println("Client is alive");
		
	}
	 public void processPacket(Object msg) { 
		 String message = (String) msg;
		 String[] messageTokens = message.split(",");
		 
		 if(messageTokens[0].equals("joined")) { // format: join, success or join, failure
			 
			 if(messageTokens[1].equals("success")){
				 System.out.println("I have joined the server");
				 game.setIsConnectedToServer(true);
				 sendCreateMessage(game.getPlayerPosition());
				
			 }
		 
			 if(messageTokens[1].equals("failure")){
				 System.out.println("I have not joined the server");
				 game.setIsConnectedToServer(false);
		 	}
		 }
		 
		 if(messageTokens[0].compareTo("bye") == 0) { 
			 UUID ghostID = UUID.fromString(messageTokens[1]);
			 ghostAvatars.removeElement(ghostID);
			 System.out.println("Ghost Removed");
			
		 }
		 
		 if (messageTokens[0].compareTo("dsfr") == 0 ) { 
			 System.out.println("DSFR");
			UUID ghostID = UUID.fromString(messageTokens[1]);
			 
			Vector3D ghostPosition = new Vector3D(); 
			ghostPosition.setX(Double.parseDouble(messageTokens[2])); 
			ghostPosition.setY(Double.parseDouble(messageTokens[3])); 
			ghostPosition.setZ(Double.parseDouble(messageTokens[4])); 
			
			createGhostAvatar(ghostID, ghostPosition);
			
		 }
		 
		 if(messageTokens[0].compareTo("create") == 0) { // etc….. 
			 
			UUID ghostID = UUID.fromString(messageTokens[1]); 
			Vector3D ghostPosition = new Vector3D(); 
			ghostPosition.setX(Double.parseDouble(messageTokens[2])); 
			ghostPosition.setY(Double.parseDouble(messageTokens[3])); 
			ghostPosition.setZ(Double.parseDouble(messageTokens[4])); 
			createGhostAvatar(ghostID, ghostPosition);
			
		 }
		 if(messageTokens[0].equals("wants")) { // etc….. 
			 System.out.println("Client Wants");
			 UUID asker = UUID.fromString(messageTokens[1]);
			 sendDetailsForMessage(asker, game.getPlayerPosition());
		 }
		 if(messageTokens[0].compareTo("move") == 0) { // etc….. }
			 UUID ghostID = UUID.fromString(messageTokens[1]); 
			 for(GhostAvatar g : ghostAvatars){
				 if(g.getID().toString().equals(ghostID.toString())){
				 	g.getLocalTranslation().setElementAt(0, 3, Double.parseDouble(messageTokens[2]));
				 	g.getLocalTranslation().setElementAt(1, 3, Double.parseDouble(messageTokens[3]));
					g.getLocalTranslation().setElementAt(2, 3, Double.parseDouble(messageTokens[4]));
					
				 }
			 }
		 }
		 if(messageTokens[0].equals("NPCinfo")) { // etc….. 
			 System.out.println("Adding NPC");
			 addNPC(Integer.parseInt(messageTokens[1]),Integer.parseInt(messageTokens[2]),Integer.parseInt(messageTokens[3]),Integer.parseInt(messageTokens[4]));
		 }
		 if(messageTokens[0].equals("mnpc")){
			 int npcGhostID = Integer.parseInt(messageTokens[1]);
			 Vector3D ghostPos = new Vector3D();
			 ghostPos.setX(Integer.parseInt(messageTokens[2]));
			 ghostPos.setY(Integer.parseInt(messageTokens[3]));
			 ghostPos.setZ(Integer.parseInt(messageTokens[4]));
			 updateGhostNPC(npcGhostID, ghostPos);
          
		 }
		 if(messageTokens[0].equals("avatar near")){
			 sendIfNear();
		 }
	 }
	 
	
	private void createGhostAvatar(UUID ghostID,Vector3D pos) {
		int x = (int) pos.getX();
		int y = (int) pos.getY();
		int z = (int) pos.getZ();
		GhostAvatar ghost = new GhostAvatar (game, ghostID, x,y,z);
		ghostAvatars.add(ghost);
		System.out.println("Ghost Added");
	}
	public void sendCreateMessage(Vector3D pos) { // format: (create, localId, x,y,z)
		 try{ 
			 String message = new String("create," + id.toString());
			 message += "," + pos.getX()+"," + pos.getY() + "," + pos.getZ();
			 sendPacket(message);
			 
		 } catch (IOException e) { 
			 e.printStackTrace();
		 }
	 }
	 public void sendJoinMessage(){ // format: join, localId
		 try{ 
			 sendPacket(new String("join," + id.toString())); 
		 }catch (IOException e) { 
			 e.printStackTrace(); 
		 }
	 }
	 public void sendByeMessage(){ // etc….. 
		 try {				
				 String message = new String("bye," + id.toString());
				 sendPacket(message);
				 
			} catch (IOException e) { 
				 e.printStackTrace();
			}
	 }
	 public void sendDetailsForMessage(UUID asker, Vector3D pos){  // etc….. }
		 try {	
			 String message = new String("dsfr," + asker.toString() + ","+ id.toString());
			 message += ("," + pos.getX()+"," + pos.getY() + "," + pos.getZ());
			 sendPacket(message);
		 } catch (IOException e) { 
			 e.printStackTrace();
		}
	 }
	 public void sendMoveMessage(Vector3D pos){ // etc….. }
		 try {				
			 String message = new String("move," + id.toString());
			 message += ("," + pos.getX()+"," + pos.getY() + "," + pos.getZ());
			 sendPacket(message);
			 
		} catch (IOException e) { 
			 e.printStackTrace();
		}
	 }
	 
	 private void addNPC(int id, int x, int y, int z){
		 GhostNPC newNPC = new GhostNPC(game,id,x,y,z);
		 ghostNPC.add(newNPC);
	 }
	 
	 private void updateGhostNPC(int id, Vector3D pos){
		 if(ghostNPC.size()>id){
			 ghostNPC.get(id).updateLocation(pos);
		 }
	 }
	 
	 public void askForNPCinfo(){
		 try{
			 sendPacket(new String("needNPC," + id.toString()));
		 } catch (IOException e) { 
			 e.printStackTrace();
		}
	}
	public void sendIfNear(){
		 Vector3D pos = game.getPlayerPosition();
		 try {				
			 String message = new String("avatar info");
			 message += ("," +(int) pos.getX()+"," +(int) pos.getY() + "," + (int)pos.getZ());
			 sendPacket(message);
			 
		} catch (IOException e) { 
			 e.printStackTrace();
		}
	}
}	
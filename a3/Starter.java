package a3;

import game.GameTwo;
import gameEngine.ServerSetup;

public class Starter {

	public static void main (String[] args){
		Thread server = new Thread () {
			public void run () {
			   new ServerSetup();
			 }
		};
		server.start();
		new GameTwo().start();
	}

}

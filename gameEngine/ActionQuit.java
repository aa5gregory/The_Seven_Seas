package gameEngine;

import game.GameTwo;
import net.java.games.input.Event;
import sage.input.action.AbstractInputAction;

public class ActionQuit extends AbstractInputAction {
	
	private GameTwo quitGame;

	public ActionQuit(GameTwo game){
		quitGame = game;
	}
	
	public void performAction(float time, Event e) {
		quitGame.myShutdown();
	}
}

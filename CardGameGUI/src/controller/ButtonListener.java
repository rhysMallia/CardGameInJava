package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.NewBet;
import view.NewPlayer;

public class ButtonListener implements ActionListener, Constants
{
	private GameEngine gameEngine;
	private ContentHolder content;

	public ButtonListener(GameEngine g, ContentHolder c) 
	{
		gameEngine = g;
		content = c;
		
	}
	
@Override
public void actionPerformed(ActionEvent e) 
{
	/** this listener listens to all buttons **/
	if(e.getActionCommand() == PLAYER) 
	{
		/** To ensure multiple windows can't be opened! **/
		if(content.getPlayer() == null) 
		{
			content.getTopBar().setBet(false);
			content.getMenuBar().setBet(false);
			content.getTopBar().setDeal(false);
			content.getMenuBar().setDeal(false);
			content.setPlayer(new NewPlayer(gameEngine, content));
		}
	}
	else if(e.getActionCommand() == BET) 
	{
		if(content.getNewBet() == null)
		{	
			content.setNewBet(new NewBet(gameEngine, content));
			content.getTopBar().setAdd(false);
			content.getTopBar().setDeal(false);
				
			content.getMenuBar().setAdd(false);
			content.getMenuBar().setDeal(false);
			content.getMenuBar().setDelete(false);
			content.getMenuBar().setClear(false);
		}
	}
	else if(e.getActionCommand() == DEAL) 
	{
		Player player = gameEngine.getPlayer(content.getTopBar().returnId());
		content.getTopBar().setAllButtons(false);
		content.getMenuBar().setClear(false);
		content.getPlayerFromMap(player.getPlayerId()).cleanArray();
		content.getDisplay().setPlayer(player);
		new Thread()
		{
			@Override
			public void run()
			{
				gameEngine.dealPlayer(player, DELAY);
			}
			}.start();
		}
	}
}



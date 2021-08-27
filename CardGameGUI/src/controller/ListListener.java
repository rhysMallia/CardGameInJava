package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class ListListener implements ActionListener, Constants
{
	private GameEngine gameEngine;
	private ContentHolder content;
	public ListListener(GameEngine g, ContentHolder c)
	{
		gameEngine = g;
		content = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand() == EXIT) 
		{
			System.exit(0);
		}
		else if(e.getActionCommand() == DELETE)
		{
			if(content.getTopBar().getBox().getSelectedItem() != null)
			{
				String temp = (String) content.getTopBar().getBox().getSelectedItem();
				if(!temp.equals(HO)) 
				{
					/** if the player is not house, it is then removed from the game
					 *  and the UI is refreshed accordingly
					 */
					temp = temp.substring(0, temp.indexOf("."));
					Player player = gameEngine.getPlayer(temp);
					content.getMap().remove(temp);
					gameEngine.removePlayer(player);
					if(gameEngine.getAllPlayers().size() == 0)
					{
						content.getTopBar().setBet(false);
						content.getTopBar().setDeal(false);
						content.getMenuBar().setBet(false);
						content.getMenuBar().setDeal(false);
						content.getMenuBar().setDelete(false);
					}
					content.getSummary().update();
					content.getTopBar().refresh();
				}
			}
		}
		else if(e.getActionCommand() == CLEAR)
		{
			if(content.getTopBar().getBox().getSelectedItem() != null)
			{
				String temp = (String) content.getTopBar().getBox().getSelectedItem();
				if(!temp.equals(HO)) 
				{
					temp = temp.substring(0, temp.indexOf("."));
					Player player = gameEngine.getPlayer(temp);
					player.setPoints(player.getPoints() + player.getBet());
					player.setBet(0);
					int counter = content.getTopBar().getBox().getSelectedIndex();
					content.getSummary().update();
					content.getTopBar().refresh();
					content.getTopBar().getBox().setSelectedIndex(counter);
					content.getMenuBar().setClear(false);
				}
			}
		}
	}
}
	

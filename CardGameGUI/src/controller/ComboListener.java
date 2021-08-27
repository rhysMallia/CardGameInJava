package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class ComboListener implements ActionListener, Constants
{
	private GameEngine gameEngine;
	private ContentHolder content;
	public ComboListener(GameEngine g, ContentHolder c) 
	{
		gameEngine = g;
		content = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(content.getTopBar().getBox().getSelectedItem() != null)
		{
			/** this loop checks if the selected player (via the value check) has bet, and if so
			 * it will set the correct configuration for the buttons!
			 */
			String temp = (String) content.getTopBar().getBox().getSelectedItem();
			if(temp.equals(HO))
			{
				content.getTopBar().setBet(false);
				content.getTopBar().setDeal(false);
				content.getMenuBar().setBet(false);
				content.getMenuBar().setDeal(false);
				content.getMenuBar().setDelete(false);
				content.getMenuBar().setClear(false);
				content.getDisplay().setPlayer(null);
				content.getDisplay().setHouseCheck(true);
				content.getDisplay().repaint();
			}
			else 
			{
				temp = temp.substring(0, temp.indexOf("."));
				Player player = gameEngine.getPlayer(temp);
				content.getDisplay().setHouseCheck(false);
				content.getDisplay().setPlayer(player);
				content.getDisplay().repaint();
				if(content.getPlayerFromMap(player.getPlayerId()).getResult() > 0)
				{
					content.getTopBar().setAdd(true);
					content.getTopBar().setBet(false);
					content.getTopBar().setDeal(false);
					content.getMenuBar().setAdd(false);
					content.getMenuBar().setBet(false);
					content.getMenuBar().setDeal(false);
					content.getMenuBar().setDelete(false);
				}		
				else if(player.getBet() > 0)
				{
					content.getTopBar().setBet(true);
					content.getTopBar().setDeal(true);
					content.getTopBar().setAdd(true);
					content.getMenuBar().setBet(true);
					content.getMenuBar().setDelete(true);
					content.getMenuBar().setDeal(true);
					content.getMenuBar().setClear(true);
				}
				else
				{
					content.getTopBar().setBet(true);
					content.getTopBar().setDeal(false);
					content.getMenuBar().setBet(true);
					content.getMenuBar().setDelete(true);
					content.getMenuBar().setDeal(false);
					content.getMenuBar().setClear(false);
				}
			}				
		}
	}
}

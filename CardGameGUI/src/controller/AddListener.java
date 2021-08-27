package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JTextField;

import model.Constants;
import model.ContentHolder;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;

public class AddListener implements ActionListener, Constants
{
	private GameEngine gameEngine;
	private ContentHolder content;
	private JTextField name;
	private JTextField points;
	private JFrame frame;
	private boolean check;

	public AddListener(JTextField n, JTextField p, JFrame f, ContentHolder con, GameEngine g, boolean chk)
	{
		name = n;
		points = p;
		frame = f;
		content = con;
		gameEngine = g;
		check = chk;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		/** I don't like making more classes, I feel like reusing listeners is a better idea but I may be wrong 
		 * I use a checker here instead of checking the button value for no reason in particular **/
		String pointS = points.getText();
		if(check)
		{
			/** adding player **/
			String nameS = name.getText();
			if(nameS.length() > 0 && pointS.length() > 0)
			{
				int playerCount = gameEngine.getAllPlayers().size();
				int pointI = Integer.parseInt(pointS);
				/** creating new player with the details passed through the add player frame **/
				if(playerCount == 0) 
				{
					content.addPlayerToMap(HI, HO);
				}
				gameEngine.addPlayer(new SimplePlayer(String.valueOf((content.getPlayerInt() + 1)), nameS, pointI));
				content.addPlayerToMap(String.valueOf(content.getPlayerInt() + 1), nameS);
				content.iteratePlayerInt();
			
				/** now that we have added our player, we have to close the window and refresh the other contents in the main panel **/
				content.setPlayer(null);
				frame.setVisible(false);
			
				content.getTopBar().refresh();
				if(gameEngine.getAllPlayers().size() > 0)
				{
					content.getTopBar().setAdd(true);
					content.getTopBar().setDeal(false);
					content.getMenuBar().setAdd(true);
					content.getMenuBar().setDelete(true);
					content.getMenuBar().setDeal(false);
					content.getSummary().update();
					Player player = gameEngine.getPlayer(content.getTopBar().returnId());
					if(player.getResult() == 0)
					{
						content.getTopBar().setBet(true);
						content.getMenuBar().setBet(true);
					}
				}
				content.getTopBar().getBox().setSelectedIndex(gameEngine.getAllPlayers().size() - 1);
			}
		}
		else
		{
			/** adding bet **/
			
			if(pointS.length() > 0)
			{
				int counter = content.getTopBar().getBox().getSelectedIndex();
				Player player = gameEngine.getPlayer(content.getTopBar().returnId());	
				if(Integer.parseInt(pointS) <= (player.getPoints() + player.getBet()))
				{	
					/** incase the player wants to change their bet, we must include the bet value and add that back into the stack for calculations **/
					if(player.getBet() > 0)
					{
						player.setPoints(player.getPoints() + player.getBet());
					}
					gameEngine.placeBet(player, Integer.parseInt(pointS));
					content.getPlayerFromMap(player.getPlayerId()).resetOutcome();
					content.getPlayerFromMap(player.getPlayerId()).setResult(0);
					player.setResult(0);
					content.getPlayerFromMap(HI).setResult(0);
							
					/** this loop is here because if the player places a bet, and you have already played a round
					 * then the players results and outcome screen should wipe because otherwise it is too hard to
					 * tell if you are in a new game state!
					 */
					for(String key : content.getMap().keySet())
					{
						if(key == HI)
						{
							continue;
						}
						else
						{
									
							for(Player playerLoop : gameEngine.getAllPlayers())
							{
								if(Objects.equals(player.getPlayerId(), key))
								{
									if(playerLoop.getBet() == 0)
									{
										content.getPlayerFromMap(key).setResult(0);
										content.getPlayerFromMap(key).resetOutcome();
										playerLoop.setResult(0);
									}
												
								}
							}
							
						}
							
						content.setNewBet(null);
						frame.setVisible(false);
						content.getTopBar().setBet(true);
						content.getTopBar().setDeal(true);
						content.getTopBar().setAdd(true);
						content.getMenuBar().setBet(true);
						content.getMenuBar().setDeal(true);
						content.getMenuBar().setAdd(true);
						content.getMenuBar().setDelete(true);
						content.getMenuBar().setClear(true);
						content.getTopBar().refresh();
						content.getSummary().update();
						content.getTopBar().getBox().setSelectedIndex(counter);
							
					}
				}
			}
		}
	}
}


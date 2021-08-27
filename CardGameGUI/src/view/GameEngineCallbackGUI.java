package view;
import javax.swing.SwingUtilities;

import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback, Constants
{
	private ContentHolder content;
	public GameEngineCallbackGUI(ContentHolder c) 
	{
		content = c;
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) 
	{
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				content.getPlayerFromMap(player.getPlayerId()).addCard(card);
				content.getDisplay().repaint();
			}
		});
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				content.getPlayerFromMap(player.getPlayerId()).addBust(card);
				content.getDisplay().repaint();
			}
		});
	}

	@Override
	public void result(Player player, int result, GameEngine engine) 
	{
		player.setResult(result);
		content.getPlayerFromMap(player.getPlayerId()).setResult(result);
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				content.getTopBar().setAdd(true);
				content.getTopBar().setBet(false);
				content.getTopBar().setDeal(false);
				content.getMenuBar().setAdd(true);
				content.getMenuBar().setBet(false);
				content.getMenuBar().setDeal(false);
				content.getMenuBar().setDelete(false);
				content.getSummary().updateTable();
			}
		});
		
		
		/** going through the player array and checking if every player has been dealt **/
		boolean checker = false;
		for(Player checkPlayer : engine.getAllPlayers())
		{
			if(content.getPlayerFromMap(checkPlayer.getPlayerId()).getResult() == 0)
			{
				checker = true;
			}
		}
		
		/** if every player has been dealt, we can deal the house, otherwise we reset the summary panel win / loss
		 * as it is a bit confusing to tell who and who has not dealt if the leftover strings are there!
		 */
		if(!checker)
		{
			content.getPlayerFromMap(HI).getArray().clear();
			new Thread()
			{
				@Override
				public void run()
				{
					engine.dealHouse(DELAY);
				}
			}.start();
		}
		else
		{
			
		}
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) 
	{
		content.getPlayerFromMap(HI).addCard(card);
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) 
	{
		content.getPlayerFromMap(HI).addBust(card);
	}

	@Override
	public void houseResult(int result, GameEngine engine) 
	{
		content.getPlayerFromMap(HI).setResult(result);
		/** very dirty looping technique to remove players from the game
		 * that have lost all their points
		 */
		Player toDel = null;
		if(engine.getAllPlayers().size() > 0)
		{
			for(Player player : engine.getAllPlayers())
			{
				if(toDel != null)
				{
					content.getMap().remove(toDel.getPlayerId());
					engine.removePlayer(toDel);
					toDel = null;
				}
				content.getPlayerFromMap(player.getPlayerId()).setOutcome(player.getResult(), result);
				if(player.getPoints() == 0)
				{
					toDel = player;
				}	
			}
		}
		
		/** this is dirty, but I couldn't properly get my loop to delete all the players, one would always
		 * hang if it was the last player in the list and I don't have the time to properly recode **/
		if(toDel != null)
		{
			content.getMap().remove(toDel.getPlayerId());
			engine.removePlayer(toDel);
		}
		
		if(engine.getAllPlayers().size() > 0)
		{
			for(Player player : engine.getAllPlayers())
			{
				if(player.getPoints() == 0)
				{
					content.getMap().remove(player.getPlayerId());
					engine.removePlayer(player);
					break;
				}
			}
		}
		
		/** this clears the results from the player array so that it can be used
		 * for certain components in the last round, and I can maintain 
		 * the results screen in the summary table
		 */
		for(String entry : content.getMap().keySet())
		{
			if(entry == HI) 
			{
				continue;
			}
			content.getPlayerFromMap(entry).setResult(0);
		}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				content.getSummary().updateTable();
				content.getTopBar().setAllButtons(true);
				content.getTopBar().refresh();
				/** I added a delay here because it switches over to fast to the house otherwise 
				 * and the last player can't really see what they have, even though that's still 
				 * have with a 1 second delay!
				 */
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				content.getTopBar().getBox().setSelectedItem(HO);
				content.getDisplay().setHouseCheck(true);
				content.getDisplay().repaint();
			}
		});
	}

}

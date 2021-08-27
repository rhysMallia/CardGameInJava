package view;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
   public static final Logger logger = Logger.getLogger(GameEngineCallbackImpl.class.getName());
   private static final String CARD = "Card Dealt to";
   private static final String BUST = "YOU BUSTED!";
   private static final String H = "House";
   private static final String P = "Final Player Results";

   // utility method to set output level of logging handlers
   public static Logger setAllHandlers(Level level, Logger logger, boolean recursive)
   {
	   
      // end recursion?
      if (logger != null)
      {
         logger.setLevel(level);
         for (Handler handler : logger.getHandlers())
            handler.setLevel(level);
         // recursion
         setAllHandlers(level, logger.getParent(), recursive);
      }
      return logger;
   }

   public GameEngineCallbackImpl()
   {
      // NOTE can also set the console to FINE in %JRE_HOME%\lib\logging.properties
	 Logger.getLogger("java.awt").setLevel(Level.OFF); 
	 Logger.getLogger("sun.awt").setLevel(Level.OFF); 
	 Logger.getLogger("javax.swing").setLevel(Level.OFF);
      setAllHandlers(Level.FINE, logger, true);
   }

   @Override
   public void nextCard(Player player, PlayingCard card, GameEngine engine)
   {
	  if(player != null)
		  logger.log(Level.FINE, String.format("%s %s .. %s", CARD, player.getPlayerName(),card.toString()));
      // intermediate results logged at Level.FINE
      
      // TODO: complete this method to log results
   }

   @Override
   public void result(Player player, int result, GameEngine engine)
   {
      // final results logged at Level.INFO
      logger.log(Level.INFO, String.format("%s, final result=%d", player.getPlayerName(), result));
      // TODO: complete this method to log results
   }

   @Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) 
	{
		logger.log(Level.INFO, String.format("%s %s .. %s ... %s", CARD, player.getPlayerName(), card.toString()
			, BUST));
	
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) 
	{
		logger.log(Level.FINE, String.format("%s %s .. %s", CARD, H, card.toString()));
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) 
	{
		logger.log(Level.INFO, String.format("%s %s .. %s ... HOUSE BUSTED!", CARD, H, card.toString()));
	}

	@Override
	public void houseResult(int result, GameEngine engine) 
	{
		String temp = P;
		for(Player player : engine.getAllPlayers()) {
			temp = temp + "\n";
			temp = temp + player.toString();
		}
		logger.log(Level.INFO, String.format("%s, final result=%d", H, result));
		logger.log(Level.INFO, String.format("%s", temp));
	}

   // TODO complete the rest of this interface

}

package client;

import java.util.logging.Level;

import javax.swing.SwingUtilities;

import view.ApplicationFrame;
import view.GameEngineCallbackImpl;

public class GUIClient{
	
	public static void main(String[] args) 
	{
		GameEngineCallbackImpl.setAllHandlers(Level.INFO, GameEngineCallbackImpl.logger, true);
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				new ApplicationFrame();
			}
		});
	}
}

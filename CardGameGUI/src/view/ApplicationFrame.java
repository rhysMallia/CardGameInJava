package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import model.Constants;
import model.ContentHolder;
import model.GameEngineImpl;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class ApplicationFrame extends JFrame implements Constants
{
	private ContentHolder contentHolder;
	private TopToolBar TopBar;
	private CardDisplay Display;
	private SummaryPanel Summary;
	private MenuBar Menu;
	
	public ApplicationFrame()
	{
		super(TITLE);
		/** creating the game engine for the project, and adding a new gameEngineCallback **/
		GameEngine gameEngine = new GameEngineImpl();
		
		/** setting the features of the Jframe itself **/
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)(screen.height * SCREEN_MOD);
		int width = (int)(screen.width * SCREEN_MOD);
		setLayout(new BorderLayout());
		
		/** creating my content holder **/
		contentHolder = new ContentHolder();
		gameEngine.addGameEngineCallback(new GameEngineCallbackGUI(contentHolder));
		
		GameEngineCallbackImpl callback = new GameEngineCallbackImpl();
		gameEngine.addGameEngineCallback(callback);
		
		/** creating and assigning the separate parts of the GUI into their positions**/
		Menu = new MenuBar(gameEngine, contentHolder);
		contentHolder.setMenuBar(Menu);
		setJMenuBar(Menu);
		
		TopBar = new TopToolBar(gameEngine, contentHolder);
		contentHolder.setTopBar(TopBar);
		add(TopBar, BorderLayout.NORTH);
		
		Display = new CardDisplay(contentHolder);
		contentHolder.setDisplay(Display);
		add(Display, BorderLayout.CENTER);
		
		Summary = new SummaryPanel(gameEngine, contentHolder);
		contentHolder.setSummary(Summary);
		add(Summary, BorderLayout.SOUTH);
		
		IconFactory icons = new IconFactory();
		contentHolder.setIcons(icons);
		
		setMinimumSize(new Dimension(screen.width/3, screen.height/3));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(width, height));
		setVisible(true);
	}
}

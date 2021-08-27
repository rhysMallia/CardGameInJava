package model;
import java.io.File;

public interface Constants {
	/** values associated to the main Application frame and player frame**/
	public final static String TITLE = "First to 42!";
	public final static String SMALL_T = "Add Player";
	public final static double SCREEN_MOD = 0.8;
	
	/** values for our buttons / lists and listeners associated to it **/
	public final static String PLAYER = "Add Player!";
	public final static String DELETE = "Remove Player!";
	public final static String BET = "Bet!";
	public final static String DEAL = "Deal!";
	public final static String OKAY = "Okay!";
	public final static String CANCEL = "Cancel!";
	public final static String EXIT = "Exit!";
	public final static String CLEAR = "Clear bet!";
	
	/** values for carddealing and other **/
	public final static int DELAY = 100;
	
	/** House related variables **/
	public final static String HO = "House";
	public final static String HI = "H";
	
	/** Icon and card rendering variables **/
	 public static final String FILE_PATH = String.format("img%s", File.separator);
	 public static final String EASY_FILE_PATH = String.format("img%scards%s", File.separator, File.separator);
	 public static final double HEIGHT_MOD = 1.5;
	 public static final int BUFFER = 10;
	 public static final int MAGIC = 7;
	 public static final int CURVE = 30;
}

package states;
 
import main.PlayerInfo;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
 

public class ZombEatGame extends StateBasedGame {

	public static final int GAMEPAUSESTATE          = 2;
	public static final int GAMEPLAYSTATE          = 1;
    public static final int MAINMENUSTATE          = 0;
    public static final int SCREENHEIGHT 			= 480;
    public static final int SCREENWIDTH 			= 640;
    public static final int TEXTSTATE          		= 3;
    public static final int GAMEOVERSTATE			= 4;
    public static final int ENDINGGAMESTATE			= 5;
    public static final int PASSWORDSTATE			= 6;
    public static final int INSTRUCTIONSSTATE			= 7;
    
    public static void main(String[] args) throws SlickException
    {
         AppGameContainer app = new AppGameContainer(new ZombEatGame());
 
         app.setDisplayMode(SCREENWIDTH, SCREENHEIGHT, false);
         app.setTargetFrameRate(60);
         app.setShowFPS(false);
         app.start();
    }
 
    public ZombEatGame()
    {
        super("Zomb'Eat");
    }
 
    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    	PlayerInfo.createNewCurrentPlayerInfo();
    	this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new TextState(TEXTSTATE));
        this.addState(new GamePauseState(GAMEPAUSESTATE));
        this.addState(new GameOverState(GAMEOVERSTATE));
        this.addState(new EndingGameState(ENDINGGAMESTATE));
        this.addState(new PasswordState(PASSWORDSTATE));
        this.addState(new InstructionsState(INSTRUCTIONSSTATE));
    }
}
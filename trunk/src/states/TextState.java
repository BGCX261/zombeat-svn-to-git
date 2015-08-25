package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;

import main.PlayerInfo;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import org.newdawn.slick.Color;

import objects.MovingPicture;
import tools.Menu;
import tools.ResourceManager;
import utils.FontLoader;

public class TextState extends BasicGameState {
 
		
	Image background=null;
	int menuX=20;
	int menuY=100;
	Image startGameOption=null;
	float startGameScale = 1;
    float scaleStep = 0.0001f;
	static Image title=null;
	static Collection<MovingPicture> movingPictures=null;
	java.text.DecimalFormat df = new java.text.DecimalFormat("000");
	Menu menu = null;
 
	
    int stateID = 3;
    static TextField text;
	
    TextState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);  
    	text = new TextField(gc, gc.getGraphics().getFont(), 5, 10, 635-1, 40);
    	text.setTextColor(new Color(255,255,255));
    	text.setBackgroundColor(null);
    	text.setBorderColor(null);
    	
    	title = GameplayState.getLevel().getImageId("TRANSITION_TITLE");
    	GameplayState.getLevel().getAmbience().loop();
    	movingPictures = GameplayState.getLevel().getMovingPicture();
    }
 
    @Override
    public int getID() {
        return stateID;
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
    		menu = new Menu();
        	ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/pauseMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	text = new TextField(gc, gc.getGraphics().getFont(), 5, 10, 635-1, 40);
    	text.setTextColor(new Color(255,255,255));
    	text.setBackgroundColor(null);
    	text.setBorderColor(null);
    	
    	title = GameplayState.getLevel().getImageId("TRANSITION_TITLE");
    	Image menuOptions = menu.getImageId("OPTIONS");
    	startGameOption = menuOptions.getSubImage(0, 0, 377, 71);
    	movingPictures = GameplayState.getLevel().getMovingPicture();
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	g.setColor(new Color(1.0f,1.0f,1.0f));
    	String[] paragraphes = GameplayState.getLevel().getText("TRANSITION").split("//");
    	text.setLocation(10, 40);

    	GameplayState.getLevel().getTransitionBackground().draw();
    	for (String par : paragraphes){
        	text.setText(par);
        	text.render(gc, g);
        	text.setLocation(text.getX(), text.getY()+20);
    	}
    	
    	title.draw();
    	startGameOption.draw(menuX, menuY, startGameScale);
    	for (MovingPicture e: movingPictures){
        	e.dessiner();
    	}
    	
    	if (GameplayState.levelNb>1) {
    		FontLoader.font26.drawString(370,450, "PASSWORD :  " + PasswordState.passwords[GameplayState.levelNb-2] + df.format(PasswordState.crypteScore(PlayerInfo.getCurrentPlayerInfo().getScore(), GameplayState.levelNb-1)));
    	}
    	
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
   	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideStartGame = false;

    	for (MovingPicture e: movingPictures){
    		e.update();
        	e.dessiner();
    	}
    	if( ( mouseX >= menuX && mouseX <= menuX + startGameOption.getWidth()) &&
    	    ( mouseY >= menuY && mouseY <= menuY + startGameOption.getHeight()) ){
    	    insideStartGame = true;
    	}

   	 
    	if(insideStartGame)
    	{
    	   if(startGameScale < 1.05f)
    		   startGameScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
     		  sbg.enterState(ZombEatGame.GAMEPLAYSTATE, new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	   
    	}else{
    	  if(startGameScale > 1.0f)
    		  startGameScale -= scaleStep * delta;
    	}
    }
    
    public static void refresh(GameContainer gc){
    	text = new TextField(gc, gc.getGraphics().getFont(), 5, 10, 635-1, 40);
    	text.setTextColor(new Color(255,255,255));
    	text.setBackgroundColor(null);
    	text.setBorderColor(null);
    	
    	title = GameplayState.getLevel().getImageId("TRANSITION_TITLE");
    	GameplayState.getLevel().getAmbience().loop();
    	movingPictures = GameplayState.getLevel().getMovingPicture();
    }
}
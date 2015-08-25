package states;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import tools.Menu;
import tools.ResourceManager;
 
public class GamePauseState extends BasicGameState {
 
	Image exitOption=null;	
	Image pause = null;
	float exitScale = 1;
	Menu menu;
	int menuX=40;
 
	
    int menuY=120;
    float scaleStep = 0.0001f;

    Image continueOption=null;
    float continueScale = 1;
    
    Image restart=null;
    float restartScale = 1;
    
    Image currentGraphics = null; //le background a afficher
    boolean newPause = true; //s'il faut changer le background
    int stateID = 2;
    
	
    GamePauseState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	menu = new Menu();
    	try {
			ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/pauseMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	Image menuOptions = menu.getImageId("OPTIONS");
   	 
    	continueOption = menuOptions.getSubImage(0, 284, 377, 71);
    	currentGraphics = new Image(640,480);
    	exitOption = menuOptions.getSubImage(0, 71, 377, 71);
    	restart = menuOptions.getSubImage(0, 137, 270, 71);
    	pause = menuOptions.getSubImage(0, 497, 170, 71);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	if (newPause){
        	sbg.getState(ZombEatGame.GAMEPLAYSTATE).render(gc, sbg, g);
        	g.copyArea(currentGraphics, 0, 0);
        	g.clear();
        	newPause=false;
    	}
    	
    	g.drawImage(currentGraphics, 0, 0, new Color(1.0f,0.9f,0.9f));

    	pause.draw((gc.getWidth()-pause.getWidth())/2, 10,1.1f);
    	continueOption.draw(menuX, menuY, continueScale);
    	exitOption.draw(menuX, menuY+160, exitScale);
    	restart.draw(menuX, menuY+80, restartScale);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
   	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideContinueGame = false;
    	boolean insideExit = false;
    	boolean insideRestart = false;
    	 
    	if( ( mouseX >= menuX && mouseX <= menuX + continueOption.getWidth()) &&
    	    ( mouseY >= menuY && mouseY <= menuY + continueOption.getHeight()) ){
    		insideContinueGame = true;
    	}else if( ( mouseX >= menuX && mouseX <= menuX+ restart.getWidth()) &&
    	          ( mouseY >= menuY+80 && mouseY <= menuY+80 + restart.getHeight()) ){
    	    insideRestart = true;
    	}else if( ( mouseX >= menuX && mouseX <= menuX+ exitOption.getWidth()) &&
  	          ( mouseY >= menuY+160 && mouseY <= menuY+160 + exitOption.getHeight()) ){
    		insideExit = true;
    	}
    	
    	if (input.isKeyPressed(Input.KEY_ESCAPE)) {
    		((GameplayState) sbg.getState(ZombEatGame.GAMEPLAYSTATE)).setState(GameplayState.STATES.START_GAME_STATE);
    		sbg.enterState(ZombEatGame.GAMEPLAYSTATE);
    	}
    	 
    	if(insideContinueGame){
    	  if(continueScale < 1.05f){
    		  continueScale += scaleStep * delta;}
    	 
    	  if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ){
    		  sbg.enterState(ZombEatGame.GAMEPLAYSTATE);
    	  }
    	}else{
    	  if(continueScale > 1.0f)
    		  continueScale -= scaleStep * delta;
    	}
   	 
	   	if(insideRestart)
	   	{
	   	   if(restartScale < 1.05f)
	   	     restartScale +=  scaleStep * delta;
	   	   
	   	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
	   	   {
	   		   ((GameplayState) sbg.getState(ZombEatGame.GAMEPLAYSTATE)).setState(GameplayState.STATES.GAME_OVER_STATE);
	   		   sbg.enterState(ZombEatGame.GAMEPLAYSTATE);
	   	   }
	   	   
	   	}else{
	   	  if(restartScale > 1.0f)
	   	    restartScale -= scaleStep * delta;
	   	}
    	 
    	if(insideExit)
    	{
    	   if(exitScale < 1.05f)
    	     exitScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ){
    		   GameplayState.getLevel().getAmbience().fade(1000, 0, true);
    		   sbg.enterState(ZombEatGame.MAINMENUSTATE,new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	   }
    	}else{
    	  if(exitScale > 1.0f)
    	    exitScale -= scaleStep * delta;
    	}
    }
    
    public void leave(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.leave(gc, sb);
        newPause = true;
    }
 
 
}
package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


import main.PlayerInfo;

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
 
public class MainMenuState extends BasicGameState {
 
    Image background = null;
	Image exitOption = null;
	Image title = null;
    float exitScale = 1;
    Menu menu;
    int menuX=60;
    int menuY=160;
    float scaleStep = 0.0001f;
 
    Image startGameOption = null;
    Image passwordOption=null;
    Image instructionsOption=null;
    float startGameScale = 1;
    float passwordScale = 1;
    float instructionsScale = 1;
    int stateID = 0;
 
    MainMenuState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb); 
        PlayerInfo.getCurrentPlayerInfo().setScore(0);
        GameplayState.setLevelNb(1);
        menu.getAmbience().play();
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
    		menu = new Menu();
        	ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/mainMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	menu.getAmbience().loop();
    	
    	background = menu.getBackground();
    	 
    	// load the menu images
    	Image menuOptions = menu.getImageId("OPTIONS");
    	title = menu.getImageId("TITLE");
    	 
    	startGameOption = menuOptions.getSubImage(0, 0, 377, 71);
    	passwordOption = menuOptions.getSubImage(0, 213, 377, 71); 
    	exitOption = menuOptions.getSubImage(0, 71, 377, 71);
    	instructionsOption = menuOptions.getSubImage(0, 424, 377, 71); 
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	background.draw(0, 0);
    	 
    	// Draw menu
    	title.draw();
    	startGameOption.draw(menuX, menuY, startGameScale);
    	passwordOption.draw(menuX,menuY+75, passwordScale);
    	instructionsOption.draw(menuX,menuY+150, instructionsScale);
    	exitOption.draw(menuX + 380, menuY+225, exitScale);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideStartGame = false;
    	boolean insideExit = false;
    	boolean insidePassword = false;
    	boolean insideInstructions = false;
    	 
    	if( ( mouseX >= menuX && mouseX <= menuX + startGameOption.getWidth()) &&
    	    ( mouseY >= menuY && mouseY <= menuY + startGameOption.getHeight()) ){
    	    insideStartGame = true;
    	}else if( ( mouseX >= menuX+350 && mouseX <= menuX+350+ exitOption.getWidth()) &&
    	          ( mouseY >= menuY+225 && mouseY <= menuY+225 + exitOption.getHeight()) ){
    	    insideExit = true;
    	}else if( ( mouseX >= menuX && mouseX <= menuX+ passwordOption.getWidth()) &&
  	          ( mouseY >= menuY+75 && mouseY <= menuY+75 + passwordOption.getHeight()) ){
    		insidePassword = true;
    	}else if( ( mouseX >= menuX && mouseX <= menuX+ instructionsOption.getWidth()) &&
    	          ( mouseY >= menuY+150 && mouseY <= menuY+150 + instructionsOption.getHeight()) ){
      		insideInstructions = true;
  	}
    	 
    	if(insideStartGame){
    	  if(startGameScale < 1.05f)
    	    startGameScale += scaleStep * delta;
    	 
    	  if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ){
    		  menu.getAmbience().fade(500, 0, true);
    		  TextState.refresh(gc);
    		  sbg.enterState(ZombEatGame.TEXTSTATE, new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)) );
    		  return;
    	  }
    	}else{
    	  if(startGameScale > 1.0f)
    	    startGameScale -= scaleStep * delta;
    	 
    	  
    	}
    	 
    	if(insideExit)
    	{
    	   if(exitScale < 1.05f)
    	     exitScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
       	    gc.exit();
    	}else{
    	  if(exitScale > 1.0f)
    	    exitScale -= scaleStep * delta;
    	}
    	
    	if(insidePassword)
    	{
    	   if(passwordScale < 1.05f)
    		   passwordScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
       	     sbg.enterState(ZombEatGame.PASSWORDSTATE, new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	}else{
    	  if(passwordScale > 1.0f)
    		  passwordScale -= scaleStep * delta;
    	}
    	
    	
    	
    	if(insideInstructions)
    	{
    	   if(instructionsScale < 1.05f)
    		   instructionsScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
       	     sbg.enterState(ZombEatGame.INSTRUCTIONSSTATE,new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	}else{
    	  if(instructionsScale > 1.0f)
    		  instructionsScale -= scaleStep * delta;
    	}
    }
    
    
 
}
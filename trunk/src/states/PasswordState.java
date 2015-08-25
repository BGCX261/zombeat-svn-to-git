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
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import tools.Menu;
import tools.ResourceManager;
import utils.FontLoader;
 
public class PasswordState extends BasicGameState {
 
	public static String[] passwords = {"ELEPHANT", "SUSHI", "MAYA", "WORLDEND"}; 
    Image background = null;
    Image passwordOption=null;
	Image exitOption = null;
	Image title = null;
	UnicodeFont f = FontLoader.font60;
	static int[] reorgaUnit = {3,6,1,0,5,8,9,2,7,4};
	static int[] reorgaDiz = {5,7,3,2,6,0,1,9,8,4};
	
	TextField passwordField = null;
    float exitScale = 1;
    Menu menu;
    int menuX=40;
    int menuY=140;
    float scaleStep = 0.0001f;
    private static boolean fullyDisplayed = false;
 
    int stateID = 6;
    
 
    PasswordState( int stateID ) 
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
        passwordField.setAcceptingInput(true);
        passwordField.setText("");
    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
    		menu = new Menu();
        	ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/passwordMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	menu.getAmbience().loop();
    	
		Image menuOptions = menu.getImageId("OPTIONS");
    	title = menu.getImageId("TITLE");
    	exitOption = menuOptions.getSubImage(0, 71, 377, 71);
		passwordOption = menuOptions.getSubImage(0, 213, 377, 71);
		
    	passwordField = new TextField(gc, f , menuX,menuY+passwordOption.getHeight(), 400, 180);
    	passwordField.setBackgroundColor(null);
    	passwordField.setBorderColor(null);
    	passwordField.setFocus(true);
    	passwordField.setMaxLength(15);
    	background = menu.getBackground();
    	 
    	// load the menu images
    	
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	background.draw(0, 0);
    	
    	passwordField.render(gc, g);
    	// Draw menu
    	title.draw();
    	passwordOption.draw(menuX,menuY); 
    	exitOption.draw(menuX, menuY+200, exitScale);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
//    	System.out.println("1000 : " + crypteScrore(1000, 1) + " decrypt: " + decryptScore(crypteScrore(1000, 1), 1));
//    	System.out.println("2500 : " + crypteScrore(2500, 2) + " decrypt: " + decryptScore(crypteScrore(2500, 2), 2));
//    	System.out.println("4000 : " + crypteScrore(4000, 3) + " decrypt: " + decryptScore(crypteScrore(4000, 3), 3));
//    	System.out.println("4100 : " + crypteScrore(4100, 3) + " decrypt: " + decryptScore(crypteScrore(4100, 3), 3));
//    	System.out.println("4200 : " + crypteScrore(4200, 3) + " decrypt: " + decryptScore(crypteScrore(4200, 3), 3));
//    	System.out.println("6600 : " + crypteScrore(65600, 4) + " decrypt: " + decryptScore(crypteScrore(6600, 4), 4));
    	
    	for (int i=0;i<4;i++){
    		if (passwordField.getText().toUpperCase().startsWith(passwords[i]) && passwordField.getText().length() == passwords[i].length()+3 ){
    			if(decryptScore(Integer.valueOf(passwordField.getText().substring(passwordField.getText().length()-3)),i+1) == -1) continue;
    			if (!fullyDisplayed) {fullyDisplayed=true; break;}
    			fullyDisplayed=false;
        		menu.getAmbience().stop();
        		menu.getSound("passwordSucceed").play();
        		GameplayState.setLevelNb(i+2);
        		PlayerInfo.getCurrentPlayerInfo().setScore(decryptScore(Integer.valueOf(passwordField.getText().substring(passwordField.getText().length()-3)),i+1));
        		if (decryptScore(Integer.valueOf(passwordField.getText().substring(passwordField.getText().length()-3)),i+1) == PlayerInfo.SCOREMAXLEVEL[i+2]){
        			for (int k=1; k<i+2; k++) PlayerInfo.getCurrentPlayerInfo().setAccomplissement(k, true);
        		}
        		sbg.enterState(ZombEatGame.TEXTSTATE, new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
        		return;
        	}
    	}
    	
    	
    	
    	Input input = gc.getInput();
    	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideExit = false;
    	 
    	if( ( mouseX >= menuX && mouseX <= menuX+ exitOption.getWidth()) &&
    	          ( mouseY >= menuY+200 && mouseY <= menuY+200 + exitOption.getHeight()) ){
    	    insideExit = true;
    	}
    	 
    	  	 
    	if(insideExit)
    	{
    	   if(exitScale < 1.05f)
    	     exitScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
       	    	sbg.enterState(ZombEatGame.MAINMENUSTATE, new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	}else{
    	  if(exitScale > 1.0f)
    	    exitScale -= scaleStep * delta;
    	}
    }
    
    
    public void leave(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.leave(gc, sb);
        passwordField.setText("");
        passwordField.setAcceptingInput(false);
    }
    
    public static int crypteScore(int score, int levelNb){
    	if (score<1000 || score>9999) return 1000*levelNb;
    	
    	int score2 = (score - levelNb*1000)/100 + 1;
    	int score2unites = score2%10;
    	int score2dizaines = (score2/10)%10;
    	
    	int centaines = reorgaUnit[score2unites] ;
    	int unites = reorgaDiz[score2dizaines];
    	int dizaines = (centaines + 2 * reorgaDiz[score2unites] + levelNb)%10; 
    	
    	return (centaines *100 + dizaines*10 + unites) ;
    }
    
    public int decryptScore(int scoreCrypte, int levelNb){
    	if(scoreCrypte == 0 ) return 1000*levelNb;
    	
    	int dizaines = (scoreCrypte/10)%10;
    	int centaines = (scoreCrypte/100);
    	int unites = (scoreCrypte)%10;
    	
    	int score2unites = indexOf(centaines, reorgaUnit);
    	int score2dizaines = indexOf(unites, reorgaDiz);
    	
    	if ((centaines + 2 * reorgaDiz[score2unites] + levelNb)%10 != dizaines) return -1;
    	int score2Decrypt =score2dizaines*10 + score2unites;
    	return (score2Decrypt-1)*100 + levelNb*1000;
    }
    
    private int indexOf(int i, int[] table){
    	int k=0;
    	while(table[k] != i) k++;
    	return k;
    }
 
}
package tools;

import java.util.HashMap;
import java.util.Map;


import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

public class Menu {
	private Map<String, Animation> animationMap;
	private Map<String, Image> imageMap;
	private Map<String, Music> musicMap;
	private Map<String, Sound> soundMap;
	private Map<String, String> textMap;
	
	public Menu(){
		
		musicMap 	 = new HashMap<String, Music>();
		imageMap 	 = new HashMap<String, Image>();
		animationMap = new HashMap<String, Animation>();
		soundMap 	 = new HashMap<String, Sound>();
		textMap 	 = new HashMap<String, String>();
	}
	
	public void addAnimation(String key, Animation anim){
		animationMap.put(key, anim);
	}
	
	public void addImage(String key, Image img){
		imageMap.put(key, img);
	}
	
	public void addMusic(String key, Music music){
		musicMap.put(key, music);
	}
	
	public void addSound(String key, Sound sound){
		soundMap.put(key, sound);
	}
	
	public void addText(String key, String text){
		textMap.put(key, text);
	}
	
	
	public Animation getAnimation(String id){
		return animationMap.get(id);
	}
	
	public Image getBackground(){
		return imageMap.get("BACKGROUND");
	}
	
	public Image getImageId(String id){
		return imageMap.get(id);
	}
	
	public Sound getSound (String id){
		return soundMap.get(id);
	}
	
	public String getText(String id){
		return textMap.get(id);
	}
	
	public Music getAmbience() {
		return musicMap.get("menuMusic");
	}
}

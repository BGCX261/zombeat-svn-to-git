package tools;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import main.Ammo;
import main.Bonus;
import main.Ennemy;
import objects.BlockMap;
import objects.Teleport;
import objects.MovingPicture;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;

import utils.Position;

public class Level{
	private Map<String, Animation> animationMap;
	private Map<String, BlockMap> blockMapMap;
	private Map<String, Bonus> bonusMap;
	private Map<String, Ennemy> ennemyMap;
	private Map<String, Image> imageMap;
	private int number;
	private Map<String, Music> musicMap;
	private Map<String, Sound> soundMap;
	private Map<String, Teleport> teleportMap;
	private Map<String, String> textMap;
	private Map<String, Ammo> ammoMap;
	private Map<String, MovingPicture> movingPictureMap;
	private Map<String, Boolean> propertyMap;
	private Map<Integer, Position> posZombiesMap;
	
	public Level(int i){
		this.number = i;
		
		musicMap 	 = new HashMap<String, Music>();
		soundMap	 = new HashMap<String, Sound>();
		imageMap 	 = new HashMap<String, Image>();
		animationMap = new HashMap<String, Animation>();
		textMap 	 = new HashMap<String, String>();
		blockMapMap	 = new HashMap<String, BlockMap>();
		ennemyMap	 = new HashMap<String, Ennemy>();
		bonusMap	 = new HashMap<String, Bonus>();
		ammoMap	 = new HashMap<String, Ammo>();
		teleportMap  = new HashMap<String, Teleport>();
		movingPictureMap= new HashMap<String, MovingPicture>();
		propertyMap = new HashMap<String, Boolean>();
		
		posZombiesMap = new HashMap<Integer,Position>();
	}
	
	public void addAmmo(String key, Ammo ammo){
		ammoMap.put(key, ammo);
	}
	
	public void addAnimation(String key, Animation anim){
		animationMap.put(key, anim);
	}
	
	public void addBlockMap(String key, BlockMap map){
		blockMapMap.put(key, map);
	}
	
	public void addBonus(String key, Bonus bonus){
		bonusMap.put(key, bonus);
	}
	
	public void addEnnemy(String key, Ennemy ennemy){
		ennemyMap.put(key, ennemy);
	}
	
	public void addImage(String key, Image img){
		imageMap.put(key, img);
	}
	
	public void addProperty(String key, Boolean bool){
		propertyMap.put(key, bool);
	}
	
	public void addMusic(String key, Music music){
		musicMap.put(key, music);
	}
	
	public void addTeleport(String key, Teleport teleport){
		teleportMap.put(key, teleport);
//		getMap().getNonBlockingEntities().add(teleport);
	}
	
	public void addText(String key, String text){
		textMap.put(key, text);
	}
	
	public void addMovingPicture(String key, MovingPicture movingPicture){
		movingPictureMap.put(key, movingPicture);
	}
	
	public Music getAmbience() {
		return musicMap.get("sound");
	}
	
	public Collection<Ammo> getAmmo() {
		return ammoMap.values();
	}
	
	public Animation getAnimation(String id){
		return animationMap.get(id);
	}
	
	public Image getBackground(){
		return imageMap.get("BACKGROUND");
	}
	
	public Collection<Bonus> getBonus(){
		return bonusMap.values();
	}
	
	public Collection<Ennemy> getEnnemies(){
		return ennemyMap.values();
	}
	
	public Image getImageId(String id){
		return imageMap.get(id);
	}
	
	public BlockMap getMap() {
		return blockMapMap.get("MAP");
	}
	
	public int getNumber(){
		return number;
	}
	
	public Music getMusic(String id) {
		return musicMap.get(id);
	}
	
	public Collection<Teleport> getTeleport(){
		return teleportMap.values();
	}
//	
//	public void addTeleportToMap(){
//		GameplayState.map.getNonBlockingEntities().addAll(getTeleport());
//	}
	
	public String getText(String id){
		return textMap.get(id);
	}
	
	public Image getTransitionBackground(){
		return imageMap.get("TRANSITION_BACKGROUND");
	}
	
	public Collection<MovingPicture> getMovingPicture(){
		return movingPictureMap.values();
	}
	
	public boolean isFinal(){
		
		return (propertyMap.get("isFinal") != null);
	}
	
	public void addInitialCoordinatesZombie(Position pos, int id){
		this.posZombiesMap.put(id, pos);
	}
	
	public Position getInitialCoordinates(int zombieID){
		return this.posZombiesMap.get(zombieID);
	}

	public void addSound(String key, Sound sound) {
		soundMap.put(key, sound);
	}
}

package tools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import main.Bonus;
import objects.BlockMap;
import objects.Teleport;
import objects.MovingPicture;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.Position;

import ennemies.AntarcticGuy;
import ennemies.FlameGuy;
import ennemies.Ninja;

 
public class ResourceManager {
	
	public Level getResources(){
		Level level = new Level(98);
		try {
			loadResourcesIntoLevel(new FileInputStream("xml/resources.xml"), level);
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
	}
 
	private static ResourceManager _instance = new ResourceManager();
 
	public final static ResourceManager getInstance(){
		return _instance;
	}
 
	private ResourceManager(){
	}
 
	private final void addElementAsAmmo(Element resourceElement, Level l) throws SlickException{
		if (!resourceElement.getAttribute("tw1").isEmpty()){
			loadAmmo(l,
					resourceElement.getAttribute("id"), 
					resourceElement.getTextContent(), 
					Integer.valueOf(resourceElement.getAttribute("tw1")),
					Integer.valueOf(resourceElement.getAttribute("tw2")),
					Integer.valueOf(resourceElement.getAttribute("tw3")),
					Integer.valueOf(resourceElement.getAttribute("th")),
					Integer.valueOf(resourceElement.getAttribute("duration")));
		}else{
			loadAmmo(l,
					resourceElement.getAttribute("id"), 
					resourceElement.getTextContent(), 
					Integer.valueOf(resourceElement.getAttribute("tw")),
					Integer.valueOf(resourceElement.getAttribute("th")),
					Integer.valueOf(resourceElement.getAttribute("duration")));
		}
	}
	
	private final void addElementAsAnimation(Element resourceElement, Level l) throws SlickException{
		loadAnimation(l, resourceElement.getAttribute("id"), resourceElement.getTextContent(), 
				Integer.valueOf(resourceElement.getAttribute("tw")),
				Integer.valueOf(resourceElement.getAttribute("th")),
				Integer.valueOf(resourceElement.getAttribute("duration")));
	}

	private final void addElementAsAnimation(Element resourceElement, Menu m) throws SlickException{
		loadAnimation(m, resourceElement.getAttribute("id"), resourceElement.getTextContent(), 
				Integer.valueOf(resourceElement.getAttribute("tw")),
				Integer.valueOf(resourceElement.getAttribute("th")),
				Integer.valueOf(resourceElement.getAttribute("duration")));
	}
 
	private final void addElementAsBlockMap(Element resourceElement, Level l) throws SlickException {
		l.addBlockMap(resourceElement.getAttribute("id"), new BlockMap(resourceElement.getTextContent()));
		}

	
	private final void addElementAsBonus(Element resourceElement, Level l) throws NumberFormatException, DOMException, SlickException{
		
		l.addBonus(resourceElement.getAttribute("id"), new Bonus( 
				l,
				Integer.valueOf(resourceElement.getAttribute("minx")),
				Integer.valueOf(resourceElement.getAttribute("maxx")),
				Integer.valueOf(resourceElement.getAttribute("y")),
				resourceElement.getAttribute("id"),
				resourceElement.getAttribute("class")));
	}
 
	private final void addElementAsEnnemy(Element resourceElement, Level l) throws NumberFormatException, DOMException, SlickException{
		loadEnnemy(l,
				resourceElement.getAttribute("id"), 
				resourceElement.getAttribute("class"), 
				Integer.valueOf(resourceElement.getAttribute("minx")),
				Integer.valueOf(resourceElement.getAttribute("maxx")),
				Integer.valueOf(resourceElement.getAttribute("y")));
	}
	
	private final void addElementAsImage(Element resourceElement, Level l) throws SlickException {
		l.addImage(resourceElement.getAttribute("id"), loadImage(resourceElement.getAttribute("id"),resourceElement.getTextContent()));
	}
 
	private final void addElementAsImage(Element resourceElement, Menu m) throws SlickException {
		m.addImage(resourceElement.getAttribute("id"), loadImage(resourceElement.getAttribute("id"),resourceElement.getTextContent()));
	}
 
 
	private final void addElementAsMusic(Element resourceElement, Level l) throws SlickException {
		l.addMusic(resourceElement.getAttribute("id"), loadMusic(resourceElement.getAttribute("id"), resourceElement.getTextContent()));
	}
	
	private final void addElementAsMusic(Element resourceElement, Menu m) throws SlickException {
		m.addMusic(resourceElement.getAttribute("id"), loadMusic(resourceElement.getAttribute("id"), resourceElement.getTextContent()));
	}
 
	private final void addElementAsSound(Element resourceElement, Level l) throws SlickException {
		l.addSound(resourceElement.getAttribute("id"), loadSound(resourceElement.getAttribute("id"), resourceElement.getTextContent()));
	}
	
	private final void addElementAsSound(Element resourceElement, Menu m) throws SlickException {
		m.addSound(resourceElement.getAttribute("id"), loadSound(resourceElement.getAttribute("id"), resourceElement.getTextContent()));
	}
	
	private final void addElementAsTeleport(Element resourceElement, Level l) throws NumberFormatException, DOMException, SlickException{
		l.addTeleport(resourceElement.getAttribute("id"), new Teleport(l, resourceElement.getAttribute("id"), 
				Integer.valueOf(resourceElement.getAttribute("x")), 
				Integer.valueOf(resourceElement.getAttribute("y")), 
				Integer.valueOf(resourceElement.getAttribute("xtogo")),
				Integer.valueOf(resourceElement.getAttribute("ytogo"))));
		
		l.addTeleport(resourceElement.getAttribute("id") + "_RETURN",new Teleport(l,resourceElement.getAttribute("id") + "_RETURN", 
				Integer.valueOf(resourceElement.getAttribute("xtogo")), 
				Integer.valueOf(resourceElement.getAttribute("ytogo")), 
				Integer.valueOf(resourceElement.getAttribute("x")),
				Integer.valueOf(resourceElement.getAttribute("y"))));
		
	}
 
 
	private final void addElementAsText(Element resourceElement, Level l) throws SlickException{
		l.addText(resourceElement.getAttribute("id"), loadText(resourceElement.getAttribute("id"),resourceElement.getTextContent()));
	}
 
	private final void addElementAsText(Element resourceElement, Menu m) throws SlickException{
		m.addText(resourceElement.getAttribute("id"), loadText(resourceElement.getAttribute("id"),resourceElement.getTextContent()));
	}
	
	private final void addElementAsMovingPicture(Element resourceElement, Level l){
		try {
			l.addMovingPicture(resourceElement.getAttribute("id"), loadMovingPicture(Integer.parseInt(resourceElement.getAttribute("x")), Integer.parseInt(resourceElement.getAttribute("y")), Integer.parseInt(resourceElement.getAttribute("tx")), Integer.parseInt(resourceElement.getAttribute("ty")), Integer.parseInt(resourceElement.getAttribute("time")), resourceElement.getTextContent()));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private final void loadAmmo(Level l, String id, String spriteSheetPath,
			int tw1, int tw2, int tw3, int th, int duration) throws SlickException{
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		
		
		Image ammo = loadImage(id, spriteSheetPath);
		Image ammo1 = ammo.getSubImage(0, 0, tw1, th);
		Image ammo2 = ammo.getSubImage(tw1, 0, tw2, th);
		Image ammo3 = ammo.getSubImage(tw1+tw2, 0, tw3, th);
 
		Image[] ammoTab = {ammo1, ammo2, ammo3};
		
		l.addAnimation(id, new Animation(ammoTab, duration));
	}
	
	private final void loadAmmo(Level l, String id, String spriteSheetPath,
			int tw, int th, int duration) throws SlickException{
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		SpriteSheet spr = new SpriteSheet(loadImage(id, spriteSheetPath), tw, th);
		Animation anim = new Animation(spr, duration);
		anim.setLooping(true);
		l.addAnimation(id, anim);
	}
 
 
 
	private final void loadAnimation(Level l, String id, String spriteSheetPath,
			int tw, int th, int duration) throws SlickException{
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		SpriteSheet spr = new SpriteSheet(loadImage(id, spriteSheetPath), tw, th);
		Animation anim = new Animation(spr, duration);
		anim.setLooping(true);
		l.addAnimation(id, anim);
	}
	
	private final void loadAnimation(Menu m, String id, String spriteSheetPath,
			int tw, int th, int duration) throws SlickException{
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		SpriteSheet spr = new SpriteSheet(loadImage(id, spriteSheetPath), tw, th);
		Animation anim = new Animation(spr, duration);
		anim.setLooping(false);
		m.addAnimation(id, anim);
	}
 
	private final void loadEnnemy(Level l,String id, String type,
			int minx, int maxx, int y) throws SlickException{
		if (type.equals("NINJA"))			l.addEnnemy(id, new Ninja(l,minx,maxx,y, id));
		else if (type.equals("FLAMEGUY")) 	l.addEnnemy(id, new FlameGuy(l,minx,maxx,y, id));
		else if (type.equals("ANTARCTICGUY")) 	l.addEnnemy(id, new AntarcticGuy(l,minx,maxx,y, id));
	}
 
	
	private final Image loadImage(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Image resource [" + id + "] has invalid path");
 
		Image image = null;
		try{
			image = new Image(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load image", e);
		}
		
		return image;
	}
	
	private final MovingPicture loadMovingPicture(int x, int y, int to_x, int to_y, int time, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Image resource [" + path + "] has invalid path");
		 
		return new MovingPicture(x, y, to_x, to_y, time, path);
	}
 	
	
	public final void loadResourcesIntoLevel(InputStream is, Level l) throws SlickException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SlickException("Could not load resources", e);
		}
		Document doc = null;
        try {
			doc = docBuilder.parse (is);
		} catch (SAXException e) {
			throw new SlickException("Could not load resources", e);
		} catch (IOException e) {
			throw new SlickException("Could not load resources", e);
		}
 
		// normalize text representation
        doc.getDocumentElement ().normalize ();
 
        NodeList listResources = doc.getElementsByTagName("resource");
 
        int totalResources = listResources.getLength();
        
        Teleport.initializeColor();
 
        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
 
        	Node resourceNode = listResources.item(resourceIdx);
 
        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
        		Element resourceElement = (Element)resourceNode;
 
        		String type = resourceElement.getAttribute("type");
 
        		if(type.equals("image")){
        			addElementAsImage(resourceElement,l);
        		}else if(type.equals("sound")){
            			addElementAsSound(resourceElement,l);
        		}else if(type.equals("music")){
        			addElementAsMusic(resourceElement,l);
        		}else if(type.equals("text")){
        			addElementAsText(resourceElement,l);
        		}else if(type.equals("animation")){
        			addElementAsAnimation(resourceElement,l);
        		}else if(type.equals("map")){
        			addElementAsBlockMap(resourceElement,l);
        		}else if(type.equals("ennemy")){
        			addElementAsEnnemy(resourceElement,l);
        		}else if(type.equals("teleport")){
        			addElementAsTeleport(resourceElement,l);
        		}else if(type.equals("ammo")){
        			addElementAsAmmo(resourceElement,l);
        		}else if(type.equals("bonus")){
        			addElementAsBonus(resourceElement,l);
        		}else if (type.equals("movingPicture")){
        				addElementAsMovingPicture(resourceElement, l);
        		}else if (type.equals("property")){
    				addElementAsProperty(resourceElement, l);
        		}else if (type.equals("coordinates")){
    				addElementAsCoordinates(resourceElement, l);
        		}
        	}
        }
 
	}
	
	private void addElementAsCoordinates(Element resourceElement, Level l) {
		l.addInitialCoordinatesZombie(new Position(
				Integer.valueOf(resourceElement.getAttribute("x")), 
				Integer.valueOf(resourceElement.getAttribute("y"))),
				Integer.valueOf(resourceElement.getAttribute("id"))
				);
	}

	private void addElementAsProperty(Element resourceElement, Level l) {
		l.addProperty(resourceElement.getAttribute("id"),
				(resourceElement.getTextContent().equals("true"))?	true : false
		);
	}

	public final void loadResourcesIntoMenu(InputStream is, Menu m) throws SlickException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SlickException("Could not load resources", e);
		}
		Document doc = null;
        try {
			doc = docBuilder.parse (is);
		} catch (SAXException e) {
			throw new SlickException("Could not load resources", e);
		} catch (IOException e) {
			throw new SlickException("Could not load resources", e);
		}
 
		// normalize text representation
        doc.getDocumentElement ().normalize ();
 
        NodeList listResources = doc.getElementsByTagName("resource");
 
        int totalResources = listResources.getLength();
 
        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
 
        	Node resourceNode = listResources.item(resourceIdx);
 
        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
        		Element resourceElement = (Element)resourceNode;
 
        		String type = resourceElement.getAttribute("type");
 
        		if(type.equals("image")){
        			addElementAsImage(resourceElement,m);
        		}else if(type.equals("sound")){
        			addElementAsSound(resourceElement,m);
        		}else if(type.equals("music")){
        			addElementAsMusic(resourceElement,m);
        		}else if(type.equals("text")){
        			addElementAsText(resourceElement,m);
        		}else if(type.equals("animation")){
        			addElementAsAnimation(resourceElement,m);
        		}
        	}
        }
 
	}
	
	private final Music loadMusic(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Sound resource [" + id + "] has invalid path");
 
		Music music = null;
 
		try {
			music = new Music(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load sound", e);
		}

		return music;
	}
	
	private final Sound loadSound(String id, String path) throws SlickException{
		if(path == null || path.length() == 0)
			throw new SlickException("Sound resource [" + id + "] has invalid path");
 
		Sound s = null;
 
		try {
			s = new Sound(path);
		} catch (SlickException e) {
			throw new SlickException("Could not load sound", e);
		}

		return s;
	}
	
	private final String loadText(String id, String value) throws SlickException{
		if(value == null)
			throw new SlickException("Text resource [" + id + "] has invalid value");
 
		return value;
	}
	
}
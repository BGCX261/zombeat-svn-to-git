package objects;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
 
public class BlockMap {
	public static int mapHeight;
	public static int mapWidth;
	private ArrayList<CollidableObject> blockingEntities= new ArrayList<CollidableObject>();
	private ArrayList<CollidableObject> nonBlockingEntities= new ArrayList<CollidableObject>();
	private String ref;
	private int square[] = {1,1,15,1,15,15,1,15}; //square shaped tile
	private int halfSquareBottomLeft[] = {1,1,1,15,15,15,15,15}; //half square
	private int halfSquareBottomRight[] = {15,1,15,15,1,15,1,15};
	private int halfSquareUpRight[] = {1,1,15,1,15,15,15,15};
	private int halfSquareUpLeft[] = {1,1,1,15,15,1,15,1};
	private int halfSquare[] = {1,3,1,15,15,15,15,3};
	public TiledMap tmap;
 
	public BlockMap(String ref) throws SlickException {
		this.ref=ref;
		tmap = new TiledMap(ref, "data");
		mapWidth = tmap.getWidth() * tmap.getTileWidth();
		mapHeight = tmap.getHeight() * tmap.getTileHeight();
 
		for (int x = 0; x < tmap.getWidth(); x++) {
			for (int y = 0; y < tmap.getHeight(); y++) {
				int tileID = tmap.getTileId(x, y, 0);
				if (tileID == 1) {
					blockingEntities.add(
                                        new Block(x * 16, y * 16, square, "square")
                                        );
					tmap.setTileId(x, y, 0, 10);
				}
				
				if (tileID == 2) {
					blockingEntities.add(
                                        new IceBlock(x * 16, y * 16, square, "square")
                                        );
					tmap.setTileId(x, y, 0, 10);
				}
				
				if (tileID == 3) {
					nonBlockingEntities.add(
                                        new CityEscape(x * 16, y * 16, halfSquare, "square")
                                        );
				}
				
				if (tileID == 4 || tileID == 5) {
					nonBlockingEntities.add(
                                        new Champi(x * 16, y * 16, square, "square")
                                        );
				}

				if (tileID == 6) {
					blockingEntities.add(
                                        new Block(x * 16, y * 16, halfSquareUpLeft, "square")
                                        );
					tmap.setTileId(x, y, 0, 10);
				}
				
				if (tileID == 7) {
					blockingEntities.add(
                                        new Block(x * 16, y * 16, halfSquareUpRight, "square")
                                        );
					tmap.setTileId(x, y, 0, 10);
				}
				
				if (tileID == 8) {
					blockingEntities.add(
                                        new Block(x * 16, y * 16, halfSquareBottomRight, "square")
                                        );
					tmap.setTileId(x, y, 0, 10);
				}
				
				if (tileID == 9) {
					blockingEntities.add(
                                        new Block(x * 16, y * 16, halfSquareBottomLeft, "square")
                                        );
					tmap.setTileId(x, y, 0, 10);
				}
			}
		}
	}
	
	public BlockMap clone(){
		try {
			BlockMap clone = new BlockMap(ref);
			return clone;
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<CollidableObject> getBlockingEntities(){
		return blockingEntities;
	}
	
	public ArrayList<CollidableObject> getNonBlockingEntities(){
		return nonBlockingEntities;
	}
}
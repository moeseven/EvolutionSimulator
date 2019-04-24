package logic;
import java.util.LinkedList;

public class World {
	public LinkedList<Creature> creatures;
	public LinkedList<Tile> tiles;
	public double maxX;
	public double maxY;
	public int size;
	public World(int size, double edgeLength){
		creatures=new LinkedList<Creature>();
		tiles= new LinkedList<Tile>();
		for(int i=0; size*size>i;i++){
			tiles.add(new Tile(this));
		}
		maxX=edgeLength;
		maxY=edgeLength;
		this.size=size;
	}
	public void update(){
		Creature c;
		//clear creature lists for all Tiles (lists are created again by creature update function
		for(int i=0; tiles.size()>i;i++){
			tiles.get(i).clearCreatures();
		}
		for(int i=0; creatures.size()>i;i++){
			creatures.get(i).update(maxX, maxY);
			//now tiles handle Food and feeding
		}
		for(int i=0; tiles.size()>i;i++){
			tiles.get(i).update();
		}
	}
	public Tile getTile(double x, double y){
		return tiles.get(getTileNumber(x,y));
	}
	public int getTileNumber(double x, double y){
		if(size*size<=((Math.floor(((x+maxX/2)/maxX*((double)(size))))*(size)+(Math.floor((y+maxY/2)/maxY*((double)(size))))))){
			return size*size-1;
		}else{
			return (int) ((Math.floor(((x+maxX/2)/maxX*((double)(size))))*(size)+(Math.floor((y+maxY/2)/maxY*((double)(size))))));
		}
		
	}
	public void triggerReproduction(){
		for(int i=0; creatures.size()>i;i++){
			Creature c=creatures.get(i);
			c.reproduceInWorld();
		}
	}
	public void checkDeaths(){
		for(int i=0; creatures.size()>i;i++){
			Creature c=creatures.get(i);
			c.checkIfToDie();
		}
	}
	public void reset(Boolean creatures){
		clearFood();
		if(creatures){
			for(int i=0; i<this.creatures.size();i++){
				this.creatures.get(i).reset();
			}
		}		
	}
	public void clearFood(){
		for(int i=0; i<tiles.size();i++){
			tiles.get(i).setCurrentFoodPlant(0);
		}
	}
	public void addCreature(Creature c){
		creatures.add(c);
	}
	public LinkedList<Creature> getCreatures() {
		return creatures;
	}
	public void setCreatures(LinkedList<Creature> creatures) {
		this.creatures = creatures;
	}
	public LinkedList<Tile> getTiles() {
		return tiles;
	}
	public void setTiles(LinkedList<Tile> tiles) {
		this.tiles = tiles;
	}
	public double getMaxX() {
		return maxX;
	}
	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}
	public double getMaxY() {
		return maxY;
	}
	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public LinkedList<Tile> getRelevantTilesForCalculatingCreatureVision(Creature c){
		//!!! only works for even numbers as world.size
		int overlap = 10;
		LinkedList<Tile> ret = new LinkedList<Tile>();
		Tile centerTile=c.getTile();
		ret.add(centerTile);//middle
		if(c.getxPos()+(maxX/size)<maxX/2&&(((Math.IEEEremainder(Math.abs(c.getxPos()),(maxX/size))<overlap&&c.getxPos()<0) || (Math.IEEEremainder(Math.abs(c.getxPos()),(maxX/size))>maxX/size-overlap&&c.getxPos()>=0)))){
			ret.add(getTile(c.getxPos()+(maxX/size),c.getyPos()));//right
			if(c.getyPos()+(maxY/size)<maxY/2&&(((Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))<overlap&&c.getyPos()<0) || (Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))>maxY/size-overlap&&c.getyPos()>=0)))){
				ret.add(getTile(c.getxPos()+(maxX/size),c.getyPos()+(maxY/size)));//down right
			}	
			if(c.getyPos()-(maxY/size)>-maxY/2&&(((Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))<overlap&&c.getyPos()>=0) || (Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))>maxY/size-overlap&&c.getyPos()<0)))){	
				ret.add(getTile(c.getxPos()+(maxX/size),c.getyPos()-(maxY/size)));//up right
			}			
		}
		if(c.getxPos()-(maxX/size)>-maxX/2&&(((Math.IEEEremainder(Math.abs(c.getxPos()),(maxX/size))<overlap&&c.getxPos()>=0) || (Math.IEEEremainder(Math.abs(c.getxPos()),(maxX/size))>maxX/size-overlap&&c.getxPos()<0)))){
			ret.add(getTile(c.getxPos()-(maxX/size),c.getyPos())); //left
			if(c.getyPos()+(maxY/size)<maxY/2&&(((Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))<overlap&&c.getyPos()<0) || (Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))>maxY/size-overlap&&c.getyPos()>=0)))){
				ret.add(getTile(c.getxPos()-(maxX/size),c.getyPos()+(maxY/size)));//down left
			}
			if(c.getyPos()-(maxY/size)>-maxY/2&& (((Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))<overlap&&c.getyPos()>=0) || (Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))>maxY/size-overlap&&c.getyPos()<0)))){	
				ret.add(getTile(c.getxPos()-(maxX/size),c.getyPos()-(maxY/size)));	//up left
			}			
		}
		if(c.getyPos()+(maxY/size)<maxY/2&& (((Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))<overlap&&c.getyPos()<0) || (Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))>maxY/size-overlap&&c.getyPos()>=0)))){	
			ret.add(getTile(c.getxPos(),c.getyPos()+(maxY/size)));// down
		}
		if(c.getyPos()-(maxY/size)>-maxY/2 && (((Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))<overlap&&c.getyPos()>=0) || (Math.IEEEremainder(Math.abs(c.getyPos()),(maxY/size))>maxY/size-overlap&&c.getyPos()<0)))){	
			ret.add(getTile(c.getxPos(),c.getyPos()-(maxY/size)));// up
		}	
		
		return ret; //return 4 tiles to be concerned for proximity detection does not perform well on the edges of the world
	}
}

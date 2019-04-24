package logic;

import java.util.LinkedList;

public class Tile {
	public double currentFoodPlant;
	public double currentFoodFlesh;
	public double currentFoodRot;
	public int foodGainPlant;
	public int maxFood;
	public LinkedList<Creature> creatures;
	public World world;
	public Tile(World world){
		currentFoodPlant = 0;
		currentFoodFlesh=0;
		currentFoodRot=0;
		foodGainPlant = 1;
		maxFood = 10000;
		creatures= new LinkedList<Creature>();
		this.world=world;
	}
	public void update(){
		letThemEat();
		currentFoodPlant+=foodGainPlant;
		if(currentFoodFlesh>1){
			currentFoodFlesh-=0.25;
			currentFoodRot+=0.2;
		}
		if(currentFoodRot>1){//maybe make it decay into more plant food
			currentFoodRot-=0.0008;
			currentFoodPlant+=0.0001;
		}
		if (currentFoodPlant>maxFood){
			currentFoodPlant=maxFood;
		}
		
	}
	public double getCurrentFoodPlant() {
		return currentFoodPlant;
	}
	public void setCurrentFoodPlant(double currentFood) {
		this.currentFoodPlant = currentFood;
	}
	public int getFoodGain() {
		return foodGainPlant;
	}
	public void setFoodGain(int foodGain) {
		this.foodGainPlant = foodGain;
	}
	public int getMaxFood() {
		return maxFood;
	}
	public void setMaxFood(int maxFood) {
		this.maxFood = maxFood;
	}
	
	public double getCurrentFoodFlesh() {
		return currentFoodFlesh;
	}
	public void setCurrentFoodFlesh(double currentFoodFlesh) {
		this.currentFoodFlesh = currentFoodFlesh;
	}
	public double getCurrentFoodRot() {
		return currentFoodRot;
	}
	public void setCurrentFoodRot(double currentFoodRot) {
		this.currentFoodRot = currentFoodRot;
	}
	public void letThemEat(){
		double totalStrength=0;// strong creatures eat first
		for(int i=0; i<creatures.size();i++){
			totalStrength+=creatures.get(i).strength;
		}
		for(int i=0; i<creatures.size();i++){
			creatures.get(i).eatFromTile(Math.floor(currentFoodPlant*creatures.get(i).strength/totalStrength),Math.floor(currentFoodFlesh*creatures.get(i).strength/totalStrength),Math.floor(currentFoodRot*creatures.get(i).strength/totalStrength),this);		
		}
	}
	public void clearCreatures() {
		creatures= new LinkedList<Creature>();
		
	}
	public String toString(){
		return ""+world.tiles.indexOf(this);
	}
}

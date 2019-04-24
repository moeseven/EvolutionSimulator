package main;

import gui.GuiWorld;
import gui.SimulationFrame;
import logic.Creature;
import logic.World;

public class Test {
	public static void main(String[] args){
		World world = new World(4,1000);	
		Creature creature = new Creature(world);
		creature.setxPos(499);
		creature.setyPos(499);
		world.addCreature(creature);
		//System.out.println(world.getTileNumber(-500, -500));
		System.out.println(creature.getTile().toString());
		for(int i=0; i<world.getRelevantTilesForCalculatingCreatureVision(creature).size();i++){
			System.out.print(" "+world.getRelevantTilesForCalculatingCreatureVision(creature).get(i).toString());
		}
		
	}
}

package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import gui.Config;
import gui.GuiWorld;
import gui.SimulationFrame;
import logic.Creature;
import logic.World;

public class OneWorldSim implements ActionListener{
	Timer tm = new Timer(4,this);
	int count = 0;
	ParametersSim ps;
	GuiWorld w;
	SimulationFrame sf;
	Config control;
	private static World world;
	//public static void main(String[] args){
	public OneWorldSim(){
		world = new World(14,460);
		
		//randomize tiles
//		for(int i=0; i<world.tiles.size();i++){
//			double rand = Math.random();
//			if(rand<0.1){
//				world.getTiles().get(i).setFoodGain(3);
//			}else{
//				if(rand<0.15){
//					world.getTiles().get(i).setFoodGain(0);
//				}
//			}
//		}
		ps= new ParametersSim();
		w = new GuiWorld(world,ps,this);
		sf = new SimulationFrame();
		control = new Config(ps,world);
		sf.add(w,BorderLayout.CENTER);
		sf.add(control,BorderLayout.LINE_END);
		tm.setDelay(ps.getTick());
		tm.start();
	}
//	public void createWorld(){
//		world= new World(4,1000);
//	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(world.creatures.size()<20){
			Creature c= new Creature(world);
			world.addCreature(c);
		}
		count+=1;
		if(count==50000){
			for(int a=0; a<world.tiles.size();a++){
					world.tiles.get(a).setFoodGain(0);
			}
		}
		if(count==60000){
			for(int a=0; a<world.tiles.size();a++){
					world.tiles.get(a).setFoodGain(1);
			}
		}
		if(count==70000){
			for(int a=0; a<world.tiles.size();a++){
					world.tiles.get(a).setFoodGain(2);
			}
		}
		if(count==90000){
			for(int a=0; a<world.tiles.size();a++){
					world.tiles.get(a).setFoodGain(1);					
			}
			count=0;
		}
		world.update();
		world.triggerReproduction();
		world.checkDeaths();
		tm.setDelay(ps.getTick());
		updateFrame();
	}
	public void updateFrame(){
		sf.repaint();
		w.repaint();
		control.repaint();
		control.updateClickedCreature();
	}
}

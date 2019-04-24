package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import logic.Creature;

public class CreatureComponent extends JComponent{
	
	private Creature creature;
	public CreatureComponent(Creature creature){
		this.creature=creature;
		this.setVisible(true);
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(creature!=null){
		g.drawString("number of nodes: "+creature.nodes.size(), 10, 50);
		g.drawString("strength: "+(double)Math.round(100*creature.strength)/(double)100, 10, 75);
		g.drawString("energy: "+(int)creature.energy, 10,100);
		g.drawString("health: "+(int)creature.getHealth(), 10, 125);
//		g.setColor(Color.ORANGE);
//		g.fillOval((int)(creature.getxPos()+creature.world.getMaxX()/2-creatureSize/2),(int)(creature.getyPos()+creature.world.getMaxY()/2-creatureSize/2), (int)(creatureSize*(creature.strength+0.5)), (int)(creatureSize*(creature.strength+0.5)));
	
		}
	}
	public Creature getCreature() {
		return creature;
	}
	public void setCreature(Creature creature) {
		this.creature = creature;
	}
	
}

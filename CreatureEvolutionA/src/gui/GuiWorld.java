package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Creature;
import logic.Tile;
import logic.World;
import main.OneWorldSim;
import main.ParametersSim;
import mathematical.MyFunctions;


public class GuiWorld extends JPanel{
	private World world;
	private ParametersSim ps;
	private LinkedList<CreatureComponent> creatureComponents;
	private int count=0;
	private int creatureSize=10;
	private int clickedX;
	private int clickedY;
	private Tile clickedTile;
	private OneWorldSim ows;
	private Creature clickedCreature;
	int x=0, velx=1;
	public GuiWorld(World world,ParametersSim ps, OneWorldSim ows){
		this.ps=ps;
		this.world=world;
		this.ows=ows;
		MyMouseListener ml=new MyMouseListener();
		creatureComponents=new LinkedList<CreatureComponent>();
		this.addMouseListener(ml);
		this.setLayout(null);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw Tiles with nutrition color
		for(int i=0; i<world.getTiles().size();i++){
			g.setColor(new Color(80,250,120,(int)(255*Math.min(world.getTiles().get(i).getCurrentFoodPlant()/world.getTiles().get(i).getMaxFood(),1))));
			g.fillRect((int)(Math.floor(i/world.getSize())*world.getMaxX()/world.getSize()), (int)(Math.floorMod(i, world.size)*world.getMaxY()/world.getSize()), (int)(world.getMaxX()/world.getSize()), (int)(world.getMaxY()/world.getSize()));
			g.setColor(new Color(250,70,120,(int)(255*Math.min(world.getTiles().get(i).getCurrentFoodFlesh()/world.getTiles().get(i).getMaxFood(),1))));
			g.fillRect((int)(Math.floor(i/world.getSize())*world.getMaxX()/world.getSize()), (int)(Math.floorMod(i, world.size)*world.getMaxY()/world.getSize()), (int)(world.getMaxX()/world.getSize()), (int)(world.getMaxY()/world.getSize()));
			g.setColor(new Color(150,190,20,(int)(255*Math.min(world.getTiles().get(i).getCurrentFoodRot()/world.getTiles().get(i).getMaxFood(),1))));
			g.fillRect((int)(Math.floor(i/world.getSize())*world.getMaxX()/world.getSize()), (int)(Math.floorMod(i, world.size)*world.getMaxY()/world.getSize()), (int)(world.getMaxX()/world.getSize()), (int)(world.getMaxY()/world.getSize()));
			g.setColor(new Color(80,0+world.getTiles().get(i).getFoodGain()*75,80));
			g.drawRect((int)(Math.floor(i/world.getSize())*world.getMaxX()/world.getSize()), (int)(Math.floorMod(i, world.size)*world.getMaxY()/world.getSize()), (int)(world.getMaxX()/world.getSize()), (int)(world.getMaxY()/world.getSize()));
		}
		
			
		for(int i=0;i<world.creatures.size();i++){
			if(world.creatures.get(i).equals(clickedCreature)){
				g.setColor(Color.WHITE);
				g.drawOval((int)(world.creatures.get(i).getxPos()+world.getMaxX()/2-creatureSize/2*(world.creatures.get(i).strength+0.4)),(int)(world.creatures.get(i).getyPos()+world.getMaxY()/2-(creatureSize/2*(world.creatures.get(i).strength+0.4))), (int)(creatureSize*(world.creatures.get(i).strength+0.4)), (int)(creatureSize*(world.creatures.get(i).strength+0.4)));			
			}
			g.setColor(Color.BLACK);
			g.drawLine((int)(world.creatures.get(i).getxPos()+world.getMaxX()/2),(int)(world.creatures.get(i).getyPos()+world.getMaxY()/2), (int)(world.creatures.get(i).getxPos()+Math.cos(world.creatures.get(i).getDirection()*2*Math.PI)*creatureSize*(world.creatures.get(i).strength+0.4)*world.creatures.get(i).getStingerExtension()+world.getMaxX()/2),(int) (world.creatures.get(i).getyPos()+Math.sin(world.creatures.get(i).getDirection()*2*Math.PI)*creatureSize*(world.creatures.get(i).strength+0.4)*world.creatures.get(i).getStingerExtension()+world.getMaxY()/2));			
			g.setColor(new Color((int)(world.creatures.get(i).getEaterFlesh()*250),(int)(world.creatures.get(i).getEaterPlant()*250),(int)(world.creatures.get(i).getEaterRot()*250)));			
			g.fillOval((int)(world.creatures.get(i).getxPos()+world.getMaxX()/2-creatureSize/2*(world.creatures.get(i).strength+0.4)),(int)(world.creatures.get(i).getyPos()+world.getMaxY()/2-(creatureSize/2*(world.creatures.get(i).strength+0.4))), (int)(creatureSize*(world.creatures.get(i).strength+0.4)), (int)(creatureSize*(world.creatures.get(i).strength+0.4)));
		}
		int ax = (int)(world.getSize()/world.getMaxX());
		int ay = (int)(world.getSize()/world.getMaxY());
		g.setColor(Color.BLACK);
		g.drawString(""+world.creatures.size(), 461, 20);
	}
	private class MyMouseListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			clickedX=(int) MyFunctions.convertXfromGuiToLogic(arg0.getX(), world);
			clickedY=(int) MyFunctions.convertXfromGuiToLogic(arg0.getY(), world);
			clickedTile=world.getTile(clickedX, clickedY);
			if(clickedTile.creatures.size()>0){
				clickedCreature=clickedTile.creatures.getFirst();
				for(int i=0; i<clickedTile.creatures.size();i++){
					if(MyFunctions.getDistance(clickedX, clickedY, clickedTile.creatures.get(i).getxPos(), clickedTile.creatures.get(i).getyPos())<MyFunctions.getDistance(clickedCreature, clickedTile.creatures.get(i))){
						clickedCreature=clickedTile.creatures.get(i);
					}
				}
				ps.setClickedCreature(clickedCreature);
				ows.updateFrame();
			}					
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public int getClickedX() {
		return clickedX;
	}
	public void setClickedX(int clickedX) {
		this.clickedX = clickedX;
	}
	public int getClickedY() {
		return clickedY;
	}
	public void setClickedY(int clickedY) {
		this.clickedY = clickedY;
	}
}

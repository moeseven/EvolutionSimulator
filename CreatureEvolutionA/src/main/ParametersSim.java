package main;

import logic.Creature;

public class ParametersSim {
	private int tick;
	private Creature clickedCreature;
	public ParametersSim(){
		tick=5;
	}
	public int getTick() {
		return tick;
	}
	public void setTick(int tick) {
		this.tick = tick;
	}
	public Creature getClickedCreature() {
		return clickedCreature;
	}
	public void setClickedCreature(Creature clickedCreature) {
		this.clickedCreature = clickedCreature;
	}
	
}

package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class SimulationFrame extends JFrame{
	public SimulationFrame(){
		setVisible(true);
		setTitle("Evolution Simulation");
		setSize(750, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
	}

}

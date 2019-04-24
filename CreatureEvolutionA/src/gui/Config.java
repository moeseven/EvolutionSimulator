package gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Creature;
import logic.World;
import main.ParametersSim;

public class Config extends JPanel{
	ParametersSim ps;
	JButton b1;
	JButton b2;
	CreatureComponent cc;
	public Config(ParametersSim ps, World world){
		this.ps=ps;
		cc=new CreatureComponent(new Creature(world));
		b1=new JButton("b1");
		b2=new JButton("decrease simulation speed");
		b1.setText(ps.getTick()+"");
		b1.addActionListener(new b1ActionListener());
		b2.addActionListener(new b2ActionListener());
		this.setVisible(true);
		setLayout(new BorderLayout());
		add(cc,BorderLayout.CENTER);
		add(b1,BorderLayout.LINE_END);
		add(b2,BorderLayout.AFTER_LAST_LINE);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString(""+ps.getTick(), 150, 150);
	}
	private class b1ActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// increase Simulation speed
			if(ps.getTick()>1){
				ps.setTick(ps.getTick()-1);
				b1.setText(ps.getTick()+"");
				cc.setCreature(ps.getClickedCreature());
			}
		}
		
	}
	private class b2ActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// decrease Simulation speed
				ps.setTick(ps.getTick()*2);
				
				if(ps.getTick()>4000){
					ps.setTick(5);
				}
				b1.setText(ps.getTick()+"");
		}
		
	}
	public void updateClickedCreature(){
		cc.setCreature(ps.getClickedCreature());
	}
}

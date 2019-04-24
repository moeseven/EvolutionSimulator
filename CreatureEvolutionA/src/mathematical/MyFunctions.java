package mathematical;

import logic.Creature;
import logic.World;

public class MyFunctions {

	public static double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.abs(x1-x2)+Math.abs(y1-y2));
	}
	public static double getDistance(Creature c0, Creature c1){
		return Math.sqrt(Math.abs(c0.xPos-c1.xPos)+Math.abs(c0.yPos-c1.yPos));
	}
	//90° angle is c1 in front of c0?
	public static boolean isInFront(Creature c0, Creature c1){
		double c1xn,c1yn;
		c1xn=(c1.xPos-c0.xPos)*Math.cos(-2*Math.PI*c0.getDirection())-(c1.yPos-c0.yPos)*Math.sin(-2*Math.PI*c0.getDirection());
		c1yn=(c1.xPos-c0.xPos)*Math.sin(-2*Math.PI*c0.getDirection())-(c1.yPos-c0.yPos)*Math.cos(-2*Math.PI*c0.getDirection());
		if(c1xn>0&&c1yn<c1xn&&c1yn>-c1xn){
			return true;
		}else{
			return false;
		}
	}	
	public static double convertXfromLogicToGui(double x,World world){
		return x+world.getMaxX()/2;
	}
	public static double convertYfromLogicToGui(double y,World world){
		return y+world.getMaxY()/2;
	}
	public static double convertXfromGuiToLogic(double x, World world){
		return x-world.getMaxX()/2;
	}
	public static double convertYfromGuiToLogic(double y, World world){
		return y-world.getMaxY()/2;
	}
}

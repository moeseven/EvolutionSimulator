package logic;

import java.util.LinkedList;

import mathematical.MyFunctions;

public class Node {
	public double value;
	public double valueNew;
	public double startValue;
	public Node n1;
	public Node n2;
	public double maxValue;
	public int kind;
	public Creature c;
	public int performanceCounter;
	public Node(Creature c,double startValue){
		this.c=c;
		this.startValue=startValue;
		value=0;
		valueNew=0;
		kind=0;
		maxValue=1;
		performanceCounter=(int)Math.random()*100;
		n1=null;
		n2=null;
	}
	public void form(){
		
	}
	public void deviate(){
		
	}
	
	public double getValueNew() {
		return valueNew;
	}
	public void setValueNew(double valueNew) {
		this.valueNew = valueNew;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setValueNotExceedingBounds(double value){
		//use this if you want to stay inside the Bounds
		if (value>maxValue){
			this.value= maxValue;
		}else{
			if (value<-maxValue){
			this.value= -maxValue;
		}
		}
		this.value = value;
	}
	public void reset(){
		value=startValue;
	}
	public double getStartValue() {
		return startValue;
	}
	public void setStartValue(double startValue) {
		this.startValue = startValue;
	}
	public double getValue(){
		return value;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int k){
		//
		kind=k;
	}
	
	public Node getN1() {
		return n1;
	}
	public void setN1(Node n1) {
		this.n1 = n1;
	}
	public Node getN2() {
		return n2;
	}
	public void setN2(Node n2) {
		this.n2 = n2;
	}
	public void updateValue(){
		value= valueNew;
	}
	public void operate(){	
		//increment performance counter
		if(performanceCounter<100){
			performanceCounter+=1;
		}else{
			performanceCounter=0;
		}	
		switch (kind) {		
        // constant is everything else
//			case 10: {//modulo addition
//				if((n1.getValue()+n2.getValue())*(n1.getValue()+n2.getValue())>1){
//					valueNew = n1.getValue()+n2.getValue()-1;
//				}else{
//					valueNew= n1.getValue()+n2.getValue();
//				}		
//				break;
//			}
//			case 11: {//add+            
//	        	valueNew= n1.getValue()+n2.getValue();
//	            break;
//	        }
//	        case 12: { //subtract-     
//	        	valueNew= n1.getValue()-n2.getValue();
//	            break;
//	        }
//	        case 13: {//multiply*            
//	        	valueNew= n1.getValue()*n2.getValue();
//	            break;
//	        }
//	        case 14: {//divide/
//	        	if(n2.getValue()==0){
//	        		valueNew=maxValue;
//	        	}else{
//	        		valueNew= n1.getValue()/n2.getValue();
//	        	}
//	            break;
//	        }
//	        case 15:{//alternating node
//	        	valueNew=-1*value;
//	        	break;
//	        }
	        case 0:{//delaying node
	        	valueNew=n1.getValue();
	        	break;
	        }
	        case 1:{//fuzzy logical and
	        	valueNew = Math.min(n1.getValue(), n2.getValue());
	        	break;
	        }
	        case 2:{//fuzzy logical or
	        	valueNew = Math.max(n1.getValue(), n2.getValue());
	        	break;
	        }
	        case 3:{//fuzzy logical not
	        	valueNew = Math.abs(1-n1.getValue());
	        	break;
	        }
	        case 4:{//fuzzy logical xor
	        	valueNew = Math.abs(n1.getValue()-n2.getValue());
	        	break;
	        }
	        case 5:{//sensor foodPlant
	        	valueNew = c.getTile().currentFoodPlant/c.getTile().maxFood;
	        	break;
	        }
	        case 6:{//sensor xposition 
	        	valueNew = c.getxPos()/c.world.maxX;
	        	break;
	        }
	        case 7:{//sensor yposition 
	        	valueNew = c.getyPos()/c.world.maxY;
	        	break;
	        }
	        case 8:{//sensor creature front with range 10
	        		valueNew = c.seeFront(10);
	        }
	        case 9:{//sensor foodFlesh not very thought through
        		valueNew = c.getTile().getCurrentFoodFlesh()/10;
	        }
	        case 10:{//sensor foodRot not very thought through
        		valueNew = c.getTile().getCurrentFoodRot()/10;
	        }
	        case 11:{//sensor other creature approximity with range 6
        		valueNew = c.senseCreatureApproximity(6);
	        }
	        case 12: {//add modulo      
	        	if(Math.abs(n1.getValue()+n2.getValue())>1){
	        		if(n1.getValue()+n2.getValue()<0){
	        			valueNew= Math.abs(n1.getValue()+n2.getValue())+1;
	        		}else{
	        			valueNew= Math.abs(n1.getValue()+n2.getValue())-1;
	        		}		
	        	}else{
	        		valueNew= n1.getValue()+n2.getValue();
	        	}
	        	
	            break;
	        }
	        //sensor Node
		}
		if (valueNew>maxValue){
			valueNew= maxValue;
		}else{
			if (valueNew<-maxValue){
			valueNew= -maxValue;
		}
		}		
	}
}
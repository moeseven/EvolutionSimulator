package logic;
import java.util.LinkedList;

import mathematical.MyFunctions;

public class Creature{
	public LinkedList<Node> nodes;
	public World world;	
	public double energy;	
	private double health;
	private double startEnergy;
	private int maxNumberOfNodes;
	private int minNumberOfNodes;
	private int numberOfDifferentNodeKinds;
	private double mutateRate;
	private int maxFoodConsumption;
	public double xPos;
	public double yPos;
	public double strength;
	private double eaterPlant;
	private double eaterFlesh;
	private double eaterRot;
	private Creature nearestCreatureFront;
	private double lastStingerExtension;
	private double aggression;
	private double frontDist;
	///genaue Anatomie der Kreatur
	private LinkedList<Double> startValues;
	private LinkedList<Integer>	kinds;
	private LinkedList<Integer>inputNode1;
	private LinkedList<Integer>inputNode2;
	
	public Creature(World world){
		this.world=world;
		initiate();
		formRandomly();
		setUp();
	}
	public Creature(LinkedList<Double> startValues, LinkedList<Integer> kinds, LinkedList<Integer> inputNode1, LinkedList<Integer> inputNode2,World world,double strength, double eaterPlant, double eaterFlesh, double eaterRot, double aggression){
		this.world=world;
		this.startValues=startValues;
		this.kinds=kinds;
		this.inputNode1=inputNode1;
		this.inputNode2=inputNode2;
		this.strength = strength;
		this.health=strength*100;
		//this.startEnergy= 1000+strength*1000;
		this.eaterFlesh=eaterFlesh;
		this.eaterPlant=eaterPlant;
		this.eaterRot=eaterRot;
		this.aggression=aggression;
		//parameters must make sense!
		initiate();
		setUp();
	}
	
	private void initiate(){
		xPos=0;
		yPos=0;
		maxFoodConsumption = (int) (7+7*strength);
		mutateRate=0.85;//between 0 and 1
		numberOfDifferentNodeKinds=13;
		maxNumberOfNodes=50;
		minNumberOfNodes=1;
		nodes = new LinkedList<Node>();
		startEnergy=1000;
		energy = startEnergy;
	}
	public void formRandomly(){
		int nodes = (int) (Math.random()*(maxNumberOfNodes-minNumberOfNodes)+minNumberOfNodes);
		strength=Math.random();
		health=strength*100;
		aggression = Math.random();
		eaterFlesh=Math.random();
		eaterPlant= Math.random();
		eaterRot=Math.random();		
		startValues = new LinkedList<Double>();
		kinds = new LinkedList<Integer>();
		inputNode1 = new LinkedList<Integer>();
		inputNode2 = new LinkedList<Integer>();
		for(int i = 0; nodes>i;i++){
			startValues.add(Math.random());
			kinds.add((int) (Math.random()*numberOfDifferentNodeKinds));
			inputNode1.add((int) (Math.random()*nodes));
			inputNode2.add((int) (Math.random()*nodes));
		}		
	}
	private void setUp(){
		
		//normalize food adaption
		double normalizer = eaterRot+eaterPlant+eaterFlesh;
		eaterFlesh=eaterFlesh/normalizer;
		eaterPlant=eaterPlant/normalizer;
		eaterRot=eaterRot/normalizer;
		//parameters must make sense!
				if(startValues.size()==kinds.size()&&kinds.size()==inputNode1.size()&&inputNode1.size()==inputNode2.size()){
					Node n;
					for(int i=0;startValues.size()>i;i++){	
						nodes.add(new Node(this,startValues.get(i)));
					}
					for(int i=0; startValues.size()>i;i++){
						n=nodes.get(i);
						n.setKind(kinds.get(i));
						n.setN1(nodes.get(inputNode1.get(i)));
						n.setN2(nodes.get(inputNode2.get(i)));
						n.reset();
					}
				}else{
					System.out.println("ERROR -- missmatch in given Parameters!");
				}
	}
	public Creature generateDescendant(){
		//Nachkommen erzeugen mit kleinen Abweichungen und Chance auf Mutation
		//node values (deviation)
		LinkedList<Double> startValuesDeviation = new LinkedList<Double>();
		LinkedList<Integer> kindsMutation = new LinkedList<Integer>();
		LinkedList<Integer> inputNode1Mutation = new LinkedList<Integer>();
		LinkedList<Integer> inputNode2Mutation = new LinkedList<Integer>();
		double childAggression= aggression+((Math.random()-0.5)*(Math.min(mutateRate*0.03,1)));
		if(childAggression<0){childAggression =0;}
		if(childAggression>1){childAggression =1;}
		double childStrength= strength+((Math.random()-0.5)*(Math.min(mutateRate*0.03,1)));
		if(childStrength<0){childStrength =0;}
		double childEaterRot= eaterRot+((Math.random()-0.5)*(Math.min(mutateRate*0.03,1)));
		double childEaterFlesh= eaterFlesh+((Math.random()-0.5)*(Math.min(mutateRate*0.03,1)));
		double childEaterPlant= eaterPlant+((Math.random()-0.5)*(Math.min(mutateRate*0.03,1)));
		if(childEaterRot<0){childEaterRot =0;}
		if(childEaterFlesh<0){childEaterFlesh =0;}
		if(childEaterPlant<0){childEaterPlant =0;}
		
		for (int i=0;startValues.size()>i;i++){
			double v=startValues.get(i)+((Math.random()-0.5)*(Math.min(mutateRate*0.03,1)));
			if(v<0){v=0;}
			if(v>1){v=1;}
			startValuesDeviation.add(v);
		}
		for (int i=0; kinds.size()>i;i++){
			kindsMutation.add(kinds.get(i));
		}
		for(int i=0; inputNode1.size()>i;i++){
			inputNode1Mutation.add(inputNode1.get(i));
		}
		for(int i=0; inputNode2.size()>i;i++){
			inputNode2Mutation.add(inputNode2.get(i));
		}
		if(Math.random()<mutateRate){
			for(int i=0; i<2;i++){
				if(Math.random()<0.1){
					//node kind (mutation)	
					int k = (int) Math.random()*kinds.size();
					kindsMutation.remove(k);
					kindsMutation.add(k, (int) Math.random()*numberOfDifferentNodeKinds);		
				}
				if(Math.random()<0.1){
					//node linking (mutation)			
					if(Math.random()<.5){
						int l= (int) Math.random()*inputNode1Mutation.size();
						inputNode1Mutation.remove(l);
						inputNode1Mutation.add(l, (int)Math.random()*inputNode1.size());
					}else{
						int l= (int) Math.random()*inputNode2Mutation.size();
						inputNode2Mutation.remove(l);
						inputNode2Mutation.add(l, (int)Math.random()*inputNode2.size());
					}
				}
			}
			
			if(Math.random()<0.1){
				childStrength=Math.random();
			}
			if(Math.random()<0.1){
				//food preferation mutation
				childEaterFlesh=Math.random();
				childEaterPlant= Math.random();
				childEaterRot=Math.random();
			}
			if(Math.random()<0.1){
				//number of nodes (mutation)
				if(Math.random()<0.55){
					if(startValuesDeviation.size()<maxNumberOfNodes){
					startValuesDeviation.add(Math.random());
					kindsMutation.add((int) (Math.random()*numberOfDifferentNodeKinds));
					inputNode1Mutation.add((int) (Math.random()*inputNode1.size()));
					inputNode2Mutation.add((int) (Math.random()*(inputNode2.size()))); 
					}
				}else{
					if(startValuesDeviation.size()>minNumberOfNodes){
						startValuesDeviation.removeLast();
						kindsMutation.removeLast();
						inputNode1Mutation.removeLast();
						inputNode2Mutation.removeLast();
						
						// avoid pointers with index out of Range
						for(int i=0; i<inputNode1Mutation.size();i++){
							if(inputNode1Mutation.get(i)>startValues.size()-2){
								Integer a = (int)(Math.random()*(startValues.size()-2));
								inputNode1Mutation.remove(i);
								inputNode1Mutation.add(i, a);
							}
						}
						for(int i=0; i<inputNode2Mutation.size();i++){
							if(inputNode2Mutation.get(i)>startValues.size()-2){
								Integer a = (int)(Math.random()*(startValues.size()-2));
								inputNode2Mutation.remove(i);
								inputNode2Mutation.add(i, a);
							}
						}
					}
				}	
			}
			
		}			
		return new Creature(startValuesDeviation,kindsMutation,inputNode1Mutation,inputNode2Mutation,this.world,childStrength,childEaterPlant,childEaterFlesh,childEaterRot,childAggression);
	}
	public void update(double maxX, double maxY){ // needs parameters so creature stays in borders
		lastStingerExtension=this.getStingerExtension();
		operate();		
		nearestCreatureFront=null;
		frontDist = seeFront(5); //get front creature for carnivore
		stitchFront(); //not used for now
		move(maxX, maxY);
		consumeEnergy();
		notifyTile();
	}
	public void damageCreature(Creature c, double damage){
		c.setHealth(c.getHealth()-damage);
	}
	public void stitchFront(){
		if(frontDist<this.getStingerExtension()*5&&nearestCreatureFront!=null){
			if(this.getStingerExtension()>lastStingerExtension){
				damageCreature(nearestCreatureFront,1*strength);
			}		
		}
	}
	public double senseCreatureApproximity(double range){
		double retVal=range;
    	LinkedList<Tile> llt =this.world.getRelevantTilesForCalculatingCreatureVision(this);
    	for(int i=0; i<llt.size();i++){
    		for(int a=0; a<llt.get(i).creatures.size();a++){
    			double distance = MyFunctions.getDistance(llt.get(i).creatures.get(a), this);
				if(distance<range&&llt.get(i).creatures.get(a)!=this){
					retVal = distance;
				}	
    		}		        		
    	} 
    	retVal=1-(retVal/range);
    	return retVal;
	}
	public double seeFront(double range){
		double retVal=range;
    	LinkedList<Tile> llt =this.world.getRelevantTilesForCalculatingCreatureVision(this);
    	for(int i=0; i<llt.size();i++){
    		for(int a=0; a<llt.get(i).creatures.size();a++){
    			double distance = MyFunctions.getDistance(llt.get(i).creatures.get(a), this);
    			if(MyFunctions.isInFront(this, llt.get(i).creatures.get(a))){
    				if(distance<range&&llt.get(i).creatures.get(a)!=this){
    					nearestCreatureFront=llt.get(i).creatures.get(a);
    					retVal = distance;
    				}	
    			}
    		}		        		
    	} 
    	//retval is minimum Distance here
    	retVal=1-(retVal/range);
    	return retVal;
	}
	public void notifyTile(){
		this.getTile().creatures.add(this);
	}
	public void operate(){
		for(int i=0; i<nodes.size();i++){
			nodes.get(i).operate();
		}
		for(int i=0; i<nodes.size();i++){
			nodes.get(i).updateValue();
		}
	}
	public void move(double maxX, double maxY){
		//move
		double edge=0.01;
		xPos=xPos+getSpeed()*Math.cos(getDirection()*2*Math.PI);
		yPos=yPos+getSpeed()*Math.sin(getDirection()*2*Math.PI);
		if(xPos>maxX/2){
			xPos=maxX/2-edge;
		}else{
			if(xPos<-maxX/2){
				xPos=-maxX/2+edge;
			}
		}
		if(yPos>maxY/2){
			yPos=maxY/2-edge;
		}else{
			if(yPos<-maxY/2){
				yPos=-maxY/2+edge;
			}
		}
	}
	public void reset(){
		setEnergy((int) startEnergy);
		setxPos(0);
		setyPos(0);
		for(int i=0; i<nodes.size();i++){
			nodes.get(i).reset();
		}
	}
	public void checkIfToDie(){
		if(health<=0){
			world.creatures.remove(this);
			this.getTile().currentFoodFlesh+=Math.max(0, energy);
		}
	}
	public void reproduceInWorld(){
		if(energy>startEnergy*9){
			energy=energy-3*startEnergy;
			Creature c;
			c=this.generateDescendant();
			c.setxPos(xPos);
			c.setyPos(yPos);
			c.setEnergy((int)startEnergy);
			world.addCreature(c);
		}		
	}
	public void heal(){
		if(energy>2*startEnergy&&health<100*strength){
			energy-=0.02;
			health+=0.001;
		}
	}
	public void consumeEnergy(){
		if(energy>1){
			energy -= 0.04+(this.nodes.size()*0.001+strength*0.14+getSpeed()*0.01+this.getStingerExtension()*0.02);
			
		}		
		if(energy<startEnergy/2){
			health-=0.02*strength;
			if(energy<startEnergy/4){
				health=0;
			}
		}else{
			heal();
		}
		checkIfToDie();
	}
	//node0 is used for Speed
	public double getSpeed(){
		if(nodes.size()>=1){
			return nodes.get(0).getValue();
		}else{
			//System.out.println("ERROR Creature has no Nodes!");
			return 0;
		}
		
	}
	//node1 is used for direction
	public double getDirection(){
		if(nodes.size()>=2){
			return nodes.get(1).getValue();
		}else{
			//System.out.println("ERROR Creature has less than 2 Nodes!");
			return 0;
		}
		
	}
	//node2 is used for stinger
	public double getStingerExtension(){
		if(nodes.size()>=3&&aggression>0.9){
			return nodes.get(2).getValue();
		}else{
			return 0;
		}
	}
	public double getEnergy(){
		return energy;
	}
	public void setEnergy(int x){
		energy=x;
	}
	
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public Tile getTile(){
		return world.getTile(getxPos(), getyPos());
	}
	public void eatFromTile(double maxPlant,double maxFlesh,double maxRot, Tile tile){// max : max food that this creature may eat from that tile
		double eat = (Math.min(maxPlant, eaterPlant*maxFoodConsumption*(1-getSpeed())));
		if(tile.getCurrentFoodPlant()>=eat){
			tile.setCurrentFoodPlant(tile.getCurrentFoodPlant()-eat);
			energy+=eat*eaterPlant;
		}
		eat = (Math.min(maxPlant, eaterFlesh*maxFoodConsumption*(1-getSpeed())));
		if(tile.getCurrentFoodFlesh()>=eat){
			tile.setCurrentFoodFlesh(tile.getCurrentFoodFlesh()-eat);
			energy+=eat*eaterFlesh;
		}
		eat = (Math.min(maxPlant, eaterRot*maxFoodConsumption*(1-getSpeed())));
		if(tile.getCurrentFoodRot()>=eat){
			tile.setCurrentFoodRot(tile.getCurrentFoodRot()-eat);
			energy+=eat*eaterRot;
		}
		if(eaterFlesh>0.5) {
			eatFlesh();
		}
	}
	public void eatFlesh() {
		//maybe at speed as a factor
		Creature prey=null;
		if (nearestCreatureFront!=null) {
			if(nearestCreatureFront.health<health){
				if(nearestCreatureFront.strength>strength/7) {
					prey=nearestCreatureFront;
				}		
			}
		}	
		if(prey!=null) {
			energy+=prey.energy*eaterFlesh/2;
			this.getTile().setCurrentFoodFlesh(this.getTile().currentFoodFlesh+prey.energy*(1-eaterFlesh/2));
			prey.energy=0;
			prey.health=0;
			prey.checkIfToDie();
		}	
	}
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	public String toString(){
		String s = new String();
		for(int i=0; nodes.size()>i;i++){
				s+= "(#"+nodes.get(i).getValue()+")["+nodes.get(i).getKind()+"] ";
		}
		return s;
	}
	public double getEaterPlant() {
		return eaterPlant;
	}
	public double getEaterFlesh() {
		return eaterFlesh;
	}
	public double getEaterRot() {
		return eaterRot;
	}
	
}

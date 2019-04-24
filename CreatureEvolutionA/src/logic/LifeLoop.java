package logic;

import java.util.LinkedList;

import logStuff.MyLoggerle;

public class LifeLoop extends Thread{
	
	
	public void run(){
		MyLoggerle log = new MyLoggerle("D:/SimulationLogFiles/MyLogFile.log");
		int generation = 0;
		int generationsToRun = 100;
		int testWorlds = 1000;
		log.logLine("--new Simulation-- ..."+"simulating for "+generationsToRun+" generations in "+testWorlds+" Worlds");
		try {
	          sleep(3000);
	        }
	    catch(InterruptedException e) {
	    }
		LinkedList<Boolean> survivor = new LinkedList<Boolean>();
		LinkedList<World> worlds = new LinkedList<World>();
		//number of test worlds
		for(int i=0; i<testWorlds; i++){
			//test world setting
			worlds.add(new World(4,1000));
			survivor.add(false);
		}
		// add one creature to every test world
		for(int i=0; i<worlds.size();i++){
			worlds.get(i).addCreature(new Creature(worlds.get(i)));
		}
		//number of generatinos to be run
		for(int i=0; i<generationsToRun; i++){
			//number of batches
			for(int b=0; b<10; b++){
				//batch size
				for(int a=0; a<1000;a++){
					for(int c=0; c<worlds.size();c++){		
						if(survivor.get(c)==false){ //optimization so we don't calculate the same creature again
							worlds.get(c).update();
						}	
					}
				}			
				int mean=0;
				for(int d=0; d<worlds.size();d++){
					mean+=worlds.get(d).creatures.get(0).getEnergy();
				}
				mean=mean/worlds.size();
				for(int d=0; d<worlds.size();d++){
					if(d==0){
						log.log("°"+(int)(mean/10)+"°");
					}
					if(d<3 || d==worlds.size()-1 || d== worlds.size()/2){
						String output = "["+worlds.get(d).creatures.get(0).nodes.size()+"]"+(int)worlds.get(d).creatures.get(0).getEnergy()/10+"("+(int)+worlds.get(d).creatures.get(0).getxPos()+"/"+(int)+worlds.get(d).creatures.get(0).getyPos()+")";
						log.log(output);
					}				
				}
				log.logLine("");
			}
			
			//sort from best to worst
			
			LinkedList<World> sortedWorlds = new LinkedList<World>();
			int size = worlds.size();
			double minFood = -1;
			World minWorld = worlds.get(0);
			int minWorldIndex =0;
			LinkedList<Boolean> sorted = new LinkedList<Boolean>();
			for(int v=0; v<size; v++){
				sorted.add(false);
			}
			for(int v=0; v<size;v++){	
				minFood =-1;
				for(int w=0; w<worlds.size();w++){		
					if(minFood==-1){
						if(sorted.get(w)==false){
							minFood=worlds.get(w).creatures.get(0).getEnergy();
						}	
					}
					if(minFood!=-1&&sorted.get(w)==false&&worlds.get(w).creatures.get(0).getEnergy()<=minFood){
						minFood = worlds.get(w).creatures.get(0).getEnergy();
						minWorld = worlds.get(w);
						minWorldIndex=w;
					}
				}
				sortedWorlds.addFirst(minWorld);
				sorted.set(minWorldIndex, true);
								
			}
			
			//natural selection
			worlds = new LinkedList<World>();
			for(int u=0; u<sortedWorlds.size();u++){
				worlds.add(sortedWorlds.get(u));
				survivor.set(u, true);//safe surviver status to know weather we need to calculate
			}
			size = worlds.size();
			for(int u=0; u<size;u++){
				double random = Math.random();
				if(random < ((double)u)/((double)size)){
					worlds.get(u).creatures.add(worlds.get(size-u-1).creatures.get(0).generateDescendant());
					worlds.get(u).creatures.removeFirst();		
					survivor.set(u, false);
				}
			}
			for(int t=0; t<worlds.size();t++){
				worlds.get(t).reset(false); // no need to reset creatures if we dont calculate them again
			}
			generation+=1;
			log.logLine("Natural Selection Occured -- Now Generation: "+generation);
			try {
		          sleep(100);
		        }
		    catch(InterruptedException e) {
		    }
			}
			
							
		}
}

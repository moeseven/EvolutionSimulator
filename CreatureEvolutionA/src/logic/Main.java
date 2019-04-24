package logic;

public class Main {
	public static void main (String[] args){
//		Creature c1 = new Creature();
//		Creature c2 = c1.generateDescendant();
//		Creature c3 = c2.generateDescendant();
//		Creature c4 = c3.generateDescendant();
//		System.out.println(c1.toString());
//		System.out.println(c2.toString());
//		System.out.println(c3.toString());
//		System.out.println(c4.toString());
//		World w= new World(4);
//		Creature c1 = new Creature();
//		w.addCreature(c1);
//		System.out.println(w.getTileNumber(-500, -500));
//		System.out.println(w.getTileNumber(260, 260));
		LifeLoop ll = new LifeLoop();
		ll.run();
	}
}

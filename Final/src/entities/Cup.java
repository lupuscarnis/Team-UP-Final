package entities;

import entities.Die;

public class Cup {
	private int traversedSpace;
	private int rerolls;
	private Die d1 = new Die(6, 1);
	private Die d2 = new Die(6, 1);
// rough sketch of cup might need more work depending on what it has to do 
	

	public Cup(int traversedSpace, int rerolls, Die d1, Die d2) {
		super();
		this.traversedSpace = traversedSpace;
		this.rerolls = rerolls;
		this.d1 = d1;
		this.d2 = d2;
	}
	
	
	public Die getD1() {
		return d1;
	}


	public void setD1(Die d1) {
		this.d1 = d1;
	}


	public Die getD2() {
		return d2;
	}


	public void setD2(Die d2) {
		this.d2 = d2;
	}


	public int rollDice() {
		d1.roll();
		d2.roll();
		if (d1.getValue()==d2.getValue()) {

			rerolls++;
			traversedSpace += d1.getValue() + d2.getValue();
			return d1.getValue() + d2.getValue();
		} else {
			rerolls = 0;
			return d1.getValue() + d2.getValue();
		
		}

	}

	

	public int getTraversedSpace() {
		return traversedSpace;
	}

	public void setTraversedSpace(int traversedSpace) {
		this.traversedSpace = traversedSpace;
	}

	public int getRerolls() {
		return rerolls;
	}

	public void setRerolls(int rerolls) {
		this.rerolls = rerolls;
	}
public String toString() {
	return "Spaces you traveled: "+ traversedSpace+"Ammount of rerolls:  "+rerolls+"Dice: "+ d1 + " " + d2;
}
}

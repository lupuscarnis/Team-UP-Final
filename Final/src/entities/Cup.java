package entities;

import entities.Die;

public class Cup {
	private int rerolls;
	private Die d1 = new Die(6, 1);
	private Die d2 = new Die(6, 1);
// rough sketch of cup might need more work depending on what it has to do 
	
	public Cup(Player player) {

	}

	public int rollDice() {

		if (d1.roll() == d2.roll()) {

			rerolls++;
			return d1.getValue() + d2.getValue();
		} else {
			return d1.getValue() + d2.getValue();
		}

	}

	public int getRerolls() {
		return rerolls;
	}

	public void setRerolls(int rerolls) {
		this.rerolls = rerolls;
	}

}

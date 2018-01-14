package entities;

import entities.Die;

public class Cup {

	private Die d1 = new Die(6, 1);
	private Die d2 = new Die(6, 1);
	private boolean rolledDouble = false;
 
	
	
	/**
	 * Cup has two dice with faceValue.
	 * Can roll dice and return combined faceValue.
	 * Can check if rolled double.
	 */
	
	public Cup() {
		
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

	/**
	 * Method rolls the two dice and add their faceValue together.
	 * 
	 * @return combined faceValue for d1 and d2.
	 */
	public int rollDice() {
		d1.roll();
		d2.roll();

		return d1.getValue() + d2.getValue();
	
	}
	/**
	 * Method checks whether the two dice have the same face value
	 * 
	 * @return true if a double is rolled
	 */
	public boolean rolledDouble() {
		 if(d1.getValue()==d2.getValue()) {
			rolledDouble = true;
			return true;
		}
		rolledDouble = false;
		return false;
	}


	public void setRolledDouble(boolean b) {
	rolledDouble = b;
		
	}


	public boolean getRolledDouble() {
		return this.rolledDouble;
	}	
}
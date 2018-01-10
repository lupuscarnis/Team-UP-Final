package entities;
import java.math.*;

public class Die {
	private int maxValue = 6;
	private int minValue = 1;
	private int value;
	
	
	
	
	
	/**
	 * @author Nicolai Barnett
	 * @param maxValue the max number the die can roll default is 6
	 * @param minValue the minimum number the die can roll default is 1
	 */
	public Die(int maxValue, int minValue)
	{
		
		this.maxValue = maxValue;
		this.minValue = minValue;
		
	}
	
	
	public int roll()
	{
		
		 value = ((int)(Math.random()*maxValue)+minValue);
		
		return value;
	}


	public int getMaxValue() {
		return maxValue;
	}


	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}


	public int getMinValue() {
		return minValue;
	}


	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	

}

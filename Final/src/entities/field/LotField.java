package entities.field;

import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.enums.LotRentTier;

/**
 * Added by Frederik on 05-01-2018 16:04:43
 *
 * Represents lot fields.
 */
public class LotField extends Field {

	private int houseCount, hotelCount;
	private LotColor color;
	private int price;
	private int pawnPrice;
	private int housePrice;
	private int hotelPrice;
	private int[] rentTierList;

	public LotField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice) {
		super(fieldType, fieldNo, text1);

		this.price=price;
		this.setPawnPrice(pawnPrice);
	}

	public int getPrice() {
		return this.price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setHousePrice(int housePrice) {
		this.housePrice=housePrice;
		
	}

	public void setHotelPrice(int hotelPrice) {
		this.hotelPrice=hotelPrice;		
	}

	public void setRent(int[] rent) {
		this.rentTierList = rent;		
	}
	
	public LotColor getColor() {
		return color;
	}

	public void setColor(LotColor color) {
		this.color = color;
	}

	public int getPawnPrice() {
		return pawnPrice;
	}

	public void setPawnPrice(int pawnPrice) {
		this.pawnPrice = pawnPrice;
	}

	public String toString()
	{
		return super.toString() + String.format("Price: %s\n"
				+ "Color:%s\n"
				+ "Pawnprice: %s\n"
				+ "Leje af grund: %s\n"
				+ "Leje m. 1 hus: %s\n" 
				+ "Leje m. 2 huse: %s\n" 
				+ "Leje m. 3 huse: %s\n"
				+ "Leje m. 4 huse: %s\n" 
				+ "Leje m. hotel: %s\n", 
				getPrice(),
				getColor(), 
				getPawnPrice(),
				getRentFor(LotRentTier.Lot),
				getRentFor(LotRentTier.OneHouse),
				getRentFor(LotRentTier.TwoHouses),
				getRentFor(LotRentTier.ThreeHouses),
				getRentFor(LotRentTier.FourHouses),
				getRentFor(LotRentTier.Hotel));		
	}

	private int getRentFor(LotRentTier rentTier) {
		
		return rentTierList[rentTier.ordinal()];
	}

	
}

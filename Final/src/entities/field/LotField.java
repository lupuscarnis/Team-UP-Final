package entities.field;

import java.io.IOException;

import controllers.GameBoardController;
import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.enums.LotRentTier;

/**
 * Added by Frederik on 05-01-2018 16:04:43
 *
 * Represents lot fields.
 */
// TODO: There is no need for a diff. house/hotel cost price.
// Cost of building is returned via getBuildingCost()
// REMEMBER: If changed you might want to change fieldsdata.txt and FieldLoader
// too :(
public class LotField extends OwnableField {

	private int houseCount, hotelCount;
	private LotColor color;
	private int housePrice;
	private int hotelPrice;
	private int[] rentTierList;

	public LotField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice) {
		super(fieldType, fieldNo, text1, price, pawnPrice);
	}

	public int getBuildingCost() {
		return housePrice;
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

	public String toString() {
		return super.toString() + String.format(
				"Color:%s\n" + "Leje af grund: %s\n" + "Leje m. 1 hus: %s\n" + "Leje m. 2 huse: %s\n"
						+ "Leje m. 3 huse: %s\n" + "Leje m. 4 huse: %s\n" + "Leje m. hotel: %s\n",
				getColor(), getRentFor(LotRentTier.Lot), getRentFor(LotRentTier.OneHouse),
				getRentFor(LotRentTier.TwoHouses), getRentFor(LotRentTier.ThreeHouses),
				getRentFor(LotRentTier.FourHouses), getRentFor(LotRentTier.Hotel));
	}

	/**
	 * Added by Frederik on 09-01-2018 14:35:38
	 * 
	 * Returns rent for x houses or hotel.
	 * 
	 * @param rentTier
	 * @return
	 */
	public int getRentFor(LotRentTier rentTier) {
		return this.rentTierList[rentTier.ordinal()];
	}

	public int getHotelPrice() {
		return hotelPrice;
	}

	public void setHotelPrice(int hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	@Override
	public int calculateRent(int dieFaceValue) throws IOException {

		// count how many fields (LotFields) owned in same color
		int lotsOwnedInColorGroup = GameBoardController.getInstance().countLotsOwnedByColor(this.getColor(),
				this.getOwner());

		// count total lots in color group
		int lotsInColorGroupTotal = GameBoardController.getInstance().countLotsInColorGroup(this.getColor());

		// are all owned in color group?
		boolean allOwned = (lotsOwnedInColorGroup == lotsInColorGroupTotal) ? true : false;

		// calc rent
		return (allOwned) ? this.getRentFor(LotRentTier.Lot) * 2 : this.getRentFor(LotRentTier.Lot);
	}
}

package entities.field;

import java.io.IOException;
import controllers.GameBoardController;
import entities.enums.FieldType;
import entities.enums.LotColor;
import entities.enums.LotRentTier;

/**
 * Sub class of OwnableField that represents a LotField.
 *
 */
//TODO: Hvor er pris på et hus? Hvad med building cost?
public class LotField extends OwnableField {

	private int houseCount, hotelCount;
	private LotColor color;
	private int housePrice;
	private int hotelPrice;
	private int[] rentTierList;

	public LotField(FieldType fieldType, int fieldNo, String text1, int price, int pawnPrice) {
		super(fieldType, fieldNo, text1, price, pawnPrice);
	}

	/**
	 * Sets array with rent prices. 
	 * 
	 * @param rent
	 */
	public void setRent(int[] rent) {
		this.rentTierList = rent;
	}

	public LotColor getColor() {
		return this.color;
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
	 * Gets the rent according to how many houses/hotels are on the lot.
	 * 
	 * @param rentTier
	 * @return
	 */
	public int getRentFor(LotRentTier rentTier) {
		return this.rentTierList[rentTier.ordinal()];
	}

	/**
	 * Cost of building a hotel on this lot
	 * 
	 * @return
	 */
	public int getHotelPrice() {
		return this.hotelPrice;
	}

	// TODO: Is this correct?
	public int getBuildingCost() {
		return this.housePrice;
	}

	public void setHotelPrice(int hotelPrice) {
		this.hotelPrice = hotelPrice;
	}

	public void setHousePrice(int housePrice) {
		this.housePrice = housePrice;
	}

	/**
	 * Calculates rent for this type of field.
	 */
	//TODO: Udregner ikke leje rigtigt!
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

	/**
	 * Handles setting number of houses on lot
	 * 
	 * @return
	 */
	public void setHouseCount(int numHouses) {
		this.houseCount = numHouses;
	}

	/**
	 * Handles getting number of houses on lot
	 * 
	 * @return
	 */
	public int getHouseCount() {
		return this.houseCount;
	}

	/**
	 * Handles setting hotel on lot (bool)
	 * 
	 * @param numHotel
	 */
	public void setHotelCount(int numHotel) {
		this.hotelCount = numHotel;
	}

	/**
	 * Handles getting number of hotels on lot
	 * 
	 * @return
	 */
	public int getHotelCount() {
		return this.hotelCount;
	}
}

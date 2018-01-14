package entities.enums;

/**
 * Is used to indicate if a lof field has x houses/hotel or lot only when calc. rent.
 * 
 * IMPORTANT: Do not change order of constants as their ordinal is used for array lookup in getRentFor() in LotField!
 *
 */
public enum LotRentTier {
	Lot,
	OneHouse,
	TwoHouses,
	ThreeHouses,
	FourHouses,
	Hotel
}

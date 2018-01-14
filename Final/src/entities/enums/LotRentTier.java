package entities.enums;

/**
 * Is used to indicate if a lot field has x houses/hotel or if the has 0. Used for calculating rent
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

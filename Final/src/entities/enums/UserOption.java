package entities.enums;

public enum UserOption {

	//StartTurn,
	ThrowDice, 
	//StartGame, 
	PawnLot,
	UnPawnLot,
	HandleBuildings,
	BuyHotel, 
	//this is a special case, need to either allow downgrade from hotel to houses (would be bending game rules) or remove ALL hotels of that lot category
	SellHotel,
	BuyHouse, 
	SellHouse,
	//InvalidOption!! :)
	EndTurn, 
	StartOfTurn, 
	BuyLot, 
	NoThanks, 
	PayRent,
	IncomeTaxPay4000,
	IncomeTaxPayTenPercent,

}

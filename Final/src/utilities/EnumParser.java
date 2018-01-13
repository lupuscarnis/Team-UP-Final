package utilities;

import entities.enums.UserOption;

/**
 * Class is a general parser between string and enums and vice versa. 
 */
public class EnumParser {
	/**
	 * Parses user selection (string) to UserOption 
	 * 
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public static UserOption fromStrToUserOption(String result) throws Exception {

		if (fromUserOptionToStr(UserOption.BuyHotel) == result)
			return UserOption.BuyHotel;
		if (fromUserOptionToStr(UserOption.BuyHouse) == result)
			return UserOption.BuyHouse;
		if (fromUserOptionToStr(UserOption.SellHotel) == result)
			return UserOption.SellHotel;
		if (fromUserOptionToStr(UserOption.SellHouse) == result)
			return UserOption.SellHouse;
		if (fromUserOptionToStr(UserOption.EndTurn) == result)
			return UserOption.EndTurn;
		if (fromUserOptionToStr(UserOption.PawnLot) == result)
			return UserOption.PawnLot;
		if (fromUserOptionToStr(UserOption.ThrowDice) == result)
			return UserOption.ThrowDice;
		if (fromUserOptionToStr(UserOption.BuyField) == result)
			return UserOption.BuyField;
		if (fromUserOptionToStr(UserOption.NoThanks) == result)
			return UserOption.NoThanks;
		if (fromUserOptionToStr(UserOption.PayRent) == result)
			return UserOption.PayRent;
		if (fromUserOptionToStr(UserOption.IncomeTaxPay4000) == result)
			return UserOption.IncomeTaxPay4000;
		if (fromUserOptionToStr(UserOption.IncomeTaxPayTenPercent) == result)
			return UserOption.IncomeTaxPayTenPercent;
		if (fromUserOptionToStr(UserOption.bidOnField) == result)
			return UserOption.bidOnField;
		if (fromUserOptionToStr(UserOption.GetOutOfJailCard) == result)
			return UserOption.GetOutOfJailCard;
		if (fromUserOptionToStr(UserOption.PayToLeaveJail) == result)
			return UserOption.PayToLeaveJail;
		if (fromUserOptionToStr(UserOption.Unpawn) == result)
			return UserOption.Unpawn;
		throw new Exception("Translation not found!");
	}

	/**
	 * Converts from UserOption to text that can be displayed to the user.
	 * 
	 * @param option
	 * @return
	 * @throws Exception
	 */
	public static String fromUserOptionToStr(UserOption option) throws Exception {
		switch (option) {
		case PayRent:
			return "Betal leje";
		case NoThanks:
			return "Nej tak";
		case BuyField:
			return "Køb grund";
		case BuyHotel:
			return "Køb hotel";
		case BuyHouse:
			return "Køb hus";
		case SellHotel:
			return "Sælg hotel";
		case SellHouse:
			return "Sælg hus";
		case EndTurn:
			return "Afslut tur";
		case PawnLot:
			return "Pantsæt grund";
		case ThrowDice:
			return "Kast terning";
		case IncomeTaxPay4000:
			return "Betal 4000";
		case IncomeTaxPayTenPercent:
			return "Betal 10%";
		case GetOutOfJailCard:
			return "Benyt benådning";
		case PayToLeaveJail:
			return "Betal kaution";
		case bidOnField:
			return "byd på ejendommen";
		case Unpawn:
			return "Ophæv Pantsætning";
		default:
			throw new Exception("Case not found!");
		}
	}
}

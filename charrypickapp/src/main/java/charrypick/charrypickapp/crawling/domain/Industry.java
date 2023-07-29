package charrypick.charrypickapp.crawling.domain;

public enum Industry {
	STEEL("steel"),
	PETROLEUM_CHEMICAL("Petroleum/Chemical"),
	OIL_REFINING("oilrefining"),
	SECONDARY_BATTERY("secondarybattery"),
	SEMICONDUCTOR("Semiconductor"),
	DISPLAY("Display"),
	MOBILE("Mobile"),
	IT("It"),
	CAR("car"),
	SHIPBUILDING("Shipbuilding"),
	SHIPPING("Shipping"),
	FNB("FnB"),
	RETAIL_DISTRIBUTION("RetailDistribution"),
	CONSTRUCTION("Construction"),
	HOTEL_TRAVEL("HotelTravel"),
	FIBER_CLOTHING("FiberClothing");

	private final String value;

	Industry(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Industry fromValue(String value) {
		for (Industry industry : Industry.values()) {
			if (industry.value.equalsIgnoreCase(value)) {
				return industry;
			}
		}
		throw new IllegalArgumentException("Invalid Industry value: " + value);
	}
}
package owner;

public class ThirdPartyFuelPriceAPI {
    public double fetchPrice(String fuelType) {
        // Simulating an API call
        return switch (fuelType.toLowerCase()) {
            case "petrol" -> 290.0;
            case "diesel" -> 310.0;
            case "hi-octane" -> 320.0;
            default -> 0.0;
        };
    }
}
package owner;

public class FuelPriceAdapter implements FuelPriceService {
    private ThirdPartyFuelPriceAPI api;

    public FuelPriceAdapter(ThirdPartyFuelPriceAPI api) {
        this.api = api;
    }

    @Override
    public double getFuelPrice(String fuelType) {
        return api.fetchPrice(fuelType);
    }
}
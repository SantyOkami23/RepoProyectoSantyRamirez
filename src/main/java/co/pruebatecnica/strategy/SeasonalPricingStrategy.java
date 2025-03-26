package co.pruebatecnica.strategy;

public class SeasonalPricingStrategy implements PricingStrategy {
    private final double multiplier;

    public SeasonalPricingStrategy(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public double calculatePrice(double basePrice) {
        return basePrice * multiplier;
    }
}
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LuxuryCars extends Car{

    private final double luxuryTax;

    public LuxuryCars(String carId, String carBrand, String carModel, double carPricePerDay, double luxuryTax) {
        super(carId, carBrand, carModel, carPricePerDay);
        this.luxuryTax = luxuryTax;
    }
    public double getLuxuryTax() {
        return luxuryTax;
    }
    public double calculateRent(LocalDate startDate, LocalDate endDate) {
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double basePrice = super.getCarPricePerDay() * rentalDays;
        return basePrice + (basePrice * luxuryTax / 100); // Add luxury tax
    }

    @Override
    public String toString() {
        return super.toString() + " (Luxury Car with " + luxuryTax + "% luxury tax)";
    }

}

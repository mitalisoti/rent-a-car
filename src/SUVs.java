import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SUVs extends Car{
    double suvTax;

    public SUVs(String carId, String carBrand, String carModel, double carPricePerDay, double suvTax) {
        super(carId, carBrand, carModel, carPricePerDay);
        this.suvTax = suvTax;
    }
    public double getSuvTax() {
        return suvTax;
    }
    public double calculateRent(LocalDate startDate, LocalDate endDate) {
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate) +1 ;
        double basePrice = super.getCarPricePerDay() * rentalDays;
        return basePrice + (basePrice * suvTax/100);
    }
    public String toString() {
        return super.toString()+ " (SUV Car with " + suvTax + "% SUV tax)";

    }
}

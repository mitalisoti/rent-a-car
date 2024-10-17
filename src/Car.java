import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Car {
    private String carId;
    private String carBrand;
    private String carModel;
    private double carPricePerDay;
    private boolean isAvailable;

    public Car(String carId, String carBrand, String carModel, double carPricePerDay) {
        if (carPricePerDay <= 0) {
            throw new IllegalArgumentException("Car price per day must be positive.");
        }
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carPricePerDay = carPricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public double getCarPricePerDay() {
        return carPricePerDay;
    }

    public double calculateRent(LocalDate startDate, LocalDate endDate) {
        long rentalDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (rentalDays < 0) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
        return carPricePerDay * (rentalDays + 1); // Include same day rental
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        if (!isAvailable) {
            System.out.println("Car is already rented.");
        } else {
            this.isAvailable = false;
        }
    }

    public void returnCar() {
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Car ID: " + carId +
                ", Brand: " + carBrand +
                ", Model: " + carModel +
                ", Price per Day: €" + carPricePerDay;
    }
}

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CarRentalSystem {
    private final Map<String, Car> cars;
    private final Map<String, Customer> customers;
    private final Map<Car, RentalPeriod> rentals;

    public CarRentalSystem() {
        this.cars = new HashMap<>();
        this.customers = new HashMap<>();
        this.rentals = new HashMap<>();
    }

    public void addCar(Car car) {
        cars.put(car.getCarId(), car);
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerID(), customer);
    }

    public void addRental(Car car, Customer customer, LocalDate startDate, LocalDate endDate) {
        if (car.isAvailable() && isCarAvailableForPeriod(car, startDate, endDate)) {

            boolean paymentCompleted = false;
            rentals.put(car, new RentalPeriod(startDate, endDate, customer, paymentCompleted));
            System.out.println("\nCar successfully rented from " + startDate + " to " + endDate + ".");
        } else {
            System.out.println("\nSorry, the car is not available for the selected period.");
        }
    }


    private boolean isCarAvailableForPeriod(Car car, LocalDate startDate, LocalDate endDate) {
        RentalPeriod existingRental = rentals.get(car);
        if (existingRental != null) {
            if (startDate.isBefore(existingRental.getEndDate()) && endDate.isAfter(existingRental.getStartDate())) {
                return false;
            }
        }
        return true;
    }

    public void returnCar(String carId) {
        Car car = cars.get(carId);
        if (car != null && rentals.containsKey(car)) {
            RentalPeriod rentalPeriod = rentals.get(car);

            if (rentalPeriod.isPaymentCompleted()) {
                rentals.remove(car);
                car.returnCar();
                System.out.println("\nCar successfully returned.");
            } else {
                System.out.println("\nCannot return the car. Payment has not been completed yet.");
            }
        } else {
            System.out.println("\nInvalid car ID or the car was not rented.");
        }
    }


    public void listAvailableCars() {
        System.out.println("\nCurrently available cars:");
        cars.values().stream()
                .filter(Car::isAvailable)
                .forEach(System.out::println);
    }

    public void listCustomers() {
        System.out.println("\nRegistered customers:");
        customers.values().forEach(System.out::println);
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n===== Rent A Car - Best Car Rental System =====\n");
                System.out.println("1. Rent A Car");
                System.out.println("2. Return a Car");
                System.out.println("3. List Available Cars");
                System.out.println("4. List Customers");
                System.out.println("5. Exit\n");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleRent(scanner);
                        break;
                    case 2:
                        handleReturn(scanner);
                        break;
                    case 3:
                        listAvailableCars();
                        break;
                    case 4:
                        listCustomers();
                        break;
                    case 5:
                        System.out.println("Thank you for using our system.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
    }

    private void handleRent(Scanner scanner) {
        try {
            System.out.println("\n===== Rent A Car =====");

            // Email validation
            String email = "";
            while (email.isEmpty()) {
                System.out.println("\nPlease enter your email address:");
                email = scanner.nextLine();
                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    System.out.println("\nInvalid email address. Please enter a valid email.");
                    email = "";
                }else if (customers.containsKey(email)) {
                    System.out.println("\nThis email address is already registered. Please login. Please use a different email.");
                    email = "";
                }
            }

            String name = "";
            while (name.isEmpty()) {
                System.out.print("\nPlease enter your full name (alphabets only): ");
                name = scanner.nextLine();
                if (!name.matches("[a-zA-ZäöüÄÖÜß ]+")) {
                    System.out.println("\nInvalid name. Please enter alphabets only.");
                    name = "";
                }
            }
            String address = "";
            while (address.isEmpty()) {
                System.out.print("\nPlease enter your address: ");
                address = scanner.nextLine();
                if(address.isEmpty()){
                    System.out.println("\nAddress cannot be empty. Please try again.");
                    address= "";
                }
            }

            int age = 0;
            while (true) {
                System.out.print("\nPlease enter your age: ");
                try {
                    age = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (age < 18 || age > 70) {
                        System.out.println("\nSorry. You must be at least 18 years old or less than 70 years old to rent a car.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nInvalid input. Please enter a valid age.");
                    scanner.nextLine(); // consume invalid input
                }
            }

            String drivingLicense = "";
            while (true) {
                try{
                System.out.print("\nPlease enter your driving license number: ");
                drivingLicense = scanner.nextLine().trim();
                if (!drivingLicense.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$")) {
                    System.out.println("\nInvalid license number. It must contain both letters and numbers, with no special characters or spaces.");
                } else if (customers.containsKey(drivingLicense)) {
                    System.out.println("\nThis license number is already registered with us. Please login or use a different license number.");
                    email = "";
                }
                else {
                    break;
                }

            }catch(InputMismatchException e){
                    System.out.println("Invalid input. Please enter a valid license number.");
                    scanner.nextLine();
                }
            }

            LocalDate licenseExpiryDate;
            while (true) {
                try {
                    System.out.print("\nPlease enter your driving license expiry date (YYYY-MM-DD): ");
                    licenseExpiryDate = LocalDate.parse(scanner.nextLine());
                    if (licenseExpiryDate.isBefore(LocalDate.now())) {
                        System.out.println("\nThe driving license has expired. Please check and try again.");
                    } else {
                        break;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("\nInvalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            }

            String carId = "";
            Car car = null;
            while (true) {
                listAvailableCars();
                System.out.print("\nEnter the car ID you want to rent: ");
                carId = scanner.nextLine().toUpperCase();

                car = cars.get(carId);
                if (car == null || !car.isAvailable()) {
                    System.out.println("Invalid car ID or the car is not available. Please try again.");
                } else {
                    break;
                }
            }

            // Rental period input
            LocalDate startDate;
            while (true) {
                try {
                    System.out.print("\nEnter rental start date (YYYY-MM-DD): ");
                    startDate = LocalDate.parse(scanner.nextLine());
                    if (startDate.isBefore(LocalDate.now())) {
                        System.out.println("\nCannot rent in the past. Please enter a valid start date.");
                    } else {
                        break;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("\nInvalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            }

            LocalDate endDate;
            while (true) {
                try {
                    System.out.print("Enter rental end date (YYYY-MM-DD): ");
                    endDate = LocalDate.parse(scanner.nextLine());
                    if (endDate.isBefore(startDate)) {
                        System.out.println("\nEnd date must be after the start date. Please try again.");
                    } else {
                        break;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("\nInvalid date format. Please enter the date in YYYY-MM-DD format.");
                }
            }
            Customer customer = new Customer(email, name, address, age, drivingLicense, licenseExpiryDate);
            addCustomer(customer);
            long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;

            // Rent the car
            RentalPeriod rentalPeriod = new RentalPeriod(startDate, endDate, customer, false); // paymentCompleted set to false
            addRental(car, customer, startDate, endDate);
            double rentAmount = car.calculateRent(startDate, endDate);
            System.out.println("\n== Rental Information ==\n");
            System.out.println("Customer ID: " + customer.getCustomerID());
            System.out.println("Customer Name: " + customer.getCustomerName());
            System.out.println("Car: " + car.getCarBrand() + " " + car.getCarModel());
            System.out.println("Rental Days: " + rentalDays);
            System.out.println("Rental Amount: " + rentAmount);
            System.out.println("\n Confirm Rental? (Y/N): ");
            String confirm = scanner.nextLine().toUpperCase();
            if (confirm.equalsIgnoreCase("Y")) {  // Use ignoreCase for better user experience
                System.out.printf("\nRental successful! Total amount to pay: €%.2f%n", rentAmount);

                // Create a PaymentOptions instance with the current rental period
                PaymentOptions paymentOptions = new PaymentOptions(rentalPeriod);
                paymentOptions.processPayment(rentAmount);
                if(rentalPeriod.isPaymentCompleted()){
                    car.rent();
                }


            } else {
                System.out.printf("\nRental has been cancelled. Please try again if you'd like to rent a car.");
            }
                 } catch (Exception e) {
            System.out.println("\nAn error occurred: " + e.getMessage());
        }
    }

    private void handleReturn(Scanner scanner) {
        System.out.println("\n===== Return A Car =====");
        System.out.print("Enter the Car ID you want to return: ");
        String carId = scanner.nextLine().toUpperCase();
        returnCar(carId);
    }
}

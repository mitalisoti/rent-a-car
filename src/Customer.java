import java.time.LocalDate;

public class Customer {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private int customerAge;
    private String drivingLicenseNumber;
    private LocalDate licenseExpiryDate;

    public Customer(String customerID, String customerName, String customerAddress, int age, String drivingLicenseNumber, LocalDate licenseExpiryDate) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerAge = age;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getCustomerAge() {
        return customerAge;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    @Override
    public String toString() {
        return "Customer: \n" +
                "customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + customerAddress + '\'' +
                ", age= " + customerAge;
    }


}

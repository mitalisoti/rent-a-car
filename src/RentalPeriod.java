import java.time.LocalDate;

public class RentalPeriod {
    private LocalDate startDate;
    private LocalDate endDate;
    private Customer customer;
    private boolean paymentCompleted;

    public RentalPeriod(LocalDate startDate, LocalDate endDate, Customer customer, boolean paymentCompleted) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.paymentCompleted = paymentCompleted;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public Customer getCustomer() {
        return customer;
    }
    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }
    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    @Override
    public String toString() {
        return "RentalPeriod{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", customer=" + customer.getCustomerName() +
                '}';
    }
}

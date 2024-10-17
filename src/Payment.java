import java.util.Scanner;


class Payment implements Runnable {
    private final double amount;
    private final String paymentMethod;
    private boolean paymentCompleted;
    private final int countdownTime = 120; // 2 minutes countdown in seconds
    private final Scanner scanner = new Scanner(System.in);

    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentCompleted = false;
    }

    // Main thread method for payment countdown
    @Override
    public void run() {
        System.out.println("\nProcessing payment of €" + amount + " via " + paymentMethod + "...");
        System.out.println("You have 2 minutes to complete the payment. Please pay through the link sent on your email. Timer started...");

        for (int i = countdownTime; i >= 0; i--) {
            try {
                System.out.print("\rTime remaining: " + i + " seconds");
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println("\nCountdown timer interrupted.");
                return;
            }
        }

        System.out.println("\nTime is up!");
        confirmPayment(); // Ask user if payment was completed after the timer ends
    }

    // Method to confirm payment after countdown
    private void confirmPayment() {
        System.out.println("\nDid you complete the payment? (Y/N): ");
        String confirmation = scanner.nextLine().toUpperCase();

        if (confirmation.equals("Y")) {
            paymentCompleted = true;
            System.out.println("\nPayment successful!");
        } else {
            System.out.println("\nPayment failed.");
        }
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }
}

class PaymentOptions {
    private RentalPeriod rentalPeriod;

    public PaymentOptions(RentalPeriod rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public void processPayment(double amount) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSelect your payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Cash");
        System.out.println("4. GPay");

        String paymentMethod = "";
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                paymentMethod = "Credit Card";
                break;
            case 2:
                paymentMethod = "Debit Card";
                break;
            case 3:
                System.out.println("\nPlease visit our nearest branch to make the payment in cash.");
                return;
            case 4:
                paymentMethod = "GPay";
                break;
            default:
                System.out.println("\nInvalid choice. Defaulting to Credit Card.");
                paymentMethod = "Credit Card";
        }

        // Start payment process for non-cash methods using multi-threading (2-minute countdown)
        Payment payment = new Payment(amount, paymentMethod);
        Thread paymentThread = new Thread(payment);
        paymentThread.start();

        try {
            paymentThread.join();
        } catch (InterruptedException e) {
            System.out.println("\nAn error occurred while processing the payment.");
        }

        if (payment.isPaymentCompleted()) {
            System.out.println("\nPayment process completed successfully.");
            rentalPeriod.setPaymentCompleted(true); // Mark payment as completed
        } else {
            System.out.println("\nPayment was not completed.");
        }
    }
}

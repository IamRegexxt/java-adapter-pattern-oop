import java.util.Scanner;

//adaptee class
interface PaymentGateway {
    void processPayment(double amount);
}
interface PaymentStatusNotifier {
    void notifyPaymentStatus(String status);
}



class PayPal {
    void sendPayment(double amount) {
        System.out.println("Processing payment of ₱" + amount + " via PayPal");

    }
}

class Stripe {
    void charge(double amount) {
        System.out.println("Processing payment of ₱" + amount + " via Stripe");

    }
}

class PaymayaSystem {
    void makePayment(double amount) {
        System.out.println("Processing payment of ₱" + amount + " via PayMaya System");
    }
}

// Adapter classes
class PayPalAdapter implements PaymentGateway, PaymentStatusNotifier {
    private PayPal payPal;

    PayPalAdapter(PayPal payPal) {
        this.payPal = payPal;
    }

    @Override
    public void processPayment(double amount) {

        payPal.sendPayment(amount);
    }

    @Override
    public void notifyPaymentStatus(String status) {
        System.out.println("PayPal payment status " + status);
    }
}

class StripeAdapter implements PaymentGateway, PaymentStatusNotifier {
    private Stripe stripe;

    StripeAdapter(Stripe stripe) {
        this.stripe = stripe;
    }

    @Override
    public void processPayment(double amount) {
        stripe.charge(amount);
    }
    public void notifyPaymentStatus(String status) {
        System.out.println("Stripe payment status " + status);
    }

}

class PaymayaAdapter implements PaymentGateway, PaymentStatusNotifier {
    private PaymayaSystem PaymayaSystem;

    PaymayaAdapter(PaymayaSystem PaymayaSystem) {
        this.PaymayaSystem = PaymayaSystem;
    }

    @Override
    public void processPayment(double amount) {
        PaymayaSystem.makePayment(amount);
    }
    public void notifyPaymentStatus(String status) {
        System.out.println("PayPal payment status " + status);
    }
}


// interface

public class ECommerce {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available Payment Gateways:");
        System.out.println("1. PayPal");
        System.out.println("2. Stripe");
        System.out.println("3. PayMaya System");

        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();

        PaymentGateway gateway;
        switch (choice) {
            case 1:
                gateway = new PayPalAdapter(new PayPal());
                break;
            case 2:
                gateway = new StripeAdapter(new Stripe());
                break;
            case 3:
                gateway = new PaymayaAdapter(new PaymayaSystem());
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.print("Enter the amount to pay: ₱");
        double amount = scanner.nextDouble();

        gateway.processPayment(amount);
        String status = (amount > 0) ? "Success" : "Failed";


        if (gateway instanceof PaymentStatusNotifier) {
            ((PaymentStatusNotifier) gateway).notifyPaymentStatus(status);
        } else {
            System.out.println("Payment status notification not supported for this gateway.");
        }
    }

}

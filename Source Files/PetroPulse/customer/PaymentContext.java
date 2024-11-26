package customer;

public class PaymentContext {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public boolean executePayment(double amount, Customer customer) throws Exception {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment strategy not set!");
        }
        return paymentStrategy.processPayment(amount, customer);
    }
}

package customer;

public interface PaymentStrategy {
    boolean processPayment(double amount, Customer customer) throws Exception;
}

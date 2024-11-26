package customer;

public class PaymentStrategyFactory {
	private static PaymentStrategyFactory psf;
	private PaymentStrategyFactory() {};
	
	public static PaymentStrategyFactory getInstance()
	{
		if (psf == null) 
        {
            synchronized (PaymentStrategyFactory.class) 
            { // Thread safety
                if (psf == null) 
                {
                	psf = new PaymentStrategyFactory();
                }
            }
        }
        
        return psf;
	}
	
    public PaymentStrategy getPaymentStrategy(String type) {
        if (type.equalsIgnoreCase("Cash")) {
            return new PaymentByCash();
        } else if (type.equalsIgnoreCase("Card")) {
            return new PaymentByCard();
        }
        throw new IllegalArgumentException("Invalid payment type: " + type);
    }
}

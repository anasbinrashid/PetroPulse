package customer;

import application.SQLHandler;

public class PaymentByCard implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, Customer customer) throws Exception {
        SQLHandler.getInstance().buildconnection();

        // Insert payment into the database
        String query = "INSERT INTO payment (paymenttype, amount, customerid, stationid) VALUES (?, ?, ?, ?)";
        int rowsAffected = SQLHandler.getInstance().insertpayment(query, "Card", amount, customer.customerid, customer.stationid);

        if (rowsAffected > 0) {
            // Update station earnings
            query = "SELECT totalearnings FROM station WHERE stationid = ?";
            int currentEarnings = SQLHandler.getInstance().getvalue(query, customer.stationid);

            double updatedEarnings = currentEarnings + amount;
            query = "UPDATE station SET totalearnings = ? WHERE stationid = ?";
            SQLHandler.getInstance().updateearnings(query, String.valueOf(customer.stationid), String.valueOf(updatedEarnings));

            // Update customer's due payment
            customer.duepayment -= amount;
            query = "UPDATE customer SET duepayment = ? WHERE customerid = ?";
            SQLHandler.getInstance().updateearnings(query, String.valueOf(customer.customerid), String.valueOf(customer.duepayment));

            SQLHandler.getInstance().closeconnection();
            return true;
        } else {
            SQLHandler.getInstance().closeconnection();
            return false;
        }
    }
}

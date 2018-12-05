public class CreditCard implements PaymentMethod{
    public void pay(int m){
        System.out.println("Payment was Successful. Total: "+ m);
    }
}
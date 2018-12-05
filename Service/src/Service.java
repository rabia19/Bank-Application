
import java.util.*;

public class Service {
    private static int Total=0;
    private static int Pay;
    private static Scanner scanner = new Scanner(System.in);
    private static PaymentMethod payment;


    Service(Service client){
    }


    public void option() {
        System.out.print("Welcome to UBank please choose from the following Paying options" +
                "\td 1-Phone"+ "2-Onay" + "3-Internet");
        Scanner op = new Scanner(System.in);
        if(op.equals("1")){
            payPhone();
        }
        if (op.equals("2")){
            payOnay();
        }
        if (op.equals("3")){
            payInternet();
        }
        else
            System.out.println("Error...");
    }

    public void payPhone(){
        System.out.println("Please enter your phone number:");
        String number = scanner.next();

        if(number.length()==12){
            System.out.println("Processing");
            selectPaymentMethod();
    }
        else
            System.out.println("Incorrect number.Try again");
    }
    public void payOnay(){
        System.out.println("Please enter Onay pin");
        String number = scanner.next();

        if(number.length()==19){
            System.out.println("Processing");
            selectPaymentMethod();
        }
        else
            System.out.println("Incorrect.Try again");
    }
    public void payInternet() {

        System.out.println("Please enter your Internet service");
        String number = scanner.next();

    }



    private static PaymentMethod selectPaymentMethod() {
        System.out.println("Please, select a payment method ");
        System.out.println("1 - PayPal");
        System.out.println("2 - CreditCard");
        int method = scanner.nextInt();
        if (method == 1) {
            payment = new PayPal();
            Pay = 1;
        } else if (method == 2) {
            payment = new CreditCard();
            Pay = 2;
        } else {
            System.out.println("No payment method was chosen.Please select");
        }
        return payment;
    }

    private static boolean st = false;

    private static void Verification(String stop) {
        while (!stop.equals("yes") || !stop.equals("no")) {
            if (stop.equals("no")) {
                payment = selectPaymentMethod();
                if (Pay == 1) {
                    payPayPal(payment);
                    st = true;
                    break;
                } else if (Pay == 2) {
                    payCard(payment);
                    st = true;
                    break;
                }

            } else if (stop.equals("yes")) {
                break;
            }
            stop = scanner.next();


        }
    }

    private static void payPayPal(PaymentMethod payment) {

        System.out.println("Enter your email: ");

        String email = scanner.next();

        System.out.println("Enter your password: ");

        String password = scanner.next();



        if (!email.equals("example@gmail.com") || !password.equals("12345")) {
            System.out.println("Email or password is incorrect.Please try again");

        } else
            System.out.print("Payment has been successful");

    }


    private static void payCard(PaymentMethod payment) {
        System.out.println("Card Number: ");

        String Card = scanner.next();

        System.out.println("CVV Code: ");

        String CVV = scanner.next();


        if (Card.length()>16 && CVV.length()>3) {
            payment.pay(Total);
            System.out.println("Payment has been successful");

        } else {
            System.out.println("Card number or CVV is incorrect");
            System.out.println();
        }

    }

}
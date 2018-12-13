public class Services {
    \
    public String [] setOfOnay = {"9643 10 8503300000"};
    public String [] setOfInternet = {"111111"};
    public String [] setOfphoneNumbers = {"777888999"};
    static Ubank bank;

    Services(String user_name,String card_number,String password) {
        bank = new Ubank(user_name,card_number,password);
    }
   // public String password = user.getPassword();

    public String payPhoneNumber(String phoneNumberForPay,String amount){
        for (int i= 0; i<setOfphoneNumbers.length; i++){
            if (setOfphoneNumbers[i].equals(phoneNumberForPay)){
                if(bank.pay(Integer.parseInt(amount))){
                    return "Payment has been Successful!";
                }
                return "You don't have enough money!";
            }
            return "No such Phone Number!";
        }
        return null;
    }
    public String payOnay(String onayNumberForPay, String amount){
        for (int i= 0; i<setOfOnay.length; i++){
            if (setOfOnay[i].equals(onayNumberForPay)){
               if(bank.pay(Integer.parseInt(amount))){
                   return "Payment has been Succesfully!";
               }
               return "You don't have enough money!";
            }
            return "No such Onay Card!";
        }
        return null;

    }
    public String payInternet(String internetNumberforPay, String amount){
        for (int i= 0; i<setOfInternet.length; i++){
            if (setOfInternet[i].equals(internetNumberforPay)){
                if(bank.pay(Integer.parseInt(amount))){
                    return "Payment has been Succesfully!";
                }
                return "You don't have enough money!";
            }
            return "No such Internet Number!";
        }
        return null;
    }




}

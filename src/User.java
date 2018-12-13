public class User {
    private static String userName;
    private static String cardNumber;
    private static String password;
    private static int currentBalance = 700;
    private static int bonus = 150;
   // private Deposit [] deposits;
   // private Credit[] credits;

    User(String username, String cardNumber, String password) {
        this.userName = username;
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public int getCurrentBalance(){
        return currentBalance;
    }
    public int getBonus(){return bonus;}
    public String getUserName(){return userName;}
    public String getCardNumber(){return cardNumber;}
    public String getPassword(){return password;}
    public void setCurrentBalance(int newCurrentBalance){
        currentBalance = newCurrentBalance;
    }
    public void setUserName(String newUserName) {
        userName = newUserName;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }




}

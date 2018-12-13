public class Ubank implements Payment,Verification {
    public String [] setOfCards = {"000000","0000000","000000"};
    public static User user;
    public int bonus;
    public Notify notification = new Notify();

    Ubank(String username, String phoneNumber,String password){

        createUser(username, phoneNumber, password);
    }

    public void createUser(String username, String phoneNumber, String password) {
        user = new User(username,phoneNumber,password);
    }
    public double creditAmount(int amountOfCredit,int amountOfMonths){
        return (amountOfCredit  * 10.5/100) / amountOfMonths;
    }
    public String makeTransactions(String friendsCardNumber,String amount) {
        for (int i= 0; i<setOfCards.length; i++){
            if (setOfCards[i].equals(friendsCardNumber)){
                if(pay(Integer.parseInt(amount))){
                    return "Transaction has been Succesfully!";
                }
                return "You don't have enough money!";
            }
            return "No such U Card!";
        }
        return null;
    }

    public User getUser() {
        return user;
    }
    @Override
    public boolean pay(int amount) {
        if (amount<=user.getCurrentBalance()){
            user.setCurrentBalance(user.getCurrentBalance()-amount);
            return true;
        }
        return false;
     }

    @Override
    public boolean verify(String Password) {
        if(Password.equals(user.getPassword())){
            return true;
        }
        return false;
    }
}

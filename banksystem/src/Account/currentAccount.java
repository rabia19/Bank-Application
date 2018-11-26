package Account;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class currentAccount extends bankAccount{

    @Override
    public void withdraw(int accountNumber, double withdraw){
        String currentBalanceString = get.getBalance(accountNumber);
        String overdraftString = get.getOverdraft(accountNumber);

        if(currentBalanceString != null || overdraftString != null){
            double currentBalance = Double.parseDouble(get.getBalance(accountNumber));
            double overdraft = Double.parseDouble(get.getOverdraft(accountNumber));

            overdraft = - + overdraft; //Turning the overdraft into a negative number;
            Double testBalance = currentBalance - withdraw;

            String description = "";

            description = inputValidation.getBlankStringInput("Why are you withdrawing this money (Click enter to leave blank): ");

            if(currentBalance < overdraft){
                withdrawDatabaseConnection(accountNumber, testBalance, description, withdraw ); //Not Charged
            }else if(currentBalance >= overdraft){
                if(testBalance < overdraft){
                    double charge = 10;
                    String descriptionCharge = "Over overdraft charge: " + "\u00A3" + charge;
                    Double testBalanceCharge = testBalance - charge;
                    withdrawDatabaseConnection(accountNumber, testBalance, description, withdraw ); //What the customer is withdrawing
                    withdrawDatabaseConnection(accountNumber, testBalanceCharge, descriptionCharge, charge ); //Charge for going overdrawn
                    System.out.println(descriptionCharge);

                }else{
                    withdrawDatabaseConnection(accountNumber, testBalance, description, withdraw );
                }
            }
        }
    }
    public void withdrawDatabaseConnection(int accountNumber, double newBalance, String description, double withdraw ){ //AccountNumber,
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE accountstbl SET balance = '" + newBalance + "'where accountNumber = '" + accountNumber + "'");
            try{
                stmt.executeUpdate("INSERT INTO transactionstbl (accountNumber,description,moneyIn,moneyOut,balance) VALUES ('" + accountNumber + "', '" + description + "', 0, '" + withdraw + "', '" + newBalance + "')");
            } catch (SQLException e) {
                System.err.println("There was a problem adding the transaction to the transaction table.");
                System.err.println("Please contact a bank manager." + e);
            } catch (Exception e){
                System.err.println("There was a problem adding the transaction to the transaction table.");
                System.err.println("Please contact a bank manager." + e);
            }
        } catch (SQLException e) {
            System.err.println("Withdraw unsuccessful, please try again or contact a bank manager");
        } catch (Exception e){
            System.err.println("Withdraw unsuccessful, please try again or contact a bank manager");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
    }
}
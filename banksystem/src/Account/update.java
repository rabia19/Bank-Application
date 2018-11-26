package Account;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class update {
    get get = new get();

    //Customer update methods
    public void updateCustomerTitle(int customerID, String title){
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE customerTbl SET title = '" + title + "' where customerID = '" + customerID + "'");

        } catch (SQLException e) {
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } catch (Exception e){
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        System.out.println("Update successfull");
    }
    public void updateCustomerFirstName(int customerID, String firstName){
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE customerTbl SET firstName = '" + firstName + "' where customerID = + '" + customerID + "'");

        } catch (SQLException e) {
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } catch (Exception e){
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        System.out.println("Update successfull");
    }
    public void updateCustomerLastName(int customerID, String lastName){
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE customerTbl SET lastName = '" + lastName + "' where customerID = + '" + customerID + "'");

        } catch (SQLException e) {
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } catch (Exception e){
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        System.out.println("Update successfull");
    }

    //Update Account details method
    public void updateAccountInterestRate(int accountNumber, double interestRate){
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE accountstbl SET interestRate = '" + interestRate + "' where accountNumber = '" + accountNumber + "'");

        } catch (SQLException e) {
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } catch (Exception e){
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        System.out.println("Update successfull");
    }
    public void updateAccountOverdraft(int accountNumber, double overdraft) {
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE accountstbl SET overdraft = " + overdraft + " where accountNumber = '" + accountNumber + "'");

        } catch (SQLException e) {
            System.err.println("Deposit unsuccessful, please try again or contact an Admin");
        } catch (Exception e){
            System.err.println("Deposit unsuccessful, please try again or contact an Admin");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        System.out.println("Update successfull");
    }
    public void updateAccountBalance(int accountNumber, String description, double balance) {
        Double currentBalance = Double.parseDouble(get.getBalance(accountNumber));

        String stringCurrentBalance = Double.toString(currentBalance);
        String stringBalance = Double.toString(balance);

        if(!stringCurrentBalance.equals(stringBalance)){
            boolean outIn = true;
            Double difference = 0.0;
            if(currentBalance < balance){

                difference = currentBalance - (+ balance);
                difference = difference * (-1);

                System.out.println("Money in: " + difference);
                outIn = true;
            }
            if(currentBalance > balance){

                difference = balance - (+ currentBalance);
                difference = difference * (-1);

                System.out.println("Money out: " + difference);
                outIn = false;
            }
            Double moneyIn = 0.0;
            Double moneyOut = 0.0;

            if(outIn){
                moneyIn = difference;
            }else if(!outIn){
                moneyOut = difference;
            }


            Connection con = null;
            Statement stmt = null;
            try{
                con = dbConnection.getConnection();
                stmt = con.createStatement();

                stmt.executeUpdate("UPDATE accountstbl SET balance = " + balance + " where accountNumber = '" + accountNumber + "'");

                try{
                    stmt.executeUpdate("INSERT INTO transactionstbl (accountNumber,description,moneyIn,moneyOut,balance) VALUES ('" + accountNumber + "', '" + description + "', '" + moneyIn + "', '" + moneyOut + "', '" + balance + "')");
                } catch (SQLException e) {
                    System.err.println("There was a problem adding the transaction to the transaction table.");
                    System.err.println("Please contact a bank manager.");
                } catch (Exception e){
                    System.err.println("There was a problem adding the transaction to the transaction table.");
                    System.err.println("Please contact a bank manager.");
                }


            } catch (SQLException e) {
                System.err.println("Update unsuccessful, please try again or contact an Admin");
            } catch (Exception e){
                System.err.println("Update unsuccessful, please try again or contact an Admin");
            } finally{
                dbConnection.closeStmt(stmt);
                dbConnection.closeCon(con);
            }
        }else{
            System.out.println("Sorry the balance is allready: " + "\u00A3" + balance);
        }
        System.out.println("Update successfull");
    }
    public void updateCustomerPassword(String password, int custID) {
        Connection con = null;
        Statement stmt = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("UPDATE customerTbl SET password = '" + password + "' where customerId = '" + custID + "'");

        } catch (SQLException e) {
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } catch (Exception e){
            System.err.println("Update unsuccessful, please try again or contact an Admin");
        } finally{
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        System.out.println("Update successfull");
    }
}
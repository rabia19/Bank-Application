package Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class get {
    //Get the balance and returns it for other methods to use it
    public String getBalance(int accountNumber){
        String balance = null;  //Set to string, because it allows return null so it can be used to display an error from other methods

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT balance from accountstbl WHERE accountNumber = '" + accountNumber + "'" );

            if(rs.next()){
                do{
                    balance = Integer.toString(rs.getInt("balance"));
                } while(rs.next());
            } else{
                System.err.println("Sorry there was a problem retriving your balance.");
                balance = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return balance;
    }
    public String getOverdraft(int accountNumber){
        String overdraft = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT overdraft from accountstbl WHERE accountNumber = '" + accountNumber + "'" );

            if(rs.next()){
                do{
                    overdraft = Integer.toString(rs.getInt("overdraft"));
                } while(rs.next());
            } else{
                System.err.println("Sorry there was a problem retriving your overdraft.");
                overdraft = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return overdraft;
    }
    public String getCustFirstName(int customerID){
        String firstName = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT firstName FROM customertbl where customerID = '" + customerID + "'");

            while(rs.next()){
                firstName = rs.getString("firstName");
            }
        } catch (SQLException e) {
            System.err.println("Error: There was a problem retrieving you first name");
        } catch (Exception e){
            System.err.println("Error: There was a problem retrieving you first name");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return firstName;
    }
    public String getCustLastName(int customerID){
        String lastName = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT lastName FROM customertbl where customerID = '" + customerID + "'");

            while(rs.next()){
                lastName = rs.getString("lastName");
            }
        } catch (SQLException e) {
            System.err.println("Customer account was not created: Please check your using the correct customer ID");
        } catch (Exception e){
            System.err.println("Customer account was not created: Please check your using the correct customer ID ");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return lastName;
    }
    public String getBankManagerFirstName(int bankManagerID){
        String firstName = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT first_Name FROM bankAdmins where ID = '" + bankManagerID + "'");

            while(rs.next()){
                firstName = rs.getString("first_Name");
            }
        } catch (SQLException e) {
            System.err.println("Error: There was a problem retrieving you first name");
        } catch (Exception e){
            System.err.println("Error: There was a problem retrieving you first name");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return firstName;
    }
    public String getBankManagerLastName(int bankManagerID){
        String lastName = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT last_Name FROM bankAdmins where ID = '" + bankManagerID + "'");

            while(rs.next()){
                lastName = rs.getString("last_Name");
            }
        } catch (SQLException e) {
            System.err.println("Customer account was not created: Please check your using the correct customer ID");
        } catch (Exception e){
            System.err.println("Customer account was not created: Please check your using the correct customer ID ");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return lastName;
    }
    public int getTypeOfAccount(int accountNumber){
        int typeOfAccount = 0;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT accountType FROM accountsTbl where accountNumber ='" + accountNumber + "'");

            while(rs.next()){
                typeOfAccount = rs.getInt("accountType");
            }
        } catch (SQLException e) {
            System.err.println("Customer account was not created: Please check your using the correct customer ID");
        } catch (Exception e){
            System.err.println("Customer account was not created: Please check your using the correct customer ID ");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return typeOfAccount;
    }
    public String getCustTitle(int customerID){
        String title = null;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT title FROM customertbl where customerID = '" + customerID + "'");

            while(rs.next()){
                title = rs.getString("title");
            }
        } catch (SQLException e) {
            System.err.println("Customer account was not created: Please check your using the correct customer ID");
        } catch (Exception e){
            System.err.println("Customer account was not created: Please check your using the correct customer ID ");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return title;
    }
}
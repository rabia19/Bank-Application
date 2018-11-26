package Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class create {
    public void createAdmin(String firstName, String lastName, String password){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("INSERT INTO bankAdmins (first_Name,last_Name,password) VALUES ('" + firstName + "','" + lastName + "','" + password + "')");

            try{
                rs = stmt.executeQuery("SELECT * FROM bankAdmins ORDER BY ID DESC LIMIT 1");

                while(rs.next()){
                    int bankAdminID = rs.getInt("ID");
                    System.out.println("Your bank admin id is " + bankAdminID);
                }
            } catch (SQLException e){
                System.err.println("An error occured and the account number could not be retrieved");
            } catch (Exception e){
                System.err.println("An error occured and the account number could not be retrieved");
            }
        } catch (SQLException e){
            System.err.println("New customer was not created");
        } catch (Exception e){
            System.err.println("New customer was not created");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
    }
    public void createCustomer(String title, String firstName, String lastName, String password){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("INSERT INTO customertbl (title,firstName,lastName,password) VALUES ('" + title + "','" + firstName + "','" + lastName + "','" + password + "')");

            try{
                rs = stmt.executeQuery("SELECT * FROM customertbl ORDER BY customerID DESC LIMIT 1");

                while(rs.next()){
                    int customerID = rs.getInt("customerID");
                    System.out.println("Your customer id is: " + customerID);
                }
            } catch (SQLException e){
                System.err.println("An error occured and the account number could not be retrieved");
            } catch (Exception e){
                System.err.println("An error occured and the account number could not be retrieved");
            }
        } catch (SQLException e){
            System.err.println("New customer was not created");
        } catch (Exception e){
            System.err.println("New customer was not created");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
    }
    public void createAccount(int customerID, int accountType, String sortCode, double interestRate, double overdraft, double balance){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            stmt.executeUpdate("INSERT INTO accountstbl (customerID,accountType,sortCode,interestRate,overdraft,balance) VALUES (" + customerID + "," + accountType + ",'" + sortCode + "'," + interestRate + "," + overdraft + "," + balance + ")");

            try{
                rs = stmt.executeQuery("SELECT * FROM accountstbl ORDER BY accountNumber DESC LIMIT 1");

                while(rs.next()){
                    int accountNumber = rs.getInt("accountNumber");
                    System.out.println("Your customer id is: " + accountNumber);
                }
            } catch (SQLException e){
                System.err.println("An error occured and the account number could not be retrieved");
            } catch (Exception e){
                System.err.println("An error occured and the account number could not be retrieved");
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
    }
}
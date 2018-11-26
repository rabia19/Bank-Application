package Account;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class savingAccount extends bankAccount{

    @Override //I'm overriding the withdraw method because i know saving account arn't going to have an overdraft so there is no reason to get the overdraft and do the calculations.
    public void withdraw(int accountNumber, double withdraw){
        String currentBalanceString = get.getBalance(accountNumber);

        if(currentBalanceString != null){
            double currentBalance = Double.parseDouble(currentBalanceString);

            double testBalance = currentBalance - withdraw;
            if(testBalance >= 0) {
                String description = "Null";
                description = inputValidation.getBlankStringInput("Why are you withdrawing this money (Click enter to leave blank): ");

                Connection con = null;
                Statement stmt = null;

                try{
                    con = dbConnection.getConnection();
                    stmt = con.createStatement();

                    stmt.executeUpdate("UPDATE accountstbl SET balance = '" + testBalance + "'where accountNumber = '" + accountNumber + "'");
                    try{
                        stmt.executeUpdate("INSERT INTO transactionstbl (accountNumber,description,moneyIn,moneyOut,balance) VALUES ('" + accountNumber + "', '" + description + "', 0, '" + withdraw + "', '" + testBalance + "')");
                    } catch (SQLException e) {
                        System.err.println("There was a problem adding the transaction to the transaction table.");
                        System.err.println("Please contact a bank manager.");
                    } catch (Exception e){
                        System.err.println("There was a problem adding the transaction to the transaction table.");
                        System.err.println("Please contact a bank manager.");
                    }
                } catch (SQLException e) {
                    System.err.println("Withdraw unsuccessful, please try again or contact a bank manager");
                } catch (Exception e){
                    System.err.println("Withdraw unsuccessful, please try again or contact a bank manager");
                } finally{
                    dbConnection.closeStmt(stmt);
                    dbConnection.closeCon(con);
                }
                System.out.println("Withdraw successfull");
            }else{
                System.out.println("Withdraw denied");
            }
        }
    }
    public void interestRate(){ //To be ran once a day
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<String> listAccountNumber = new ArrayList<String>();
        List<Double> listInterestRate = new ArrayList<Double>();
        List<Double> listBalance = new ArrayList<Double>();
        List<Double> listInterestRateBalance = new ArrayList<Double>();

        int count = 0;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT accountNumber, interestRate, Balance FROM accountsTbl WHERE accountType = 2");

            while(rs.next()) {
                listAccountNumber.add(rs.getString("accountNumber"));
                listInterestRate.add(rs.getDouble("interestRate"));
                listBalance.add(rs.getDouble("balance"));
                count++;
            }
        } catch (SQLException e){
            System.err.println("Error pulling records for interest rate");
        } catch(Exception e){
            System.err.println("Error pulling records for interest rate");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }

        int i;
        for(i = 0; i < count; i++){
            double interest = 0;
            double balance = 0;
            double dailyInterest = 0;
            double dailyInterestBalance = 0;

            interest = listInterestRate.get(i);
            balance = listBalance.get(i);

            dailyInterest = (interest/100) / 365; //Calculates the daily interest

            dailyInterestBalance = balance * dailyInterest; //Calculates the amount of interest for a day

            listInterestRateBalance.add(dailyInterestBalance);
        }
        i = 0;
        do{
            String fileName = listAccountNumber.get(i);

            try {
                File file = new File("interestRate\\" + fileName + ".txt");

                //If file doesn't exists, then create it
                Boolean exists = true;
                if(!file.exists()) {
                    file.createNewFile();
                    exists = false;
                }
                if(exists){ //Update the data
                    Scanner fScn = new Scanner(new File("interestRate\\" + fileName + ".txt"));
                    String data;

                    Double interestRateBalance = 0.0;

                    while( fScn.hasNextLine() ){
                        data = fScn.nextLine();

                        String[] token = data.split(" ");
                        interestRateBalance = Double.parseDouble(token[1]);
                    }
                    fScn.close();

                    Double newInterestRateBalance = 0.0;
                    newInterestRateBalance = listInterestRateBalance.get(i) + interestRateBalance;

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);

                    //To replace a line in a file
                    bw.write(listAccountNumber.get(i) + " " + newInterestRateBalance);

                    bw.close();
                }else{ //Insert new data
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(listAccountNumber.get(i) + " " + listInterestRateBalance.get(i));
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }while(i<count);
    }
    public void addingInterestRate(){ //ran once a year
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("SELECT accountNumber FROM accountsTbl WHERE accountType = 2");

            List<Integer> listAccountNumber = new ArrayList<Integer>();
            int count = 0;

            while(rs.next()) {
                listAccountNumber.add(rs.getInt("accountNumber"));
                count++;
            }
            int i = 0;

            do{
                int accountNumber = listAccountNumber.get(i);

                File file = new File("interestRate\\" + accountNumber + ".txt");

                if(file.exists()) {
                    Scanner fScn = new Scanner(new File("interestRate\\" + accountNumber + ".txt"));
                    String data;

                    Double interestRateBalance = 0.0;

                    while( fScn.hasNextLine() ){
                        data = fScn.nextLine();

                        String[] token = data.split(" ");
                        interestRateBalance = Double.parseDouble(token[1]);
                    }
                    fScn.close();

                    String description = "Interest";
                    Double balance = Double.parseDouble(get.getBalance(accountNumber));
                    Double newBalance = balance + interestRateBalance;

                    try{
                        con = dbConnection.getConnection();
                        stmt = con.createStatement();

                        stmt.executeUpdate("UPDATE accountstbl SET balance = '" + newBalance + "'where accountNumber = '" + accountNumber + "'");
                        try{
                            stmt.executeUpdate("INSERT INTO transactionstbl (accountNumber,description,moneyIn,moneyOut,balance) VALUES ('" + accountNumber + "', '" + description + "', '" + interestRateBalance + "', 0, '" + newBalance + "')");
                        } catch (SQLException e) {
                            System.err.println("There was a problem adding the transaction to the transaction table.");
                            System.err.println("Please contact a bank manager.");
                        } catch (Exception e){
                            System.err.println("There was a problem adding the transaction to the transaction table.");
                            System.err.println("Please contact a bank manager.");
                        }
                    } catch (SQLException e) {
                        System.err.println("Deposit unsuccessful, please try again or contact a bank manager");
                    } catch (Exception e){
                        System.err.println("Deposit unsuccessful, please try again or contact a bank manager");
                    } finally{
                        dbConnection.closeStmt(stmt);
                        dbConnection.closeCon(con);
                    }
                    file.delete();
                }
                i++;
            }while(i < count);
        } catch (SQLException e){
            System.err.println("Error inputting records for interest rate");
        } catch(Exception e){
            System.err.println("Error inputting records for interest rate");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
    }
}
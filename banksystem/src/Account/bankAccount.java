package Account;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class bankAccount {
    Scanner in = new Scanner(System.in);
    //Validation methods
    public static inputValidation valid = new inputValidation();

    //Gets extra information a method may need
    get get = new get();

    //Method for the customer to decide what account to use
    public int accountChoice(int customerID){
        int mainAccountNumber = 0;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("Select a.accountNumber, b.accountType, a.balance, a.overdraft from accountstbl a join accounttypetbl b on a.accountType = b.accountTypeId where customerID = '" + customerID + "'");

            List<String> listCount = new ArrayList<String>();
            List<String> listAccountNumber = new ArrayList<String>();
            List<String> listAccountType = new ArrayList<String>();
            List<String> listBalance = new ArrayList<String>();
            List<String> listOverdraft = new ArrayList<String>();

            int count = 1;
            int accountNumberIndex = 0;
            while(rs.next()){
                listCount.add(Integer.toString(count++));
                listAccountNumber.add(Integer.toString(rs.getInt("accountNumber")));
                listAccountType.add(rs.getString("accountType"));
                listBalance.add(Double.toString(rs.getDouble("balance")));
                listOverdraft.add(Double.toString(rs.getDouble("overdraft")));
            }
            if(count == 1){
                mainAccountNumber = 0;
            }else{
                int lenghCount = biggestStringLength(listCount);
                int lenghAccountNumber = biggestStringLength(listAccountNumber);
                int lenghAccountType = biggestStringLength(listAccountType);
                int lenghBalance = biggestStringLength(listBalance);
                int lenghOverdraft = biggestStringLength(listOverdraft);

                //If the column title is bigger than the data
                if(lenghCount < ("ID".length())){
                    lenghCount = ("ID".length());
                }
                if(lenghAccountNumber < ("Account Number".length())){
                    lenghAccountNumber = ("Account Number".length());
                }
                if(lenghAccountType < ("Account Type".length())){
                    lenghAccountType = ("Account Type".length());
                }
                if(lenghBalance < ("Balance".length())){
                    lenghBalance = ("Balance".length());
                }
                if(lenghOverdraft < ("Overdraft".length())){
                    lenghOverdraft = ("Overdraft".length());
                }

                String formatCount = "%" + lenghCount + "s";
                String formatAccountNumber = "%" + lenghAccountNumber + "s";
                String formatAccountType = "%" + lenghAccountType + "s";
                String formatBalance = "%" + lenghBalance + "s";
                String formatOverdraft = "%" + lenghOverdraft + "s";

                int number = lenghCount + lenghAccountNumber + lenghAccountType + lenghBalance + lenghOverdraft;

                System.out.printf("  " + formatCount + "    | " + formatAccountNumber + "|  " + formatAccountType + "|  " + " " + formatBalance + "|  " + " " + formatOverdraft + " %n" ,"ID", "Account Number", "Account Type", "Balance", "Overdraft");
                String line = printLine(number, 19);
                System.out.println(line);

                for(int i = 0; i < listCount.size(); i++) {
                    String index= listCount.get(i);
                    String accountNumber = listAccountNumber.get(i);
                    String accountType = listAccountType.get(i);
                    String balance = listBalance.get(i);
                    String overdraft = listOverdraft.get(i);
                    try{
                        System.out.printf("[ " + formatCount + " ]  | " + formatAccountNumber + "|  " + formatAccountType + "|  " + "\u00A3" + formatBalance + "|  " + "\u00A3" + formatOverdraft + " %n" ,index, accountNumber, accountType, balance, overdraft);
                    } catch(Exception e){
                        System.err.println("ERROR: Format Exception");
                    }
                }

                System.out.println("");

                while(accountNumberIndex < 1){
                    accountNumberIndex = valid.getIntegerInput("Which account would you like to use:");
                }
                accountNumberIndex = accountNumberIndex - 1;
                mainAccountNumber = Integer.parseInt(listAccountNumber.get(accountNumberIndex));
            }
        } catch (SQLException e){
            System.err.println("SQLException");
        } catch (Exception e){
            System.err.println("Exception");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return mainAccountNumber;
    }

    public void printBalance(int accountNumber){
        String currentBalance = get.getBalance(accountNumber);
        if(currentBalance != null){
            System.out.println("Your balance is �" + currentBalance);
        }
    }

    public void withdraw(int accountNumber, double withdraw){
        String currentBalanceString = get.getBalance(accountNumber);
        String overdraftString = get.getOverdraft(accountNumber);

        if(currentBalanceString != null || overdraftString != null){
            double currentBalance = Double.parseDouble(get.getBalance(accountNumber));
            double overdraft = Double.parseDouble(get.getOverdraft(accountNumber));

            overdraft = - + overdraft; //Turning the overdraft into a negative number;

            double testBalance = currentBalance - withdraw;
            if(testBalance >= overdraft) {
                String description = "";
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
    public void deposit(int accountNumber, double deposit){
        String currentBalanceString = get.getBalance(accountNumber);

        if(currentBalanceString != null){
            double currentBalance = Double.parseDouble(get.getBalance(accountNumber));

            String description = "Null";

            description = inputValidation.getBlankStringInput("Why are you depositing this money (Click enter to leave blank): ");

            double newBalance = currentBalance + deposit;

            Connection con = null;
            Statement stmt = null;

            try{
                con = dbConnection.getConnection();
                stmt = con.createStatement();

                stmt.executeUpdate("UPDATE accountstbl SET balance = '" + newBalance + "'where accountNumber = '" + accountNumber + "'");
                try{
                    stmt.executeUpdate("INSERT INTO transactionstbl (accountNumber,description,moneyIn,moneyOut,balance) VALUES ('" + accountNumber + "', '" + description + "', '" + deposit + "', 0, '" + newBalance + "')");
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
            System.out.println("Deposit successfull");
        }
    }
    public void transactions(int accountNumber){
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("Select transactionID, dateAndTime, Description, moneyIn, moneyOut, balance from transactionstbl where accountNumber = '" + accountNumber + "'");

            List<String> listTransactionID = new ArrayList<String>();
            List<String> listDateAndTime = new ArrayList<String>();
            List<String> listDescription = new ArrayList<String>();
            List<String> listMoneyIn = new ArrayList<String>();
            List<String> listMoneyOut = new ArrayList<String>();
            List<String> listBalance = new ArrayList<String>();

            int count = 0;
            while(rs.next()){
                listTransactionID.add(Integer.toString(rs.getInt("transactionID")));
                listDateAndTime.add(rs.getString("dateAndTime"));
                listDescription.add(rs.getString("Description"));
                listMoneyIn.add(Double.toString(rs.getDouble("moneyIn")));
                listMoneyOut.add(Double.toString(rs.getDouble("moneyOut")));
                listBalance.add(Double.toString(rs.getDouble("balance")));
                count++;
            }
            if(count == 0){
                System.out.println("Sorry it appears there has been no transaction on this account.");
            }else{
                //Get the highest string length from an array list
                int lenghTransactionID = biggestStringLength(listTransactionID);
                int lenghDateAndTime = biggestStringLength(listDateAndTime);
                int lenghDescription = biggestStringLength(listDescription);
                int lenghMoneyIn = biggestStringLength(listMoneyIn);
                int lenghMoneyOut = biggestStringLength(listMoneyOut);
                int lenghBalance = biggestStringLength(listBalance);

                //If the column title is bigger than the data
                if(lenghTransactionID < ("ID".length())){
                    lenghTransactionID = ("ID".length());
                }
                if(lenghDateAndTime < ("Date and Time".length())){
                    lenghDateAndTime = ("ID".length());
                }
                if(lenghDescription < ("Description".length())){
                    lenghDescription = ("Description".length());
                }
                if(lenghMoneyIn < ("moneyIn".length())){
                    lenghMoneyIn = ("moneyIn".length());
                }
                if(lenghMoneyOut < ("moneyOut".length())){
                    lenghMoneyOut = ("moneyOut".length());
                }
                if(lenghBalance < ("balance".length())){
                    lenghBalance = ("balance".length());
                }

                //Creates dynamic java formatter
                String formatTransactionID = "%" + lenghTransactionID + "s";
                String formatDateAndTime = "%" + lenghDateAndTime + "s";
                String formatDescription = "%" + lenghDescription + "s";
                String formatMoneyIn = "%" + lenghMoneyIn + "s";
                String formatMoneyOut = "%" + lenghMoneyOut + "s";
                String formatBalance = "%" + lenghBalance + "s";

                //Add up all the length to pass to the line method
                int number = lenghTransactionID + lenghDateAndTime + lenghDescription + lenghMoneyIn + lenghMoneyOut + lenghBalance;

                //Prints the column headings
                System.out.printf("  " + formatTransactionID + "    | " + formatDateAndTime + "|  " + formatDescription + "|  " + " " + formatMoneyIn + "|  " + " " + formatMoneyOut + "|  " + " " + formatBalance + " %n" , "ID", "Date and Time", "Description", "moneyIn", "moneyOut", "balance");
                String line = printLine(number, 23);
                System.out.println(line);

                //Loops through to show the data
                for(int i = 0; i < listTransactionID.size(); i++) {
                    String transactionID = listTransactionID.get(i);
                    String dateAndTime = listDateAndTime.get(i);
                    String description = listDescription.get(i);
                    String moneyIn = listMoneyIn.get(i);
                    String moneyOut = listMoneyOut.get(i);
                    String balance = listBalance.get(i);
                    try{
                        System.out.printf("[ " + formatTransactionID + " ]  | " + formatDateAndTime + "|  " + formatDescription + "|  " + "\u00A3" + formatMoneyIn + "|  " + "\u00A3" + formatMoneyOut + "|  " + "\u00A3" + formatBalance + " %n" ,transactionID, dateAndTime, description, moneyIn, moneyOut, balance);
                    } catch(Exception e){
                        System.err.println("ERROR: Format Exception");
                    }
                }

                //Save to text file
                String response = "";
                while(!response.toLowerCase().equals("n") && !response.toLowerCase().equals("y")){
                    response = inputValidation.getPasswordInput("Would you like to save the transaction to a file? (Y/N)");
                }

                //Creates and or updates a text file
                if(response.toLowerCase().equals("y")){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                    //get current date time with Date()
                    Date date = new Date();

                    //Creates the file name using the current date and the account number
                    String fileName = dateFormat.format(date) + " " + accountNumber;

                    try {
                        int user = 0;
                        try{
                            con = dbConnection.getConnection();
                            stmt = con.createStatement();

                            rs = stmt.executeQuery("Select a.customerID from customertbl a join accountstbl b on a.customerID = b.customerID where accountNumber = '" + accountNumber + "'");

                            while(rs.next()){
                                user = rs.getInt("customerID");
                            }
                            rs.close();
                        } catch (SQLException e){
                            System.err.println("SQLException");
                        } catch (Exception e){
                            System.err.println("Exception");
                        } finally{
                            dbConnection.closeRs(rs);
                            dbConnection.closeStmt(stmt);
                            dbConnection.closeCon(con);
                        }


                        File file = new File("transactions\\" + fileName + ".txt");
                        // if file doesn't exists, then create it
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        FileWriter fw = new FileWriter(file.getAbsoluteFile());
                        BufferedWriter bw = new BufferedWriter(fw);

                        String writeAccountNumber = "Account Number: " + accountNumber;
                        String writeCustomerID = "Customer ID: " + user;
                        String writeName = get.getCustTitle(user) + " " + get.getCustLastName(user) + ", " + get.getCustFirstName(user);
                        String writeColumnNames = String.format("  " + formatTransactionID + "    | " + formatDateAndTime + "|  " + formatDescription + "|  " + " " + formatMoneyIn + "|  " + " " + formatMoneyOut + "|  " + " " + formatBalance + " %n" , "ID", "Date and Time", "Description", "moneyIn", "moneyOut", "balance");

                        bw.write(writeAccountNumber  + System.getProperty( "line.separator" ));
                        bw.newLine();
                        bw.write(writeCustomerID  + System.getProperty( "line.separator" ));
                        bw.write(writeName  + System.getProperty( "line.separator" ));

                        bw.newLine();
                        bw.newLine();

                        bw.write(writeColumnNames);
                        bw.write(line);
                        bw.newLine();

                        for(int i = 0; i < listTransactionID.size(); i++) {
                            String transactionID = listTransactionID.get(i);
                            String dateAndTime = listDateAndTime.get(i);
                            String description = listDescription.get(i);
                            String moneyIn = listMoneyIn.get(i);
                            String moneyOut = listMoneyOut.get(i);
                            String balance = listBalance.get(i);
                            try{
                                String data = String.format("[ " + formatTransactionID + " ]  | " + formatDateAndTime + "|  " + formatDescription + "|  " + "\u00A3" + formatMoneyIn + "|  " + "\u00A3" + formatMoneyOut + "|  " + "\u00A3" + formatBalance + " %n" ,transactionID, dateAndTime, description, moneyIn, moneyOut, balance);
                                bw.write(data);
                            } catch(Exception e){
                                System.err.println("ERROR: Format Exception");
                            }
                        }
                        System.out.println("File has been saved :)");
                        String response2 = "";
                        while(!response2.toLowerCase().equals("n") && !response2.toLowerCase().equals("y")){
                            response2 = inputValidation.getPasswordInput("Would you like to open the file? (Y/N)");
                        }
                        if(response2.toLowerCase().equals("y")){
                            java.awt.Desktop.getDesktop().edit(file);
                        }
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace(); // I'd rather declare method with throws IOException and omit this catch.
                    }
                }
                if(response.toLowerCase().equals("n")){
                    System.out.println("File not printed");
                }
            }
        } catch (SQLException e){
            System.err.println("SQLException");
        } catch (Exception e){
            System.err.println("Exception");
        }
    }
    public boolean checkAcountNumber(int accountNumber){
        boolean exist = false;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("Select * from accountstbl where accountNumber = '" + accountNumber + "'");

            int count = 0;
            while(rs.next()){
                count++;
            }
            if(count < 1){
                System.out.println("Sorry account number doensnt Exist");
                exist = false;
            }else{
                exist = true;
            }
        } catch (SQLException e){
            System.err.println("SQLException");
        } catch (Exception e){
            System.err.println("Exception");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return exist;
    }
    public boolean checkCustomerID(int customerID){
        boolean exist = false;

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();

            rs = stmt.executeQuery("Select * from customertbl where customerID = '" + customerID + "'");

            int count = 0;
            while(rs.next()){
                count++;
            }
            if(count < 1){
                System.out.println("Sorry account number doesnt Exist");
                exist = false;
            }else{
                exist = true;
            }
        } catch (SQLException e){
            System.err.println("SQLException");
        } catch (Exception e){
            System.err.println("Exception");
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
        return exist;
    }
    public static int biggestStringLength(List<String> list){
        int largestString = list.get(0).length();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).length() > largestString){
                largestString = list.get(i).length();
            }
        }
        return largestString;
    }
    public static String printLine(int calculatedSpace, int extraSpace){
        calculatedSpace += extraSpace;
        char[] c = new char[calculatedSpace];
        Arrays.fill(c, '_');
        String line = new String(c);

        return line;
    }
}

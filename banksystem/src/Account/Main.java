package Account;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import java.sql.*;
public class Main {
    public static void main(String[] args){
        dbConnection.testConnection(); //Test connection to the server

        //Login class
        login login = new login();

        //Super Class
        bankAccount bankAcc = new bankAccount();
        //Sub Classes
        final currentAccount currentAcc = new currentAccount();
        final savingAccount savingAcc = new savingAccount();

        update update = new update();
        create create = new create();
        get get = new get();

        //Validation methods
        inputValidation valid = new inputValidation();

        //To schedule the interest method
        Date date = new Date();
        Timer timer = new Timer();

        timer.schedule(new TimerTask(){
            public void run(){
                savingAcc.interestRate();
            }
        },date, 24*60*60*1000); //add 24 hours delay between job executions.

        int user;
        String pwd;
        int loginOption;

        mainMenu(); //Prints the main menu

        do{
            //Re-initialising variable on each loop
            loginOption = 0;
            user = 0;
            pwd = null;

            while (loginOption < 1 || loginOption > 3){
                loginOption = valid.getIntegerInput("Option: ");
            }

            // Switch construct
            switch (loginOption) {
                case 1: //Admin choice
                    System.out.println("");
                    while (user < 1){
                        user = valid.getIntegerInput("Username: ");
                    }

                    while(pwd == null){
                        pwd = inputValidation.getPasswordInput("Password: ");
                    }

                    System.out.println("");
                    if(login.validate_login("bankAdmins", "ID", user, pwd)){
                        System.out.println("Valid Login");
                        System.out.println("");
                        System.out.println("Hello, " + get.getBankManagerFirstName(user) + " " + get.getBankManagerLastName(user));
                        adminMenu();

                        boolean ext = true;
                        do{
                            int userOption = 0;
                            while (userOption < 1 || userOption > 10){
                                userOption = valid.getIntegerInput("Option: ");
                            }
                            switch (userOption){
                                case 1:  //Create admin account
                                    String adminFirstName = null;
                                    String adminLastName = null;
                                    String adminPassword = null;

                                    while(adminFirstName == null){
                                        adminFirstName = inputValidation.getStringInput("Please enter the first name: ");
                                    }
                                    while(adminLastName == null){
                                        adminLastName = inputValidation.getStringInput("Please enter the last name: ");
                                    }
                                    while(adminPassword == null){
                                        adminPassword = inputValidation.getPasswordInput("Please enter a password: ");
                                    }

                                    create.createAdmin(adminFirstName, adminLastName, adminPassword);
                                    break;
                                case 2: //Create Customer account
                                    String title = null;
                                    int titleNum = 0; //Used to select the user title
                                    String firstName = null;
                                    String lastName = null;
                                    String password = null;

                                    System.out.println("[1] Mr");
                                    System.out.println("[2] Mrs");
                                    System.out.println("[3] Ms");
                                    System.out.println("[4] Miss");

                                    while (titleNum < 1 || titleNum > 4){
                                        titleNum = valid.getIntegerInput("Title: ");
                                    }

                                    if(titleNum == 1){
                                        title = "Mr";
                                    }
                                    if(titleNum == 2){
                                        title = "Mrs";
                                    }
                                    if(titleNum == 3){
                                        title = "Ms";
                                    }
                                    if(titleNum == 4){
                                        title = "Miss";
                                    }

                                    while(firstName == null){
                                        firstName = inputValidation.getStringInput("Please enter the customers first name: ");
                                    }
                                    while(lastName == null){
                                        lastName = inputValidation.getStringInput("Please enter the customers last name: ");
                                    }
                                    while(password == null){
                                        password = inputValidation.getPasswordInput("Please enter a password: ");
                                    }

                                    create.createCustomer(title, firstName, lastName, password);
                                    break;
                                case 3:  //Setting up a customer account
                                    int typeOfAccount = 0;
                                    int customerID = 0;
                                    String sortCode = "12-65-32";
                                    double interestRate = 0;
                                    double overdraft = -1;
                                    double balance = -1;


                                    System.out.println("What type of account would you like to make? ");

                                    System.out.println("[1] Current Account");
                                    System.out.println("[2] Saving Account");


                                    while (typeOfAccount < 1 || typeOfAccount > 2){
                                        typeOfAccount = valid.getIntegerInput("Account type: ");
                                    }
                                    while (customerID < 1){
                                        customerID = valid.getIntegerInput("Customer ID: ");
                                    }
                                    if(typeOfAccount == 2){
                                        while (interestRate < 1){
                                            interestRate = valid.getDoubleInput("How much intrest would you like this account to have? ");
                                        }
                                    }
                                    if(typeOfAccount == 1){
                                        while (overdraft < 0){
                                            overdraft = valid.getDoubleInput("How much of an overdraft is this account going to have? ");
                                        }
                                    }
                                    while (balance < 0){
                                        balance = valid.getDoubleInput("Please set the default balance on the account: ");
                                    }

                                    create.createAccount(customerID, typeOfAccount, sortCode, interestRate, overdraft, balance);
                                    break;
                                case 4: //Updating Customer details
                                    System.out.println("Please enter the customer id, which you want to update");
                                    int customerUpdateID = 0;
                                    while (customerUpdateID < 1){
                                        customerUpdateID = valid.getIntegerInput("Customer  ID:  ");
                                    }
                                    if(bankAcc.checkCustomerID(customerUpdateID)){ //If the customer ID enter exist
                                        System.out.println("What would you like to update: ");
                                        System.out.println("");
                                        System.out.println("===============================================");
                                        System.out.println("|     Please select and option:               |");
                                        System.out.println("===============================================");
                                        System.out.println("| Options:                                    |");
                                        System.out.println("|        [1] Update Customers title           |");
                                        System.out.println("|        [2] Update Customers first name      |");
                                        System.out.println("|        [3] Update Customers last name       |");
                                        System.out.println("|        [4] Cancel update                    |");
                                        System.out.println("===============================================");

                                        int optionUpdateCustDetails = 0;
                                        while (optionUpdateCustDetails < 1 || optionUpdateCustDetails > 4){
                                            optionUpdateCustDetails = valid.getIntegerInput("Option: ");
                                        }
                                        boolean ext2 = true;
                                        switch(optionUpdateCustDetails){
                                            case 1: //Update Customer Title
                                                String titleChange = null;
                                                int titleChangeNum = 0; //Used to select the user title

                                                System.out.println("[1] Mr");
                                                System.out.println("[2] Mrs");
                                                System.out.println("[3] Ms");
                                                System.out.println("[4] Miss");

                                                while (titleChangeNum < 1 || titleChangeNum > 4){
                                                    titleChangeNum = valid.getIntegerInput("Title: ");
                                                }

                                                if(titleChangeNum == 1){
                                                    titleChange = "Mr";
                                                }
                                                if(titleChangeNum == 2){
                                                    titleChange = "Mrs";
                                                }
                                                if(titleChangeNum == 3){
                                                    titleChange = "Ms";
                                                }
                                                if(titleChangeNum == 4){
                                                    titleChange = "Miss";
                                                }

                                                update.updateCustomerTitle(customerUpdateID, titleChange);

                                                adminMenu();
                                                ext2 = false;
                                                break;
                                            case 2: //Update Customer first name
                                                String updateFirstName = null;
                                                while(updateFirstName == null){
                                                    updateFirstName = inputValidation.getStringInput("Please enter the customers first name: ");
                                                }

                                                update.updateCustomerFirstName(customerUpdateID, updateFirstName);

                                                adminMenu();
                                                ext2 = false;
                                                break;
                                            case 3: //Update Customer last name
                                                String updateLastName = null;
                                                while(updateLastName == null){
                                                    updateLastName = inputValidation.getStringInput("Please enter the customers last name: ");
                                                }

                                                update.updateCustomerLastName(customerUpdateID, updateLastName);

                                                adminMenu();
                                                ext2 = false;
                                                break;
                                            case 4:
                                                adminMenu();
                                                ext2 = false;
                                                break;
                                        }while(ext2);
                                    }
                                    break;
                                case 5: //Updating Customer Account details
                                    System.out.println("Please enter the account number, which you want to update");
                                    int accountNumberUpdate = 0;
                                    while (accountNumberUpdate < 1){
                                        accountNumberUpdate = valid.getIntegerInput("Account Number:  ");
                                    }
                                    if(bankAcc.checkAcountNumber(accountNumberUpdate)){ //If the account number enter exist
                                        System.out.println("What would you like to update: ");
                                        System.out.println("");
                                        System.out.println("===============================================");
                                        System.out.println("|     Please select and option:               |");
                                        System.out.println("===============================================");
                                        System.out.println("| Options:                                    |");
                                        System.out.println("|        [1] Update Account Interest rate     |");
                                        System.out.println("|        [2] Update Account overdraft         |");
                                        System.out.println("|        [3] Update Account balance           |"); //add transaction table
                                        System.out.println("|        [4] Cancel update                    |");
                                        System.out.println("===============================================");

                                        int optionUpdateAccountDetails = 0;
                                        while (optionUpdateAccountDetails < 1 || optionUpdateAccountDetails > 4){
                                            optionUpdateAccountDetails = valid.getIntegerInput("Option: ");
                                        }
                                        boolean ext2 = true;
                                        switch(optionUpdateAccountDetails){
                                            case 1: //Update Interest rate
                                                if(get.getTypeOfAccount(accountNumberUpdate) == 2){
                                                    double updateInterestRate = 0;
                                                    while (updateInterestRate < 1){
                                                        updateInterestRate = valid.getDoubleInput("How much intrest would you like this account to have? ");
                                                    }
                                                    update.updateAccountOverdraft(accountNumberUpdate, updateInterestRate);
                                                } else{
                                                    System.out.println("Sorry you cant apply an interest rate to this type of account.");
                                                }
                                                adminMenu();
                                                ext2 = false;
                                                break;
                                            case 2: //Update overdraft
                                                if(get.getTypeOfAccount(accountNumberUpdate) == 1){
                                                    double updateOverdraft = 0;
                                                    while (updateOverdraft < 1){
                                                        updateOverdraft = valid.getDoubleInput("How much overdraft would you like this account to have? ");
                                                    }
                                                    update.updateAccountOverdraft(accountNumberUpdate, updateOverdraft);
                                                } else{
                                                    System.out.println("Sorry you cant apply an overdraft to this type of account.");
                                                }
                                                adminMenu();
                                                ext2 = false;
                                                break;
                                            case 3: //Update Balance
                                                double updateBalance = 0;
                                                while (updateBalance < 1){
                                                    updateBalance = valid.getDoubleInput("What is the customers new balance? ");
                                                }
                                                String description = "Updated by bank manager";
                                                update.updateAccountBalance(accountNumberUpdate, description, updateBalance);
                                                adminMenu();
                                                ext2 = false;
                                                break;
                                            case 4:
                                                adminMenu();
                                                ext2 = false;
                                                break;

                                        }while(ext2);
                                    }
                                    break;
                                case 6: //Check Customers balance
                                    int accountNumberCheckBalance = 0;
                                    System.out.println("Please enter the customer account number?");

                                    while (accountNumberCheckBalance < 1){
                                        accountNumberCheckBalance = valid.getIntegerInput("Account number: ");
                                    }
                                    bankAcc.printBalance(accountNumberCheckBalance);
                                    break;
                                case 7: //Print transaction
                                    int accountNumberTransaction = 0;

                                    System.out.println("Please enter the customer account number?");

                                    while (accountNumberTransaction < 1){
                                        accountNumberTransaction = valid.getIntegerInput("Account number: ");
                                    }
                                    bankAcc.transactions(accountNumberTransaction);
                                    break;
                                case 8: //Add interest to customer accounts
                                    savingAcc.addingInterestRate();
                                    break;
                                case 9: //Show menu
                                    adminMenu();
                                    break;
                                case 10: //Login out of admin account
                                    mainMenu(); //Prints the main menu

                                    ext = false;
                                    break;
                                default:
                                    System.err.println("Invalid selection");
                                    break;
                            }
                        }while (ext);
                    }else{
                        System.err.println("Invalid");
                    }
                    break;
                case 2: //Account Holder
                    System.out.println("");
                    while (user < 1){
                        user = valid.getIntegerInput("Customer  ID:  ");
                    }
                    while(pwd == null){
                        pwd = inputValidation.getPasswordInput("Password: ");
                    }

                    System.out.println("");
                    if(login.validate_login("customertbl", "customerID", user, pwd)){
                        System.out.println("Valid Login");
                        System.out.println("");
                        System.out.println("Hello, " + get.getCustFirstName(user) + " " + get.getCustLastName(user));
                        System.out.println("");

                        int accountNumber = 0;
                        accountNumber = bankAcc.accountChoice(user);

                        if(accountNumber > 0){
                            System.out.println(accountNumber);
                            int typeOfAccount = get.getTypeOfAccount(accountNumber);

                            customerMenu();

                            boolean ext = true;
                            do{
                                int userOption = 0;
                                while (userOption < 1 || userOption > 8){
                                    userOption = valid.getIntegerInput("Option: ");
                                }
                                switch (userOption){
                                    case 1: //Check balance
                                        bankAcc.printBalance(accountNumber);
                                        break;
                                    case 2: //Deposit
                                        double deposit = -1;

                                        System.out.println("How much would you like to Deposit: ");
                                        while(deposit < 0){
                                            deposit = valid.getDoubleInput("Amount: ");
                                        }
                                        bankAcc.deposit(accountNumber, deposit);
                                        bankAcc.printBalance(accountNumber);
                                        break;
                                    case 3: //Withdraw
                                        double withdraw = -1;
                                        System.out.println("How much would you like to withdraw: ");
                                        while (withdraw < 0){
                                            withdraw = valid.getDoubleInput("Amount: ");
                                        }//Make an override class
                                        if(typeOfAccount == 1){ //Current Savings
                                            currentAcc.withdraw(accountNumber, withdraw);
                                            bankAcc.printBalance(accountNumber);
                                        }if(typeOfAccount == 2){ //Savings Account
                                        savingAcc.withdraw(accountNumber, withdraw);
                                        bankAcc.printBalance(accountNumber);
                                    }
                                        break;
                                    case 4: //Print transaction history
                                        bankAcc.transactions(accountNumber);
                                        break;
                                    case 5: //Change password
                                        String password = null;
                                        while(password == null){
                                            System.out.println("Waht would u like to change it to.");
                                            password = inputValidation.getPasswordInput("Password: ");
                                        }

                                        update.updateCustomerPassword(password, user);
                                        break;
                                    case 6: //Change account
                                        accountNumber = bankAcc.accountChoice(user);
                                        System.out.println(accountNumber);
                                        typeOfAccount = get.getTypeOfAccount(accountNumber);
                                        break;
                                    case 7: //Show menu
                                        customerMenu();
                                        break;
                                    case 8: //Login out
                                        mainMenu(); //Prints the main menu

                                        ext = false;
                                        break;
                                    default:
                                        System.err.println("Invalid selection");
                                        break;
                                }
                            }while (ext);
                        }else{
                            System.out.println("Sorry, to use this part of the system you need an account assiosated with you.");
                            System.out.println("Please conact a bank manager for more assistance.");
                            mainMenu(); //Prints the main menu
                        }
                    }else{
                        System.err.println("Invalid");
                    }
                    break;
                case 3:
                    String response = "";
                    while(!response.toLowerCase().equals("n") && !response.toLowerCase().equals("y")){
                        response = inputValidation.getPasswordInput("Are you sure you want to exit? (Y/N)");
                    }

                    if(response.toLowerCase().equals("n")){
                        System.out.println("Thanks for staying :)");
                    }
                    if(response.toLowerCase().equals("y")){
                        System.exit(0);
                    }
                    break;
                default:
                    System.err.println("Invalid selection");
                    break;
            }
        }while (loginOption != 4);
    }

    public static void mainMenu(){
        System.out.println("");
        System.out.println("======================================");
        System.out.println("|     Please select and option:      |");
        System.out.println("======================================");
        System.out.println("| Options:                           |");
        System.out.println("|        [1] Admin                   |");
        System.out.println("|        [2] Account Holder          |");
        System.out.println("|        [3] Exit                    |");
        System.out.println("======================================");
    }
    public static void adminMenu(){
        System.out.println("");
        System.out.println("===============================================");
        System.out.println("|     Please select and option:               |");
        System.out.println("===============================================");
        System.out.println("| Options:                                    |");
        System.out.println("|        [1] Create new Admin Account         |");
        System.out.println("|        [2] Create new Customer              |");
        System.out.println("|        [3] Set up an Account for a customer |");
        System.out.println("|        [4] Update Customer Details          |");
        System.out.println("|        [5] Update Customer Account details  |");
        System.out.println("|        [6] Check Customer balance           |");
        System.out.println("|        [7] Print transaction details        |");
        System.out.println("|        [8] Add interest                     |");
        System.out.println("|        [9] Show menu                        |");
        System.out.println("|        [10] Logout                          |");
        System.out.println("===============================================");
    }

    public static void customerMenu(){
        System.out.println("========================================");
        System.out.println("|     Please select and option:        |");
        System.out.println("========================================");
        System.out.println("| Options:                             |");
        System.out.println("|        [1] Check balance             |");
        System.out.println("|        [2] Deposit                   |");
        System.out.println("|        [3] Withdraw                  |");
        System.out.println("|        [4] Print transaction History |");
        System.out.println("|        [5] Change password           |");
        System.out.println("|        [6] Change account            |");
        System.out.println("|        [7] Show menu                 |");
        System.out.println("|        [8] Logout                    |");
        System.out.println("========================================");
    }

}

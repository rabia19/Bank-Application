package Account;

import java.util.EmptyStackException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inputValidation {
    public static Scanner scanner = new Scanner(System.in);

    public Boolean isType(String testStr, String type) {
        try {
            if (type.equalsIgnoreCase("float")) {
                Float.parseFloat(testStr);
            } else if (type.equalsIgnoreCase("int")) {
                Integer.parseInt(testStr);
            } else if (type.equalsIgnoreCase("double")) {
                Double.parseDouble(testStr);
            }
            return true;
        } catch(InputMismatchException e){
            return false;
        } catch(NumberFormatException e){
            return false;
        } catch(EmptyStackException e){
            return false;
        }
    }

    public int getIntegerInput(String informationText) {
        Boolean error = false;
        String userInp = "";
        do {
            System.out.println(informationText);
            userInp = scanner.nextLine();
            if (!this.isType(userInp, "int")) {
                error = true;
                System.err.println("Incorrect entry. Please input only a positive integer");
            } else {
                error = false;
            }
        } while (error == true);
        return Integer.parseInt(userInp);
    }
    public double getDoubleInput(String informationText) {
        Boolean error = false;
        String userInp = "";
        do {
            System.out.println(informationText);
            userInp = scanner.nextLine();
            if (!this.isType(userInp, "double")) {
                error = true;
                System.err.println("Incorrect entry. Please input only a positive integer");
            } else {
                error = false;
            }
        } while (error == true);
        return Double.parseDouble(userInp);
    }
    public static String getStringInput(String prompt){
        String value = null;
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z ]*");
        while (value == null){
            try{
                System.out.println(prompt);
                value = scanner.nextLine();
                Matcher matcher = pattern.matcher(value);

                if (matcher.matches()) {
                    return value;
                } else {
                    System.err.println("Oops: Please enter letters only");
                    return null;
                }
            } catch (InputMismatchException e){
                System.err.println("Incorrect entry. Please input only accepted letter.");
            } catch (EmptyStackException e){
                System.err.println("Incorrect entry. Please input only accepted letter.");
            } catch (Exception e){
                System.err.println("Incorrect entry. Please input only accepted letter.");
            }
        }
        return null;
    }
    public static String getBlankStringInput(String prompt){
        String value = null;
        Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z ]*");
        try{
            System.out.println(prompt);
            value = scanner.nextLine();
            Matcher matcher = pattern.matcher(value);
            if(value.isEmpty()){
                return "";
            }
            if (matcher.matches()) {
                return value;
            } else {
                System.err.println("Oops: Please enter letters only");
                return "";
            }
        } catch (InputMismatchException e){
            System.err.println("Incorrect entry. Please input only accepted letter.");
        } catch (EmptyStackException e){
            System.err.println("Incorrect entry. Please input only accepted letter.");
        } catch (Exception e){
            System.err.println("Incorrect entry. Please input only accepted letter.");
        }

        return "";
    }
    public static String getPasswordInput(String prompt){
        String value = null;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$");
        while (value == null){
            try{
                System.out.println(prompt);
                value = scanner.nextLine();
                Matcher matcher = pattern.matcher(value);
                if (matcher.matches()) {
                    return value;
                } else {
                    System.err.println("Oops: Please enter letters only");
                    return null;
                }
            } catch (InputMismatchException e){
                System.err.println("Oops: Please enter letters only");
                value = null;
            } catch (EmptyStackException e){
                System.err.println("Oops: Please enter letters only");
                value = null;
            } catch (Exception e){
                System.err.println("Oops: Please enter letters only");
                value = null;
            }
        }
        return value;
    }

}
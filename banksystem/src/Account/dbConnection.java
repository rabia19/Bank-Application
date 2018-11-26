package Account;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


//Standard Connection to the database for methods to use
public class dbConnection {
    public static Connection getConnection() {
        Properties props = new Properties();
        FileInputStream fis = null;
        Connection con = null;
        try {
            fis = new FileInputStream("dbBank.properties");
            props.load(fis);

            // load the Driver Class
            Class.forName(props.getProperty("DB_DRIVER_CLASS"));

            // create the connection now
            con = DriverManager.getConnection(
                    props.getProperty("DB_URL"),
                    props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD")
            );
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //Method to test the connection before the program runs
    public static void testConnection(){
        Properties props = new Properties();
        FileInputStream fis = null;

        try{
            fis = new FileInputStream("dbBank.properties");
            props.load(fis);
            // load the Driver Class
            Class.forName(props.getProperty("DB_DRIVER_CLASS"));
            // create the connection now
            DriverManager.getConnection(props.getProperty("DB_URL"),
                    props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD"));
            System.out.println("Database is connected !");

        } catch(Exception e){
            System.out.println("Do not connect to DB - Error:"+e);
            System.out.println("");
            System.err.println("Program has been ended due to connectivity problems with the server.");

            System.exit(0); //End the program if it does not connect to the server
        }
    }

    //Closing
    public static void closeCon(Connection con){
        if (con != null){
            try{
                con.close();
            }
            catch (SQLException e){/* ignored */}
        }
    }
    public static void closeRs(ResultSet rs){
        if (rs != null){
            try{
                rs.close();
            }
            catch (SQLException e){/* ignored */}
        }
    }
    public static void closeStmt(Statement stmt){
        if (stmt != null){
            try{
                stmt.close();
            }
            catch (SQLException e){/* ignored */}
        }
    }
}
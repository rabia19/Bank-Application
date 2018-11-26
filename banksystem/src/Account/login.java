package Account;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login {
    public boolean validate_login(String accountType, String idColumn, int account_Number, String password) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            con = dbConnection.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("Select * from " + accountType + " Where " + idColumn + "='" + account_Number + "' and BINARY password='" + password + "'");

            if(rs.next()) {
                return true;
            }else{
                return false;
            }
        } catch (SQLException e){
            System.err.println("New customer was not created");
            return false;
        } catch(Exception e){
            return false;
        } finally{
            dbConnection.closeRs(rs);
            dbConnection.closeStmt(stmt);
            dbConnection.closeCon(con);
        }
    }
}


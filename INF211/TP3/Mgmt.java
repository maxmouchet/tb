import java.sql.*;
import java.util.*;

public class Mgmt {
    private StatementDelegate sd;

    public Mgmt(String login, String password, int empNo) throws SQLException, EmployeeNotExists, ConnectionException {
        sd = StatementDelegate.getConnection(login, password);
        displayManager(empNo);
        sd.closeConnection();
    }

    public static void main(String[] args) throws EmployeeNotExists, ConnectionException, SQLException {
        new Mgmt(args[0], args[1], Integer.parseInt(args[2]));
    }

    private void displayManager(int empNo) throws EmployeeNotExists, SQLException {
        ResultSet rs = sd.executeQuery("SELECT chiefname, collabname " +
                                         "FROM management WHERE chiefno = " + empNo);

        if (rs.next()) {
            System.out.println("Collaborators of " + rs.getString("chiefname") + ":");

            do {
                System.out.println(" - " + rs.getString("collabname"));
            } while (rs.next());
        } else {
            throw new EmployeeNotExists();
        }
        
        sd.closeAll();
    }
}

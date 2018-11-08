package APIv1.databaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {

    public Connection getdbms() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_farming", "root", "ashish");
        } catch (Exception e1) {
            System.out.println(e1);
        }
        return con;
    }
}

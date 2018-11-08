package APIv1.operations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Path("csv")
public class DbToCSV {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getit() {
        String filename = "Desktop:test.csv";
        String s = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_farming", "root", "ashish");
            String query = "select * from temp_logs";
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // fw.append(rs.getString("farmer_name"));
                s = s.concat(rs.getString("farmer_name"));
                ///fw.append(',');
                s = s.concat("','");
                // fw.append(rs.getString("date"));
                s = s.concat(rs.getString("date"));
                // fw.append(',');
                s = s.concat("','");
                // fw.append(rs.getString("value"));
                s = s.concat(rs.getString("value"));
                //fw.append('\n');
                s = s.concat("'\n'");
            }
            conn.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(s);
        return s;
    }
}
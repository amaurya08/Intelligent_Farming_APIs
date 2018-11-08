package APIv1.operations;

import APIv1.databaseHelper.database;
import APIv1.pojo.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Path("add")
public class addSensor {
    private String errorString;
    private String fid;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ArrayList<ResultResponse> addSensor(@FormParam("sid") String sensorID,
                                               @FormParam("email") String email) {
        ArrayList<ResultResponse> result = new ArrayList<>();
        System.out.print("Hello World");
        String queryFID = "SELECT\n" +
                "  `farmer_id`\n" +
                "FROM\n" +
                "  `smart_farming`.`farmer_details` WHERE farmer_email = ?";

        String queryInsert = "INSERT INTO `smart_farming`.`sensor_allotted` (\n" +
                "  `sid`,\n" +
                "  `farmer_id`,\n" +
                "  `current_value`,\n" +
                "  `last_updated`\n" +
                ")\n" +
                "VALUES\n" +
                "  (\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?,\n" +
                "    ?\n" +
                "  );\n" +
                "\n";
        try {
            Connection con = new database().getdbms();
            PreparedStatement stmt = con.prepareStatement(queryFID);
            stmt.setString(1, email);
            System.out.print(stmt.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                fid = resultSet.getString("farmer_id");
                System.out.print(fid + "");
                stmt = con.prepareStatement(queryInsert);
                stmt.setString(1, sensorID);
                stmt.setString(2, fid);
                stmt.setString(3, "0");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                stmt.setString(4, dateTime);
                stmt.executeUpdate();
                result.add(new ResultResponse("false", "Added Successfully"));
                System.out.print(stmt.toString());
            } else {
                errorString = "There is Some Error";
                //  result.add(new ResultResponse("false",errorString));
            }
            con.close();
        } catch (Exception e) {
            errorString = e.getMessage();
            System.out.print(errorString);
            result.add(new ResultResponse("true", errorString));
        }

        return result;
    }

}

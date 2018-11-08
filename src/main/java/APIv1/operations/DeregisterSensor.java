package APIv1.operations;

import APIv1.databaseHelper.database;
import APIv1.pojo.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Path("removeSensor")
public class DeregisterSensor {
    private String errorString;
    private String fid;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ArrayList<ResultResponse> addSensor(@FormParam("sid") String sensorID,
                                               @FormParam("email") String email) {
        ArrayList<ResultResponse> result = new ArrayList<>();

        String queryFID = "SELECT\n" +
                "  `farmer_id`\n" +
                "FROM\n" +
                "  `smart_farming`.`farmer_details` WHERE farmer_email = ?";

        String queryInsert = "DELETE\n" +
                "FROM\n" +
                "  `smart_farming`.`sensor_allotted`\n" +
                "WHERE `sid` = ?\n" +
                "  AND `farmer_id` = ?;\n";
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

                System.out.print(stmt.toString());
                result.add(new ResultResponse("false", "Removed Successfully"));
                stmt.executeUpdate();

            } else {
                errorString = "There is Some Error";
                //  result.add(new ResultResponse("false",errorString));
            }
            con.close();
        } catch (Exception e) {
            errorString = e.getMessage();
            System.out.print(errorString);
            result.add(new ResultResponse("false", errorString));
        }

        return result;
    }
}

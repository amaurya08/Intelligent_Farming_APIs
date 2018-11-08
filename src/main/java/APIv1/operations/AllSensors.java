package APIv1.operations;

import APIv1.databaseHelper.database;
import APIv1.pojo.ResultAllSensor;
import APIv1.pojo.SensorPojo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Path("allsensors")
public class AllSensors {
    private String errorString;
    private String fid;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ArrayList<ResultAllSensor> getAll(@FormParam("sid") String sid,
                                             @FormParam("email") String email) {
        ArrayList<ResultAllSensor> result = new ArrayList<>();
        String queryFID = "SELECT\n" +
                "  `farmer_id`\n" +
                "FROM\n" +
                "  `smart_farming`.`farmer_details` WHERE farmer_email = ?";
        String querySelect = "SELECT * FROM \n" +
                "sensor_allotted JOIN sensor\n" +
                "ON sensor.`sensor_id` = sensor_allotted.sid\n" +
                "WHERE " +
                "farmer_id = ?";
        try {
            Connection con = new database().getdbms();
            PreparedStatement stmt = con.prepareStatement(queryFID);
            stmt.setString(1, email);
            System.out.print(stmt.toString());
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                fid = resultSet.getString("farmer_id");
                stmt = con.prepareStatement(querySelect);
                stmt.setString(1, fid);
                System.out.print(stmt.toString());
                ResultSet resultSet1 = stmt.executeQuery();
                ArrayList<SensorPojo> allsensors = new ArrayList<>();
                if (resultSet1.isBeforeFirst()) {
                    while (resultSet1.next()) {
                        allsensors.add(new SensorPojo(resultSet1.getString("sid"),
                                resultSet1.getString("farmer_id"),
                                resultSet1.getString("current_value"),
                                resultSet1.getString("last_updated"),
                                resultSet1.getString("sensor_name")));
                    /*result.add(new ResultAllSensor("false", "",
                            new SensorPojo(resultSet1.getString("sid"),
                                    resultSet1.getString("farmer_id"),
                                    resultSet1.getString("current_value"),
                                    resultSet1.getString("last_updated"),
                                    resultSet1.getString("sensor_id"),
                                    resultSet1.getString("sensor_name"))));*/
                    }
                    result.add(new ResultAllSensor("false", allsensors));
                } else {
                    System.out.print("Empty Result Set");
                    result.add(new ResultAllSensor("true", "No Sensors Used"));
                }

            } else {
                errorString = "There is Some Error";
                //  result.add(new ResultResponse("false",errorString));
            }
            con.close();
        } catch (Exception e) {
            errorString = e.getMessage();
            System.out.print(errorString);
            result.add(new ResultAllSensor("false", errorString));
        }
        return result;
    }
}

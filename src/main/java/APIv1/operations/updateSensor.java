package APIv1.operations;

import APIv1.databaseHelper.database;
import APIv1.pojo.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Path("updateSensor")
public class updateSensor {
    private String errorString;
    private String fid;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ArrayList<ResultResponse> setSensor(@FormParam("sid") String sensorID,
                                               @FormParam("email") String email,
                                               @FormParam("value") String value) {
        ArrayList<ResultResponse> arrayList = new ArrayList<>();
        String queryFID = "SELECT\n" +
                "  `farmer_id`\n" +
                " FROM\n" +
                "  `smart_farming`.`farmer_details` WHERE farmer_email = ?";

        String queryInsert = "UPDATE\n" +
                "  `smart_farming`.`sensor_allotted`\n" +
                "SET\n" +
                "  `current_value` = ?,\n" +
                "  `last_updated` = ?\n" +
                "WHERE `sid` = ?\n" +
                "  AND `farmer_id` = ?;\n";

        try {
            Connection con = new database().getdbms();
            PreparedStatement stmt = con.prepareStatement(queryFID);
            stmt.setString(1, email);
            /*System.out.print(stmt.toString());*/
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {

                fid = resultSet.getString("farmer_id");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                stmt = con.prepareStatement(queryInsert);
                stmt.setString(1, value);
                stmt.setString(2, dateTime);
                stmt.setString(3, sensorID);
                stmt.setString(4, fid);
                if (stmt.executeUpdate() > 0)
                    arrayList.add(new ResultResponse("true", "Added Successfully"));
                else
                    arrayList.add(new ResultResponse("false", "Sensor Not Addded"));

            } else {
                errorString = "There is Some Error";
                //  result.add(new ResultResponse("false",errorString));
            }
            con.close();
        } catch (Exception e) {
            errorString = e.getMessage();
            System.out.print(errorString);
            arrayList.add(new ResultResponse("false", errorString));
        }
        addLogs(sensorID, email, value);
        return arrayList;
    }

    private void addLogs(String sensorID, String email, String value) {
        String querySelect = "SELECT\n" +
                "  `sensor_name`\n" +
                "FROM\n" +
                "  `smart_farming`.`sensor`" +
                " WHERE sensor_id = ?";
        String farmerNameQuery = "SELECT\n" +
                "  `farmer_name` \n" +
                "FROM\n" +
                "  `smart_farming`.`farmer_details`\n" +
                " WHERE farmer_email = ?";
        String insertQuery = "";
        String farmerName = "";
        String sensorName = "";
        Connection con = null;
        try {
            con = new database().getdbms();
        } catch (Exception e) {
            errorString = e.getMessage();
            System.out.println(errorString);
        }

        try {
            PreparedStatement statement = con.prepareStatement(farmerNameQuery);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                farmerName = resultSet.getString("farmer_name");
            }
            statement = con.prepareStatement(querySelect);
            statement.setString(1, sensorID);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sensorName = resultSet.getString("sensor_name");
            }
            System.out.println("Inserting "+sensorName+" Sensor");
            if (sensorName.charAt(0) == 'T') {
                insertQuery = "INSERT INTO `smart_farming`.`temp_logs` (\n" +
                        "  `farmer_name`,\n" +
                        "  `date`,\n" +
                        "  `value`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "  (\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    ?\n" +
                        "  );";
                statement = con.prepareStatement(insertQuery);
                statement.setString(1, farmerName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                statement.setString(2, dateTime);
                statement.setString(3, value);
                statement.executeUpdate();
            } else if (sensorName.charAt(0) == 'H') {

                insertQuery = "INSERT INTO `smart_farming`.`humidity_logs` (\n" +
                        "  `farmer_name`,\n" +
                        "  `values`,\n" +
                        "  `date`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "  (\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    ?\n" +
                        "  );\n" +
                        "\n";
                statement = con.prepareStatement(insertQuery);
                statement.setString(1, farmerName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                statement.setString(3, dateTime);
                statement.setString(2, value);
                statement.executeUpdate();
            } else if (sensorName.charAt(0) == 's') {
                insertQuery = "INSERT INTO `smart_farming`.`soil_logs` (\n" +
                        "  `farmer_name`,\n" +
                        "  `value`,\n" +
                        "  `date`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "  (\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    ?\n" +
                        "  );\n" +
                        "\n";
                statement = con.prepareStatement(insertQuery);
                statement.setString(1, farmerName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                statement.setString(3, dateTime);
                statement.setString(2, value);
                statement.executeUpdate();
            } else if (sensorName.charAt(0) == 'P') {
                insertQuery = "INSERT INTO `smart_farming`.`relay_logs` (\n" +
                        "  `farmer_name`,\n" +
                        "  `value`,\n" +
                        "  `data`\n" +
                        ")\n" +
                        "VALUES\n" +
                        "  (\n" +
                        "    ?,\n" +
                        "    ?,\n" +
                        "    ?\n" +
                        "  );\n" +
                        "\n";
                statement = con.prepareStatement(insertQuery);
                statement.setString(1, farmerName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                statement.setString(3, dateTime);
                statement.setString(2, value);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            errorString = e.getMessage();
            System.out.println(errorString);
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<ResultResponse> getsetSensor(@QueryParam("sid") String sensorID,
                                               @QueryParam("email") String email,
                                               @QueryParam("value") String value) {
        ArrayList<ResultResponse> arrayList = new ArrayList<>();
        String queryFID = "SELECT\n" +
                "  `farmer_id`\n" +
                " FROM\n" +
                "  `smart_farming`.`farmer_details` WHERE farmer_email = ?";

        String queryInsert = "UPDATE\n" +
                "  `smart_farming`.`sensor_allotted`\n" +
                "SET\n" +
                "  `current_value` = ?,\n" +
                "  `last_updated` = ?\n" +
                "WHERE `sid` = ?\n" +
                "  AND `farmer_id` = ?;\n";

        try {
            Connection con = new database().getdbms();
            PreparedStatement stmt = con.prepareStatement(queryFID);
            stmt.setString(1, email);
            /*System.out.print(stmt.toString());*/
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {

                fid = resultSet.getString("farmer_id");
                String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                stmt = con.prepareStatement(queryInsert);
                stmt.setString(1, value);
                stmt.setString(2, dateTime);
                stmt.setString(3, sensorID);
                stmt.setString(4, fid);
                if (stmt.executeUpdate() > 0)
                    arrayList.add(new ResultResponse("false", "Added Successfully"));
                else
                    arrayList.add(new ResultResponse("true", "Sensor Not Addded"));

            } else {
                errorString = "There is Some Error";
                //  result.add(new ResultResponse("false",errorString));
            }
            con.close();
        } catch (Exception e) {
            errorString = e.getMessage();
            System.out.print(errorString);
            arrayList.add(new ResultResponse("false", errorString));
        }
        addLogs(sensorID, email, value);
        return arrayList;
    }

}

package APIv1.operations;


import APIv1.databaseHelper.database;
import APIv1.pojo.Farmer;
import APIv1.pojo.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
//Done
@Path("signup")
public class signup {
    private String errorString;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<ResultResponse> signUp(@FormParam("name") String name,
                                            @FormParam("mobile") String mobile,
                                            @FormParam("email") String email,
                                            @FormParam("password") String password,
                                            @FormParam("gender") String gender) {

        Farmer farmer = new Farmer(name, mobile, email, gender, password);
        System.out.println(farmer.toString());
        ArrayList<ResultResponse> result = new ArrayList<ResultResponse>();
        try {
            if (insertIntoTables(farmer)) {
                result.add(new ResultResponse("false", "Successfully Registered"));
            } else {
                result.add(new ResultResponse("true", errorString));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
        // return farmer;
    }

    private boolean insertIntoTables(Farmer farmer) throws SQLException {
        boolean result = false;
        Connection con=null;
        try {
            con = new database().getdbms();
            String s1 = "INSERT INTO `smart_farming`.`farmer_details` (\n" +
                    "  `farmer_name`,\n" +
                    "  `farmer_mobile`,\n" +
                    "  `farmer_email`,\n" +
                    "  `farmer_gender`\n" +
                    ")\n" +
                    "VALUES\n" +
                    "  (\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?\n" +
                    "  );";

            PreparedStatement preparedStmt = con.prepareStatement(s1);
            preparedStmt.setString(1, farmer.getFarmer_name());
            preparedStmt.setString(2, farmer.getFarmer_mobile());
            preparedStmt.setString(3, farmer.getFarmer_email());
            preparedStmt.setString(4, farmer.getFarmer_gender());

            String s2 = "INSERT INTO `smart_farming`.`login_table` (\n" +
                    "  `user_email`,\n" +
                    "  `password`,\n" +
                    "  `type`\n" +
                    ")\n" +
                    "VALUES\n" +
                    "  (\n" +
                    "   ?,\n" +
                    "   ?,\n" +
                    "   ?\n" +
                    "  );";
            PreparedStatement preparedStmtlogin = con.prepareStatement(s2);

            preparedStmtlogin.setString(1, farmer.getFarmer_email());
            preparedStmtlogin.setString(2, farmer.getFarmer_password());
            preparedStmtlogin.setString(3, "farmer");

            // execute the java preparedstatement
            if (preparedStmt.executeUpdate() > 0 && preparedStmtlogin.executeUpdate() > 0) {
                System.out.print("Created");
                result = true;
            } else
                result = false;
            preparedStmt.close();
            con.close();
        } catch (Exception e1) {
            System.out.println(e1);
            errorString = e1.getMessage();
            if(con!=null)
            {
                con.close();
            }
        }
        return result;
    }
}

package APIv1.operations;

import APIv1.databaseHelper.database;
import APIv1.pojo.Farmer;
import APIv1.pojo.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


@Path("fetchprofile")
public class FetchProfile {
    private Farmer farmer;
    private String errorString;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ArrayList<ResultResponse> signUp(@FormParam("email") String email,
                                            @FormParam("password") String passwd) {
        ArrayList<ResultResponse> resultResponses = new ArrayList<>();
        System.out.println("EMail " + email + "\nPwd " + passwd);
        if (getProfile(email, passwd)) {
            resultResponses.add(new ResultResponse("false", farmer, "success"));
        } else
            resultResponses.add(new ResultResponse("true", errorString));
        return resultResponses;
    }


    private boolean getProfile(String email, String passwd) {
        boolean result = false;
        try {
            Connection con = new database().getdbms();

            String s1 = "SELECT * FROM \n" +
                    "farmer_details \n" +
                    "JOIN login_table ON login_table.user_email = farmer_details.farmer_email\n" +
                    "WHERE farmer_email = ? AND password = ?;";

            PreparedStatement preparedStmt = con.prepareStatement(s1);
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, passwd);
            ResultSet resultSet = preparedStmt.executeQuery();

            // execute the java preparedstatement
            if (resultSet.next()) {
                farmer = new Farmer(resultSet.getString("farmer_name"), resultSet.getString("farmer_mobile"),
                        resultSet.getString("farmer_email"), resultSet.getString("farmer_gender"));
                System.out.print(farmer.toString());
                result = true;
            } else
                result = false;
            con.close();
        } catch (Exception e1) {
            System.out.println(e1);
            errorString = e1.getMessage();
        }
        return result;
    }


}

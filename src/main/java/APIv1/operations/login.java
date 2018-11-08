package APIv1.operations;

import APIv1.databaseHelper.database;
import APIv1.pojo.ResultResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Path("login")
public class login {
    private String errorString;
    private String email;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ArrayList<ResultResponse> signUp(@FormParam("email") String email,
                                            @FormParam("password") String passwd) {
        ArrayList<ResultResponse> resultResponses = new ArrayList<>();

        if (checkLogin(email, passwd)) {
            resultResponses.add(new ResultResponse("false", "success"));
        } else
            resultResponses.add(new ResultResponse("true", errorString));
        return resultResponses;
    }


    private boolean checkLogin(String email, String passwd) {
        boolean result = false;
        try {
            Connection con = new database().getdbms();

            String s1 = "SELECT * FROM login_table\n" +
                    "WHERE user_email = ?" +
                    "AND `password` = ?\n" +
                    "\n";

            PreparedStatement preparedStmt = con.prepareStatement(s1);
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, passwd);
            ResultSet resultSet = preparedStmt.executeQuery();

            // execute the java preparedstatement
            if (resultSet.next()) {
                email = resultSet.getString(1);
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

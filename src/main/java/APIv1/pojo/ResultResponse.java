package APIv1.pojo;

public class ResultResponse {
    private String error;
    private String message;
    private Farmer farmer;

    public ResultResponse() {

    }

    public ResultResponse(String error, Farmer farmer, String message) {
        this.error = error;
        this.farmer = farmer;
        this.message = message;
    }

    public ResultResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

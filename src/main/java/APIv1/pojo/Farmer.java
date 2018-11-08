package APIv1.pojo;

public class Farmer {
    private String farmer_id;
    private String farmer_name;
    private String farmer_mobile;
    private String farmer_email;
    private String farmer_gender;
    private String farmer_password;

    public Farmer() {
    }

    public Farmer(String farmer_name, String farmer_mobile, String farmer_email, String farmer_gender, String farmer_password) {
        this.farmer_name = farmer_name;
        this.farmer_mobile = farmer_mobile;
        this.farmer_email = farmer_email;
        this.farmer_gender = farmer_gender;
        this.farmer_password = farmer_password;
    }

    public Farmer(String farmer_name, String farmer_mobile, String farmer_email, String farmer_gender) {
        this.farmer_name = farmer_name;
        this.farmer_mobile = farmer_mobile;
        this.farmer_email = farmer_email;
        this.farmer_gender = farmer_gender;
        this.farmer_password = farmer_password;
    }

    @Override
    public String toString() {
        return "Farmer{" +
                "farmer_id='" + farmer_id + '\'' +
                ", farmer_name='" + farmer_name + '\'' +
                ", farmer_mobile='" + farmer_mobile + '\'' +
                ", farmer_email='" + farmer_email + '\'' +
                ", farmer_gender='" + farmer_gender + '\'' +
                ", farmer_password='" + farmer_password + '\'' +
                '}';
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getFarmer_mobile() {
        return farmer_mobile;
    }

    public void setFarmer_mobile(String farmer_mobile) {
        this.farmer_mobile = farmer_mobile;
    }

    public String getFarmer_email() {
        return farmer_email;
    }

    public void setFarmer_email(String farmer_email) {
        this.farmer_email = farmer_email;
    }

    public String getFarmer_gender() {
        return farmer_gender;
    }

    public void setFarmer_gender(String farmer_gender) {
        this.farmer_gender = farmer_gender;
    }

    public String getFarmer_password() {
        return farmer_password;
    }

    public void setFarmer_password(String farmer_password) {
        this.farmer_password = farmer_password;
    }
}

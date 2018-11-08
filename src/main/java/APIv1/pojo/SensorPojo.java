package APIv1.pojo;

public class SensorPojo {
    private String sid;
    private String farmerid;
    private String value;
    private String lastUpdated;
    private String sensorName;

    public SensorPojo(String sid, String farmerid, String value, String lastUpdated, String sensorName) {
        this.sid = sid;
        this.farmerid = farmerid;
        this.value = value;
        this.lastUpdated = lastUpdated;
        this.sensorName = sensorName;
    }

    public SensorPojo() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


    public String getFarmerid() {
        return farmerid;
    }

    public void setFarmerid(String farmerid) {
        this.farmerid = farmerid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}

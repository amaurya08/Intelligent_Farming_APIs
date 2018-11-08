package APIv1.pojo;

import java.util.ArrayList;

public class ResultAllSensor {
    private String error;
    private ArrayList<SensorPojo> sensorPojo;
    private String message;

    public ResultAllSensor(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public ResultAllSensor(String error, ArrayList<SensorPojo> sensorPojo) {
        this.error = error;
        this.sensorPojo = sensorPojo;
        this.message = message;
    }

    public String getErrorString() {
        return error;
    }

    public void setErrorString(String errorString) {
        this.error = errorString;
    }

    public ArrayList<SensorPojo> getSensorPojo() {
        return sensorPojo;
    }

    public void setSensorPojo(ArrayList<SensorPojo> sensorPojo) {
        this.sensorPojo = sensorPojo;
    }
}

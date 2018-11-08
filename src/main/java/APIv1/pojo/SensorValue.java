package APIv1.pojo;

import com.sun.research.ws.wadl.Include;


public class SensorValue {
    String sid;
    String fid;
    String value;

    public SensorValue() {
    }

    public SensorValue(String sid, String fid, String value) {
        this.sid = sid;
        this.fid = fid;
        this.value = value;
    }

    public String getSid() {
        return sid;
    }

    @Override
    public String toString() {
        return "SensorValue{" +
                "sid='" + sid + '\'' +
                ", fid='" + fid + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

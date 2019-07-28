package video.streaming.settingsactivity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIData {

    @SerializedName("a")
    @Expose
    private String a;

    @SerializedName("b")
    @Expose
    private String b;

    @SerializedName("c")
    @Expose
    private String c;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @SerializedName("d")
    @Expose
    private String d;
}

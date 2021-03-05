package be.ucll.java.gip5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apikey {

    @SerializedName("apikey")
    @Expose
    private String apikey;

    @SerializedName("error")
    @Expose
    private String error;

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

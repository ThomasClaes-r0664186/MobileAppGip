package be.ucll.java.gip5.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameReportReturn {

    @SerializedName("Role")
    @Expose
    private Role role;
    @SerializedName("Reports")
    @Expose
    private List<Report> reports = null;
    @SerializedName("error")
    @Expose
    private String error;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public String getError(){
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
package be.ucll.java.gip5.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GamesReturn {

    @SerializedName("Games")
    @Expose
    private List<Game> games = null;
    @SerializedName("Roles")
    @Expose
    private List<Role> roles = null;
    @SerializedName("Participants")
    @Expose
    private List<Participant> participants = null;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}
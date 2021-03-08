package be.ucll.java.gip5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("person")
    @Expose
    private Person person;
    @SerializedName("game")
    @Expose
    private Game game;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
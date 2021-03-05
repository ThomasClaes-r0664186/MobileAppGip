package be.ucll.java.gip5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Participant {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("game")
    @Expose
    private Game game;
    @SerializedName("person")
    @Expose
    private Person person;

    public Participant(){}

    public Participant(Game game){
        this.id = 0;
        this.availability = null;
        this.game = game;
        this.person = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString(){
        return "id: " + this.id + " availability: " + this.availability + "\n game start time: " + this.game.getStartTime() + "\n person first name: " + this.person.getFirstname();
    }
}
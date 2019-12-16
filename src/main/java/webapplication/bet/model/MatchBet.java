package webapplication.bet.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class MatchBet {

    private Long id;
    private String team1;
    private String team2;
    private float type_1;
    private float type_2;
    private float type_X;
    private float type_1X;
    private float type_2X;
    private float type_12;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime time;

    public MatchBet() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public float getType_1() {
        return type_1;
    }

    public void setType_1(float type_1) {
        this.type_1 = type_1;
    }

    public float getType_2() {
        return type_2;
    }

    public void setType_2(float type_2) {
        this.type_2 = type_2;
    }

    public float getType_X() {
        return type_X;
    }

    public void setType_X(float type_X) {
        this.type_X = type_X;
    }

    public float getType_1X() {
        return type_1X;
    }

    public void setType_1X(float type_1X) {
        this.type_1X = type_1X;
    }

    public float getType_2X() {
        return type_2X;
    }

    public void setType_2X(float type_2X) {
        this.type_2X = type_2X;
    }

    public float getType_12() {
        return type_12;
    }

    public void setType_12(float type_12) {
        this.type_12 = type_12;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
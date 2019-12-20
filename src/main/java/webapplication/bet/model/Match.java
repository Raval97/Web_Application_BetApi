package webapplication.bet.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="matches")
public class Match {

//    public static final String ID="id";
//    @Column(name = ID)
    private Long id;
    private String team1;
    private String team2;
    //private League league;     //private Score score;
    private String league;
    private String score;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime time;
    @ManyToOne
    private Courses courses;

    public Match() {
    }

    private enum Leagues{
        ANG1, GER1, ESP1, ITA1, FRA1, POL1, LM
    }
    enum Scores{
        S1, S2, SX, NA
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

///////////////////
//    public League getLeague() {
//        return league;
//    }
//
//    public void setLeague(League league) {
//        this.league = league;
//    }

//    public Score getScore() {
//        return score;
//    }
//
//    public void setScore(Score score) {
//        this.score = score;
//    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
/////////////////////


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
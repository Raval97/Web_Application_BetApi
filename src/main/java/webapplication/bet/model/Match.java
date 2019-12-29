package webapplication.bet.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "courses")

@Entity
@Table(name="matches")
public class Match {

//    private League league;     //private Score score;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String team1;
    private String team2;
    private String league;
    private String score;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private LocalTime time;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private Set<Courses> courses;

    public Match(String team1, String team2, String league, String score, LocalDate date, LocalTime time, Courses... courses) {
        this.team1 = team1;
        this.team2 = team2;
        this.league = league;
        this.score = score;
        this.date = date;
        this.time = time;
        this.courses = Stream.of(courses).collect(Collectors.toSet());
        this.courses.forEach(x -> x.setMatch(this));
    }

    public Match() {
    }

//    private enum Leagues{
//        ANG1, GER1, ESP1, ITA1, FRA1, POL1, LM
//    }
//    enum Scores{
//        S1, S2, SX, NA
//    }

}
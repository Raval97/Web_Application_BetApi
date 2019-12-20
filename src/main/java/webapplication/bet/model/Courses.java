package webapplication.bet.model;

import javax.persistence.*;

@Entity
@Table(name="courses")
public class Courses {

    private Long id;
    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "id_match", referencedColumnName = Match.ID, foreignKey = @ForeignKey(name="id_match"))
    //private Match match;
//    @OneToMany(mappedBy = "courses")
//    private List<Match> match;
    @Column(name = "id_match")
    private Long idMatch;
//    @Enumerated(EnumType.STRING)
//    private Type type;
    private String type;
    private float value;

    public Courses() {
    }

    private enum Type {
        T1, T2, TX, T1X, T2X, T12;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<Match> getMatch() {
//        return match;
//    }
//
//    public void setMatch(List<Match> match) {
//        this.match = match;
//    }

//    public Match getMatch() {
//        return match;
//    }
//
//    public void setMatch(Match match) {
//        this.match = match;
//    }

    public Long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Long idMatch) {
        this.idMatch = idMatch;
    }

//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
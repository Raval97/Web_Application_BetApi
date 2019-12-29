package webapplication.bet.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "couponCourse")

@Entity
@Table(name="courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Match match;
    private String type;
    private float value;
    private boolean state;
    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private Set<CouponCourse> couponCourses;

    public Courses(String type, float value, boolean state, CouponCourse... couponCourses) {
        this.type = type;
        this.value = value;
        this.state = state;
        this.couponCourses = Stream.of(couponCourses).collect(Collectors.toSet());
        this.couponCourses.forEach(x -> x.setCourses(this));
    }

    public Courses() {
    }

//    private enum Type implements Description{
//        T1 ("1"),
//        T2 ("2"),
//        TX ("X"),
//        T1X ("1X"),
//        T2X ("2X"),
//        T12 ("12");
//
//        private String description;
//
//        Type(String description) {
//            this.description = description;
//        }
//
//        @Override
//        public String getHumanReadableDescription(){
//            return description;
//        }
//    };

}
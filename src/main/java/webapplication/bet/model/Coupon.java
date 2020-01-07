package webapplication.bet.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "couponCourse")

@Entity
@Table(name="coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private User idUser;
    private float rate;
    private float amount;
    private float possibleWin;
    private String state;
    @DateTimeFormat(pattern = "yyyy-MMM-dd HH:mm:ss")
    private LocalDateTime date;
    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private Set<CouponCourse> couponCourses;


    public Coupon(float rate, float amount, float possibleWin, String  state, CouponCourse... couponCourses) {
        this.rate = rate;
        this.amount = amount;
        this.possibleWin = possibleWin;
        this.state = state;
        this.couponCourses = Stream.of(couponCourses).collect(Collectors.toSet());
        this.couponCourses.forEach(x -> x.setCoupon(this));
    }

    public Coupon() {
    }

}
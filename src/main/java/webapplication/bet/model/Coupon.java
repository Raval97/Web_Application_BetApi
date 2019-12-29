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
@Table(name="coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private AppUser idUser;
    private float rate;
    private float amount;
    private float possibleWin;
    private boolean state;
    @OneToMany(mappedBy = "courses", cascade = CascadeType.ALL)
    private Set<CouponCourse> couponCourses;


    public Coupon(float rate, float amount, float possibleWin, boolean state, CouponCourse... couponCourses) {
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
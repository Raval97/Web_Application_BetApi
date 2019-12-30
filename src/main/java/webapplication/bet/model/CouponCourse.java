package webapplication.bet.model;

import lombok.Data;

import javax.persistence.*;

@Data

@Entity
@Table(name="coupon_course")
public class CouponCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn
    private Courses courses;
    @ManyToOne
    @JoinColumn
    private Coupon coupon;

    public CouponCourse() {
    }

}
package webapplication.bet.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webapplication.bet.model.CouponCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import webapplication.bet.model.Courses;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CouponCourseRepository extends JpaRepository<CouponCourse, Long> {

    @Query("SELECT cc.id from CouponCourse cc " +
            "inner JOIN cc.coupon co " +
            "inner JOIN co.idUser u  WHERE u.id= :user AND co.id= :coupon")
    List<Long> findAllIdByUserIdInCoupon(@Param("user") Long userId, @Param("coupon") Long couponId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO coupon_course (id, coupon_id, courses_id) " +
            "VALUES (NULL, :idCoupon, :idCourse)", nativeQuery = true)
    void newCouponCourse(@Param("idCoupon") Long idCoupon, @Param("idCourse") Long idCourse);

    @Query(value = "SELECT IF(" +
            "(SELECT CONCAT(m.team1,' vs ',m.team2) FROM coupon_course cc " +
            "LEFT JOIN courses c ON cc.courses_id=c.id " +
            "LEFT JOIN matches m ON c.match_id=m.id " +
            "LEFT JOIN coupon co ON cc.coupon_id=co.id " +
            "WHERE m.id= :idMatch AND co.id= :idCoupon) is null, 1, 0)", nativeQuery = true)
    int checkMatchInCoupon(@Param("idMatch") Long idMatch, @Param("idCoupon") Long idCoupon);
}

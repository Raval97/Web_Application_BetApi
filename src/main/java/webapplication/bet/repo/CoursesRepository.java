package webapplication.bet.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webapplication.bet.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import webapplication.bet.model.Match;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Courses, Long> {

    @Query("SELECT c from Courses c " +
            "inner JOIN c.couponCourses cc " +
            "inner JOIN cc.coupon co " +
            "inner JOIN co.idUser u  WHERE u.id= :user AND co.id= :coupon")
    List<Courses> findAllByUserIdInCouponId(@Param("user") Long userId, @Param("coupon") Long couponId);

    @Query(value = "   SELECT c.* from coupon_course cc " +
            "LEFT JOIN courses c on cc.courses_id=courses_id " +
            "LEFT JOIN matches m on m.id=c.match_id " +
            "inner JOIN coupon co on co.id=cc.coupon_id " +
            "WHERE cc.coupon_id= :id AND cc.courses_id=c.id", nativeQuery = true)
    List<Courses> findAllByCouponId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE courses SET courses.value = :value WHERE courses.id = :id", nativeQuery = true)
    void updateValue(@Param("value") float value, @Param("id") Long id);

    List<Courses> findAllByMatch_LeagueOrderByTypeAscIdAsc(String league);

    Courses findByMatch_IdAndType(Long id, String type);

    @Query(value = "SELECT c.* FROM courses c LEFT JOIN matches m ON c.match_id=m.id " +
            "WHERE m.score = 'N/A' ORDER by c.type ASC, c.id ASC", nativeQuery = true)
    List<Courses> findAllActualToBet();

    @Query(value = "SELECT c.* FROM courses c LEFT JOIN matches m ON c.match_id=m.id " +
            "WHERE m.score != 'N/A' ORDER by c.type ASC, c.id ASC", nativeQuery = true)
    List<Courses> findAllFinishedBet();

    @Query(value = "SELECT c.* FROM courses c LEFT JOIN matches m ON c.match_id=m.id " +
            "WHERE m.score = 'N/A' and m.league= :league ORDER by c.type ASC, c.id ASC", nativeQuery = true)
    List<Courses> findActualToBetByLeague(@Param("league") String league);


    @Query(value = "SELECT c.* FROM courses c LEFT JOIN matches m ON c.match_id=m.id " +
            "WHERE m.score != 'N/A' and m.league= :league ORDER by c.type ASC, c.id ASC", nativeQuery = true)
    List<Courses> findFinishedBetByLeague(@Param("league") String league);

}

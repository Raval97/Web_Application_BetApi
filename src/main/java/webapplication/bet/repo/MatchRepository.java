package webapplication.bet.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webapplication.bet.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByLeague(String league);

    @Query(value = "SELECT m.id FROM matches m " +
            "LEFT JOIN courses c on m.id = c.match_id WHERE c.id= :id", nativeQuery = true)
    Long findIdByCourses_Id(@Param("id") Long id);

    @Query(value = "SELECT m.* from coupon_course cc " +
            "LEFT JOIN courses c on cc.courses_id=courses_id " +
            "LEFT JOIN matches m on m.id=c.match_id " +
            "inner JOIN coupon co on co.id=cc.coupon_id " +
            "WHERE cc.coupon_id= :id AND cc.courses_id=c.id", nativeQuery = true)
    List<Match> findAllByCouponId(@Param("id") Long id);

    @Query(value = "SELECT CONCAT(m.team1,' vs ',m.team2) from coupon_course cc " +
            "LEFT JOIN courses c on cc.courses_id=courses_id " +
            "LEFT JOIN matches m on m.id=c.match_id " +
            "inner JOIN coupon co on co.id=cc.coupon_id " +
            "WHERE co.id_user_id= :user AND cc.coupon_id= :coupon AND cc.courses_id=c.id", nativeQuery = true)
    List<String> findAllMatchNamed(@Param("user") Long userId, @Param("coupon") Long couponId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE matches SET matches.date = :date, league = :league, score = :score, " +
            "team1 = :team1, team2 = :team2, matches.time = :time WHERE matches.id = :id", nativeQuery = true)
    void updateMatch(@Param("id") Long id, @Param("league") String league, @Param("score") String score,
                     @Param("team1") String team1, @Param("team2") String team2,
                     @Param("date") LocalDate date, @Param("time") LocalTime time);
}

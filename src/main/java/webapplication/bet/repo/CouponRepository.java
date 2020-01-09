package webapplication.bet.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webapplication.bet.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE coupon SET coupon.amount = :amount WHERE coupon.id = :id", nativeQuery = true)
    void updateAmount(@Param("amount") float amount, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE coupon SET coupon.date = now() WHERE coupon.id = :id", nativeQuery = true)
    void updateDate(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO coupon (id, amount, possible_win, rate, state, date, id_user_id) " +
            "VALUES (NULL, 5, 0, 0, 'N/A', NULL, :id)", nativeQuery = true)
    void addCoupon(@Param("id") Long id);

    @Query(value = " SELECT if(cl.bet_account_balance-possible_win>=0, 'true', 'false') FROM coupon c " +
            "LEFT JOIN client cl on c.id_user_id=cl.user_id where id= :id", nativeQuery = true)
    String checkAvailabilityMoney(@Param("id") Long id);

    @Query(value = "SELECT max(id) from coupon", nativeQuery = true)
    int getLastCouponId();

    @Query(value = "SELECT * from coupon where id_user_id= :id", nativeQuery = true)
    List<Coupon> findAllByIdUser(Long id);
}

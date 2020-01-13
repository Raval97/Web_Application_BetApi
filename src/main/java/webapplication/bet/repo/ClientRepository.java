package webapplication.bet.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapplication.bet.model.Client;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT * FROM client c WHERE c.user_id= :id", nativeQuery = true)
    Client findByIdClient(@Param("id") Long id);

    @Query(value = "SELECT c.bet_account_balance FROM client c WHERE c.user_id= :id", nativeQuery = true)
    float findAmountByIdClient(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE client SET client.bet_account_balance = :price WHERE client.user_id= :id", nativeQuery = true)
    void updateAmountOfClient(@Param("price") float price, @Param("id") Long id);
}

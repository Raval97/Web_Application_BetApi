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

}

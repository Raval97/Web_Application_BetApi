package webapplication.bet.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webapplication.bet.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query (value = "select * from user where user.id= :id", nativeQuery = true)
    User findByUserId(Long id);
}

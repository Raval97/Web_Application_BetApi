package webapplication.bet.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webapplication.bet.model.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);
}

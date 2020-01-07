package webapplication.bet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webapplication.bet.model.User;
import webapplication.bet.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repo;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
    }

    public User get(long id) {
        return repo.findByUserId(id);
    }

    public void save(User user){
        repo.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repo.findByUsername(s);
    }

}

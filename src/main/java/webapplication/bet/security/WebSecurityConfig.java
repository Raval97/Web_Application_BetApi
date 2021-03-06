package webapplication.bet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import webapplication.bet.model.Client;
import webapplication.bet.model.User;
import webapplication.bet.repo.ClientRepository;
import webapplication.bet.repo.UserRepository;
import webapplication.bet.service.UserService;

import java.time.LocalDate;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userDetailsService;
    private UserRepository userRepository;
    private ClientRepository clientRepository;

    @Autowired
    public WebSecurityConfig(UserService userDetailsService, UserRepository userRepository, ClientRepository clientRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/client/All/actual").hasRole("USER")
                .antMatchers("/client/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin().permitAll().defaultSuccessUrl("/default", true)
                .and()
                .logout()
                .logoutSuccessUrl("/user/ALL/actual")
                .permitAll();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void get() {
//        Client client = new Client("Adam", "Malysz", "Wisla", "Krakowska", 99,
//                LocalDate.of(1980,01,01), "malysz@gmail.com", "1234", 1000);
//        Client client2 = new Client("Robert", "Kubica", "Krakow", "Warszzawska", 24,
//                LocalDate.of(1985,04,04), "mkubica@gmail.com", "5678", 1000);
//        User appUserClient1 = new User("User1", passwordEncoder().encode("User1"), "ROLE_USER", client);
//        User appUserClient2 = new User("User2", passwordEncoder().encode("User2"), "ROLE_USER", client2);
//        User appUserAdmin = new User("Admin", passwordEncoder().encode("Admin"), "ROLE_ADMIN");
//        userRepository.save(appUserClient1);
//        userRepository.save(appUserClient2);
//        userRepository.save(appUserAdmin);
//    }
}


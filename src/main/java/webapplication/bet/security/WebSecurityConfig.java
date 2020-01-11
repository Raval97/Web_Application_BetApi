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
                .antMatchers("/test1").hasRole("USER")
                .antMatchers("/test2").hasRole("ADMIN")
                .and()
                .formLogin().permitAll();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void get() {
//        User appUserUser = new User("User1", passwordEncoder().encode("User1"), "ROLE_USER";
//        User appUserAdmin = new User("Admin", passwordEncoder().encode("Admin"), "ROLE_ADMIN");
//        userRepository.save(appUserUser);
//        userRepository.save(appUserAdmin);
//    }
}


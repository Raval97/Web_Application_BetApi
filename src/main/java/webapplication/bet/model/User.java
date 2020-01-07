package webapplication.bet.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.service.spi.InjectService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "coupon" )
//@EqualsAndHashCode(exclude = "client")

@Entity
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL)
    private Set<Coupon> coupon;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Client client;

    public User(String username, String password, String role, Client client, Coupon... coupon) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.client=client;
        this.client.setUser(this);
        this.coupon = Stream.of(coupon).collect(Collectors.toSet());
        this.coupon.forEach(x -> x.setIdUser(this));
    }

//    public User(String username, String password, String role, Coupon... coupon) {
//        this.username = username;
//        this.password = password;
//        this.role = role;
//        this.coupon = Stream.of(coupon).collect(Collectors.toSet());
//        this.coupon.forEach(x -> x.setIdUser(this));
//    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public void setPassword(String password) {
        this.password = password;
        this.password = passwordEncoder().encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @InjectService
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

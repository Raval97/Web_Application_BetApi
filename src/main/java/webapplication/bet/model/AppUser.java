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
@EqualsAndHashCode(exclude = "coupon")

@Entity
@Table(name="user")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    @OneToMany(mappedBy = "idUser", cascade = CascadeType.ALL)
    private Set<Coupon> coupon;

    public AppUser(String username, String password, String role, Coupon... coupon) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.coupon = Stream.of(coupon).collect(Collectors.toSet());
        this.coupon.forEach(x -> x.setIdUser(this));
    }

    public AppUser() {
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public void setPassword(String password) {
        this.password = password;
        this.password = passwordEncoder().encode(password);
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

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

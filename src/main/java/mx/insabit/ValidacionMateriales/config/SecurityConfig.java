
package mx.insabit.ValidacionMateriales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/api/public/**").permitAll()
          .anyRequest().authenticated()
      )
      .httpBasic(withDefaults());  // aquí usamos el método helper
    return http.build();
}



    @Bean
    public UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()  // para ejemplo simple, no para producción
            .username("Conavi")
            .password("Grupo586")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
}
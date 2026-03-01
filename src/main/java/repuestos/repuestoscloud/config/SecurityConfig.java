package repuestos.repuestoscloud.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // ✅ para que el POST /registro funcione aunque tu form no tenga csrf token
            .csrf(csrf -> csrf.ignoringRequestMatchers("/registro"))

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index",
                    "/login",
                    "/registro",
                    "/css/**", "/js/**", "/images/**", "/webjars/**",
                    "/errores/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/productos", true)
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
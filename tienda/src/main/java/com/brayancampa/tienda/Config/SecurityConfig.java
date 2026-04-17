package com.brayancampa.tienda.Config;

import com.brayancampa.tienda.controller.RegisterController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    // Creamos un objeto para encriptar la contraseña
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        // contraseña encriptada -> return new BCryptPasswordEncoder();
    }

    // Creamos un objeto para el usuario de la aplicación
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //Verificamos el admin manual
            if ("admin".equals(username)) {
                return User.withUsername("admin")
                        .password("1234")
                        .roles("ADMIN")
                        .build();
            }
            //Usamos el buscador que creamos en el controlador
            String passwordEncontrada = RegisterController.buscarPassword(username);
            if (passwordEncontrada != null) {
                return User.withUsername(username)
                        .password(passwordEncontrada)
                        .roles("USER")
                        .build();
            }
            //Si no existe en ningún lado
            throw new UsernameNotFoundException("Usuario no encontrado");
        };
    }

    // Creamos un objeto para la configuración de las rutas de Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests( auth -> auth.requestMatchers("/login","/register", "/enviar-registro","/css/**").permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/home",true).permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }
}

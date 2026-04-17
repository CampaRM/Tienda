package com.brayancampa.tienda.Config;

import com.brayancampa.tienda.controller.RegisterController;
import com.brayancampa.tienda.entity.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SecurityConfig {

    private static List<Usuario> usuariosRegistrados = new ArrayList<>();

    // Creamos un objeto para el usuario de la aplicación
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //Verificamos el admin manual
            if ("admin".equals(username)) {
                return User.withUsername("admin")
                        .password("{noop}")
                        .roles("ADMIN")
                        .build();
            }
            // 2. Buscamos en tu lista de Usuarios registrados
            for (Usuario u : usuariosRegistrados) {
                if (u.getNombreUsuario().equalsIgnoreCase(username)) {
                    return User.withUsername(u.getNombreUsuario())
                            .password("{noop}") // forma de spring security para no tener que ingresar una contraseña.
                            .roles("USER")
                            .build();
                }
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

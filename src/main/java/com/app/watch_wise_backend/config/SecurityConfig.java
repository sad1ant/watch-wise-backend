package com.app.watch_wise_backend.config;

import com.app.watch_wise_backend.middleware.AdminMiddleware;
import com.app.watch_wise_backend.middleware.AuthMiddleware;
import com.app.watch_wise_backend.repository.UserRepository;
import com.app.watch_wise_backend.service.AuthService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<AuthMiddleware> filterRegistration(AuthService authService, UserRepository userRepository) {
        FilterRegistrationBean<AuthMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthMiddleware(authService, userRepository));
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminMiddleware> adminFilterRegistration(AuthService authService, UserRepository userRepository) {
        FilterRegistrationBean<AdminMiddleware> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminMiddleware(authService, userRepository));
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

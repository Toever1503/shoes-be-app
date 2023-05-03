package com.photoism.cms.common.config;

import com.photoism.cms.common.enums.RoleEnum;
import com.photoism.cms.common.security.CustomAccessDeniedHandler;
import com.photoism.cms.common.security.CustomAuthenticationEntryPoint;
import com.photoism.cms.common.security.JwtAuthenticationFilter;
import com.photoism.cms.common.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationContext applicationContext;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(PERMIT_ALL_LIST).permitAll()
                .requestMatchers(OWNER_AND_ADMIN_LIST).access(getWebExpressionAuthorizationManager("@authorizationChecker.hasAdminOrCheckId(request, #id)"))
                .requestMatchers(ADMIN_ONLY_LIST).hasAnyRole(RoleEnum.ROLE_SUPER_ADMIN.getTitle(), RoleEnum.ROLE_ADMIN.getTitle())
                .requestMatchers(SUPER_ADMIN_ONLY_LIST).hasRole(RoleEnum.ROLE_SUPER_ADMIN.getTitle())
                .anyRequest().authenticated()

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private static final String[] PERMIT_ALL_LIST = {
            "/*/auth/sign-in",
            "/*/user/{id}/password",
            "/*/file/**",
            "/*/etc/code",
            "/*/etc/health-check"
    };

    private static final String[] OWNER_AND_ADMIN_LIST = {
            "/*/user/{id}"
    };

    private static final String[] ADMIN_ONLY_LIST = {
            "/*/auth/refresh/{id}",
            "/*/user/",
            "/*/user/list/*",
            "/*/user/resetPassword/{id}",
            "/*/store/**"
    };

    private static final String[] SUPER_ADMIN_ONLY_LIST = {
            "/*/etc/test"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(IGNORING_LIST);
    }

    private static final String[] IGNORING_LIST = {
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger/**",
            "/actuator/health",
            "/*/exception/**"
    };

    private WebExpressionAuthorizationManager getWebExpressionAuthorizationManager(final String expression) {
        final var expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        final var authorizationManager = new WebExpressionAuthorizationManager(expression);
        authorizationManager.setExpressionHandler(expressionHandler);
        return authorizationManager;
    }
}
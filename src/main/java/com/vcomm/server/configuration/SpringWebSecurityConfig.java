package com.vcomm.server.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

//  @Override
//  @Bean
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

//  @Bean(name = "emAuthenticationProvider")
//  public AuthenticationProvider emAuthenticationProvider() {
//    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//    provider.setPasswordEncoder(passwordEncoder());
//    provider.setUserDetailsService(userDetailsService);
//    return provider;
//  }

//  @Autowired
//  public void configure(AuthenticationManagerBuilder auth) {
//    auth.authenticationProvider(emAuthenticationProvider());
//  }

//  @Autowired
//  public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService);
//  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    http
//      .sessionManagement()
//      .maximumSessions(2)
//      .maxSessionsPreventsLogin(true);
//
//    http
//      .csrf().disable();
//    http
//      .httpBasic()
//      .disable();
//
//    http
//      .authorizeRequests()
//      .antMatchers("/admin/api/v1/login")
//      .permitAll();
//
//    http
//      .logout()
//      .logoutRequestMatcher(new AntPathRequestMatcher("/admin/api/v1/logout", "POST"))
//      .deleteCookies("JSESSIONID")
//      .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT))
//      .invalidateHttpSession(true);
//
//    http
//      .antMatcher("/admin/**")
//      .authorizeRequests()
//      .antMatchers("/admin/**").hasAuthority("ADMIN_PRIVILEGE");
//  }

  @Configuration
  @Order(1)
  public static class userWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "emUserDetailsService")
    UserDetailsService emUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http
              .sessionManagement()
              .maximumSessions(2)
              .maxSessionsPreventsLogin(true);

      http
              .csrf().disable();
      http
              .httpBasic()
              .disable();

      http
              .authorizeRequests()
              .antMatchers("/admin/api/v1/login")
              .permitAll();

      http
              .logout()
              .logoutRequestMatcher(new AntPathRequestMatcher("/admin/api/v1/logout", "POST"))
              .deleteCookies("JSESSIONID")
              .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT))
              .invalidateHttpSession(true);

      http
              .antMatcher("/admin/**")
              .authorizeRequests()
              .antMatchers("/admin/**").hasAuthority("ADMIN_PRIVILEGE");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    @Primary
    public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
    }

    @Bean(name = "emAuthenticationProvider")
    public AuthenticationProvider emAuthenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setPasswordEncoder(passwordEncoder());
      provider.setUserDetailsService(emUserDetailsService);
      return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(emUserDetailsService);
      auth.authenticationProvider(emAuthenticationProvider());
    }

  }

  @Configuration
  @Order(2)
  public static class roomWebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Resource(name = "roomUserDetailsService")
    UserDetailsService roomUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http
              .sessionManagement()
              .maximumSessions(2)
              .maxSessionsPreventsLogin(true);

      http
              .csrf().disable();
      http
              .httpBasic()
              .disable();

      http
              .authorizeRequests()
              .antMatchers("/api/v1/login")
              .permitAll();

      http
              .logout()
              .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout", "POST"))
              .deleteCookies("JSESSIONID")
              .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpServletResponse.SC_NO_CONTENT))
              .invalidateHttpSession(true);

      http
              .antMatcher("/api/**")
              .authorizeRequests()
              .antMatchers("/api/**").hasAuthority("ROOM_PRIVILEGE");
    }

    @Bean(name = "roomPasswordEncoder")
    public PasswordEncoder roomPasswordEncoder(){
      return new BCryptPasswordEncoder();
    }

    @Override
    @Bean(name = "roomAuthenticationManager")
    public AuthenticationManager authenticationManager() throws Exception {
      return super.authenticationManagerBean();
    }

    @Bean(name = "roomAuthenticationProvider")
    public AuthenticationProvider roomAuthenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setPasswordEncoder(roomPasswordEncoder());
      provider.setUserDetailsService(roomUserDetailsService);
      return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(roomUserDetailsService);
      auth.authenticationProvider(roomAuthenticationProvider());
    }

  }

}

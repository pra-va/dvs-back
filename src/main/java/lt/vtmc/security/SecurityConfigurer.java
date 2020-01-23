package lt.vtmc.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import lt.vtmc.security.service.SystemAdminService;
import lt.vtmc.security.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Configuration
	@Order(1)
	public static class AdminSecurityConfigurer extends WebSecurityConfigurerAdapter {

		@Autowired
		private SecurityEntryPoint securityEntryPoint;

		@Autowired
		private SystemAdminService systemAdminService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(systemAdminService);

		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().authorizeRequests().antMatchers("/", "/swagger-ui.html", "/createuser").permitAll().and()
					.authorizeRequests().antMatchers("/api/admin").hasRole("ADMIN").antMatchers("/api/user")
					.hasRole("USER").and().formLogin().successHandler(new SimpleUrlAuthenticationSuccessHandler() {

						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {
							response.setHeader("Access-Control-Allow-Credentials", "true");
							response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
							response.setHeader("Content-Type", "application/json;charset=UTF-8");
							response.getWriter().print("{\"username\": \""
									+ SecurityContextHolder.getContext().getAuthentication().getName() + "\"}");
						}

					}).failureHandler(new SimpleUrlAuthenticationFailureHandler()).loginPage("/api/loginadmin")
					.permitAll().and().logout().logoutUrl("/api/logoutadmin")
					.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll().and()
					.csrf().disable().exceptionHandling().authenticationEntryPoint(securityEntryPoint).and().headers()
					.frameOptions().disable();
		}
	}

	@Configuration
	@Order(2)
	public static class UserSecurityConfigurer extends WebSecurityConfigurerAdapter {

		@Autowired
		private SecurityEntryPoint securityEntryPoint;

		@Autowired
		private UserService userService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService);

		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().authorizeRequests().antMatchers("/", "/swagger-ui.html", "/createuser").permitAll().and()
					.authorizeRequests().antMatchers("/api/admin").hasRole("ADMIN").antMatchers("/api/user")
					.hasRole("USER").and().formLogin().successHandler(new SimpleUrlAuthenticationSuccessHandler() {

						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {
							response.setHeader("Access-Control-Allow-Credentials", "true");
							response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
							response.setHeader("Content-Type", "application/json;charset=UTF-8");
							response.getWriter().print("{\"username\": \""
									+ SecurityContextHolder.getContext().getAuthentication().getName() + "\"}");
						}

					}).failureHandler(new SimpleUrlAuthenticationFailureHandler()).loginPage("/api/loginuser")
					.permitAll().and().logout().logoutUrl("/api/logoutuser")
					.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll().and()
					.csrf().disable().exceptionHandling().authenticationEntryPoint(securityEntryPoint).and().headers()
					.frameOptions().disable();
		}
	}

}

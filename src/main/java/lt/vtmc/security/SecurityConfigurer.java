package lt.vtmc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityEntryPoint securityEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);

	}

	/**
	 * Remove /createadmint from permitall authorization at the end.
	 */
	@Override

	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().antMatchers("/api/**").hasRole("ADMIN").antMatchers("/api/user")
				.hasRole("USER").antMatchers("/**").permitAll().and().formLogin().loginPage("/api/login").permitAll()
				.and().logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID")
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll().and()
				.csrf().disable();

//		http.cors().and().authorizeRequests().antMatchers("/api/admin").hasRole("ADMIN").antMatchers("/api/user")
//				.hasAnyRole("ADMIN", "USER").and().authorizeRequests()
//				.antMatchers("/", "/swagger-ui.html", "/createadmin", "/createuser").permitAll().and().formLogin()
//				.successHandler(new SimpleUrlAuthenticationSuccessHandler() {
//
//					@Override
//					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//							Authentication authentication) throws IOException, ServletException {
//						response.setHeader("Access-Control-Allow-Credentials", "true");
//						response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//						response.setHeader("Content-Type", "application/json;charset=UTF-8");
//						response.getWriter().print("{\"username\": \""
//								+ SecurityContextHolder.getContext().getAuthentication().getName() + "\"}");
//					}
//
//				}).failureHandler(new SimpleUrlAuthenticationFailureHandler()).loginPage("/api/login").permitAll().and()
//				.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID")
//				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll().and()
//				.csrf().disable().exceptionHandling().authenticationEntryPoint(securityEntryPoint).and().headers()
//				.frameOptions().disable();
	}

}

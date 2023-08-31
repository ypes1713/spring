package main.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("select login, password, enabled from user where login=?")
			.authoritiesByUsernameQuery("select login, role from role where login=?");
		
//			.inMemoryAuthentication()
//			.withUser("ming").password(passwordEncoder().encode("001811")).roles("EMPLOYEE")
//			.and()
//			.withUser("v").password(passwordEncoder().encode("0521")).roles("CLIENT");
	}
 	
    @Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/", "/login")
				.permitAll()
//			.antMatchers("/shopViewProduct/**", "/cart")
//				.hasAnyRole("ADMIN","CLIENT")
			.antMatchers("/admin")
			    .hasAnyRole("ADMIN")
			.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/checkUserAccount")
				.defaultSuccessUrl("/")
				.permitAll()
			.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.permitAll()
			.and()
				.exceptionHandling().accessDeniedPage("/forbidden");
		
		http.csrf().disable();
	}

        
}

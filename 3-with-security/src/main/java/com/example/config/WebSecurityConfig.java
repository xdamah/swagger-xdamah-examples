package com.example.config;

/*import static com.cbc.encryptionservicepoc.TestData.ADMIN_NAME;
import static com.cbc.encryptionservicepoc.TestData.ADMIN_PASSWORD;
import static com.cbc.encryptionservicepoc.TestData.*;
import static com.cbc.encryptionservicepoc.TestData.DEV_PASSWORD;
import static com.cbc.encryptionservicepoc.TestData.USER_NAME;
import static com.cbc.encryptionservicepoc.TestData.USER_PASSWORD;
*/

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

//import static org.springframework.security.web.util.matcher.
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	MvcRequestMatcher.Builder mvc;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http = http.headers(cfg -> cfg.frameOptions(cfx -> cfx.sameOrigin()));

		RequestCache nullRequestCache = new NullRequestCache();
		http = http

				.requestCache((cache) -> cache.requestCache(nullRequestCache));
		http = http.sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http = http.csrf(cfg -> cfg.disable());

		http = http.authorizeHttpRequests((requests) -> requests
				.requestMatchers(mvc.pattern("/"), mvc.pattern("/swagger-ui.html"), mvc.pattern("/v3/api-docs"))
				.permitAll()

				.requestMatchers(mvc.pattern("/secured/*/")).hasRole("User")

				// .anyRequest().authenticated()
				.anyRequest().permitAll());
		// .formLogin(cfg->cfg.disable())
		http.httpBasic(withDefaults());

		// we dont need any logout
		/*
		 * http.logout((logout) -> { logout.logoutUrl("/logout"); logout.permitAll();
		 * });
		 */
		// );

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("User")
				.build();
		return new InMemoryUserDetailsManager(user);
	}
}

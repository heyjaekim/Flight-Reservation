package boot;

import org.springframework.boot.autoconfigure.*;
// import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.*;
import org.springframework.boot.context.web.*;

import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;

import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;

import org.slf4j.*;

import java.util.*;

// @SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {
	@Autowired
	AppUserService appUserService;		

	@Autowired
	RoleService roleService;		

	// externalize application configuration
	// TODO: put application.properties under resources/{appName}/ in source code
	// TODO: put application.properties under lib/{appName}/ in tomcat
	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", "classpath:boot/");
		return props;
	}

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	// security settings, review all TODOs
	@Configuration
	@EnableWebSecurity
	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		public class CustomizedUserDetailsService 
		  implements UserDetailsService {

				// form authentication via /login and login.html
				@Override
				public UserDetails loadUserByUsername(String username) 
						throws UsernameNotFoundException {
						List<GrantedAuthority> authorities = new ArrayList<>();
						AppUser user;
						try {
							user = appUserService.get(username);
							if (user != null) {
								Iterable<Role> roles = roleService.getRoles(user.getUsername());
								roles.forEach(role->authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole())));
							}
						} catch(Throwable t) {
							throw new UsernameNotFoundException("User Not Found");
						}
						return new User(user.getUsername(), user.getPassword(), authorities);
				}
	  }

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// TODO: setup access control via role
			if (!application_security) {
				log.warn("***** No Access Control *****");
				http.authorizeRequests()
						.antMatchers("/**").permitAll();
			} else {
				http.authorizeRequests()
						.antMatchers("/", "/index.html", "/register.html", "/user", "/adduser", "/airport", "/aircraft", "/airline", "/flight", "/search", "/css/**", "/js/**").permitAll()
						.antMatchers("/customer_rep.html").hasRole("customer_representative")
						.antMatchers("/cr/**").hasRole("customer_representative")
						.antMatchers("/admin.html").hasRole("admin")
						.antMatchers("/admin/**").hasRole("admin")
						.anyRequest().authenticated();
			}

			// TODO: optional form based authentication
			http.formLogin().loginPage("/login").permitAll();

			// TODO: additional in template or Ajax js
			http.csrf().disable();

			// TODO: optional disable xframe for h2 console
			http.headers().frameOptions().disable();

			// Setup logout
			http.logout().logoutSuccessUrl("/").permitAll();
		}

		// ******** Usually no need to change code below
		@Value("${application.security:true}")
		private boolean application_security;

		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) 
		throws Exception {
			CustomizedUserDetailsService userDeatailService = 
					new CustomizedUserDetailsService();

			// Optional if form based login used    
			auth.userDetailsService(userDeatailService);
		}
	} 
	
	@Configuration
	public class MvcConfig extends WebMvcConfigurerAdapter {
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			// choose login form as templates/login.html
			registry.addViewController("/login").setViewName("login");
		}
	}

	@Override
	protected SpringApplicationBuilder configure(
	SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder
			.sources(Application.class)
			.properties(getProperties());
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class)
			.sources(Application.class)
			.properties(getProperties())
			.build()
			.run(args);
	}    
}

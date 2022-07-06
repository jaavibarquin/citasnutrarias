package es.nutrarias.citas.citasnutrarias.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.nutrarias.citas.citasnutrarias.security.jwt.JWTAuthorizationFilter;
import es.nutrarias.citas.citasnutrarias.security.jwt.JWTEntryPoint;
import es.nutrarias.citas.citasnutrarias.security.service.UsuarioSecurityService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UsuarioSecurityService userSvc;
	
	@Autowired
	JWTEntryPoint jwtEP;
	
	@Bean
	public JWTAuthorizationFilter jwtTokenFilter() {
		return new JWTAuthorizationFilter();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSvc).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST,"/nutrarias/auth/**").permitAll()
		.antMatchers(HttpMethod.GET, "/nutrarias/citas/{area}/libres").permitAll()
		.antMatchers(HttpMethod.PUT, "/nutrarias/citas/{area}/{dia}/{hora}").permitAll()
		.anyRequest().authenticated()
		.and().exceptionHandling().authenticationEntryPoint(jwtEP)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	
	}
}

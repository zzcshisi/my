package zzc.recruitment.config;


import zzc.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userService;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/cantact").setViewName("contact");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor(userService)).addPathPatterns("/**").excludePathPatterns("/user/login","/login","/logout","/user/register","/register","/user/passwd","/user/passwd/edit","/index","/notice","/","/css/**","/js/**","/assets/**","/img/**","static/**");
        registry.addInterceptor(new IdentityOneInterceptor(userService)).addPathPatterns("/manager/**");
        registry.addInterceptor(new IdentityTwoInterceptor(userService)).addPathPatterns("/business/**");
    }
}

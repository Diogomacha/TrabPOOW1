package br.ufsm.csi.projetodiogopoow.config;

import br.ufsm.csi.projetodiogopoow.security.AutorizadorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
    private final AutorizadorInterceptor autorizadorInterceptor;

    public AppConfig(AutorizadorInterceptor autorizadorInterceptor) {
        this.autorizadorInterceptor = autorizadorInterceptor;
    }

    @Bean
    public WebMvcConfigurer mvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(autorizadorInterceptor);
            }
        };
    }
}


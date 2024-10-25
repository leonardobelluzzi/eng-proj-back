package br.com.fatec.fatec_eng3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.fatec.fatec_eng3.interceptors.AuthInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
    private AuthInterceptor authenticationInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Permite para todas as rotas
                .allowedOrigins("*")  // Permite de qualquer origem
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Permite todos os métodos HTTP
                .allowedHeaders("*")  // Permite todos os cabeçalhos
                .allowCredentials(false);  // Se quiser desativar envio de cookies ou autenticação via credenciais
    }

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/api/**");
    }
}
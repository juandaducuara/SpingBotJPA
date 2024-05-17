package com.aluracursos.screenmatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Definicin de beans para posterior uso
@Configuration
//Se implemeta la configuracion WevMvc
public class CorsConfiguration implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD","TRACE","CONNECT");
    }
}

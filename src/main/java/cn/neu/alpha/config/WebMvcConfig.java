package cn.neu.alpha.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @author: CCM 20164969
 * @description: The config of this application
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //Interceptor to solve cross-domain issues
////        registry.addInterceptor(new ProcessInterceptor());
//        //Token interceptor
////        registry.addInterceptor(authorizationInterceptor)
////                .addPathPatterns("/admin/**")
////                .excludePathPatterns("/admin/login")
////                .excludePathPatterns("/admin/dashboard");
//    }

    @Bean
    public WebMvcConfigurationSupport forwardToIndex() {
        return new WebMvcConfigurationSupport() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/back").setViewName(
                        "forward:/back/index");
                registry.addViewController("/").setViewName(
                        "forward:/back/index");
            }
        };
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //Inherit parent configuration
        super.configureMessageConverters(converters);
        //create fastJson converter
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //create a config class
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        //Modify the config
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //add the converter into application
        converters.add(fastConverter);
    }
}

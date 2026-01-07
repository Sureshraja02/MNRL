/*
 * package com.fisglobal.fsg.dip.core.config;
 * 
 * import javax.inject.Inject;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.web.servlet.config.annotation.WebMvcConfigurer; import
 * org.springframework.web.servlet.view.InternalResourceViewResolver;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * com.fisglobal.fsg.dip.core.crypto.DecryptionUtils; import
 * com.fisglobal.fsg.dip.core.crypto.EncryptionUtils;
 * 
 * import springfox.documentation.builders.PathSelectors; import
 * springfox.documentation.builders.RequestHandlerSelectors; import
 * springfox.documentation.spi.DocumentationType; import
 * springfox.documentation.spring.web.plugins.Docket; import
 * springfox.documentation.swagger2.annotations.EnableSwagger2;
 * 
 * @Configuration
 * 
 * @EnableSwagger2 public class SwaggerConfig {
 * 
 * @Value("${rms.crypto.key}") private String SECRET_KEY;
 * 
 * @Value("${rms.crypto.salt}") private String SALT;
 * 
 * @Inject private EncryptionUtils encrypt;
 * 
 * @Inject private DecryptionUtils decrypt;
 * 
 * @Autowired private ObjectMapper objectMapper;
 * 
 * @Bean public Docket SwaggerApi() { return new
 * Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any
 * ()) .paths(PathSelectors.any()).build(); }
 * 
 * @Bean public WebMvcConfigurer corsConfigurer() { return new
 * WebMvcConfigurer() {
 * 
 * @Override public void addCorsMappings(CorsRegistry registry) {
 * registry.addMapping("/*").allowedOrigins("http://localhost:3000") //
 * .allowedOriginPatterns("*") // .allowCredentials(true) .exposedHeaders(
 * "access-control-allow-credentials", "access-control-allow-origin",
 * "access-control-expose-headers", "content-type", "requestid", "Authorization"
 * ) .allowedHeaders( "access-control-allow-credentials",
 * "access-control-allow-origin", "access-control-expose-headers",
 * "content-type", "requestid", "Authorization" ); }
 * 
 * @Override public void
 * configureMessageConverters(List<HttpMessageConverter<?>> converters) {
 * 
 * APIEncryptDecrypt encryptDecryptConvertors = new
 * APIEncryptDecrypt(SECRET_KEY, SALT, encrypt, decrypt, objectMapper);
 * converters.add(encryptDecryptConvertors); }
 * 
 * }; }
 * 
 * @Bean public InternalResourceViewResolver defaultViewResolver() { return new
 * InternalResourceViewResolver(); } }
 */

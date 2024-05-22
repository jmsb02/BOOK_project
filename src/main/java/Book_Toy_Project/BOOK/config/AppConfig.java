package Book_Toy_Project.BOOK.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * RestTemplate는 스프링 프레임워크에서 제공하는 HTTP 클라이언트
     * -> RESTful 웹 서비스와의 통신을 쉽게 할 수 있도록 도와줌.
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

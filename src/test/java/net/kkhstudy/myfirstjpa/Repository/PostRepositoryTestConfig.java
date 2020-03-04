package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Entity.PostListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostRepositoryTestConfig {
    @Bean
    public PostListener postListener() {
        return new PostListener();
    }
}

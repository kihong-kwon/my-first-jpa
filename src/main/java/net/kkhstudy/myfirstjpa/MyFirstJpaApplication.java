package net.kkhstudy.myfirstjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MyFirstJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstJpaApplication.class, args);
    }

}

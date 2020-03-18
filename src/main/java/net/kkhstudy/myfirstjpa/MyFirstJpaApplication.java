package net.kkhstudy.myfirstjpa;

import net.kkhstudy.myfirstjpa.Repository.SimpleCustomJpaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

@SpringBootApplication
// Springboot를 사용하는 경우 아래의 에노테이션을 추가안해도 되지만 커스터마이즈 설정을 할 경우에는 Springboot라도 설정이 필요하다.
@EnableJpaRepositories(queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND, repositoryBaseClass = SimpleCustomJpaRepository.class) // DEFAULT, 미리 정의한 쿼리 찾아보고 없으면 만들기
public class MyFirstJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFirstJpaApplication.class, args);
    }

}

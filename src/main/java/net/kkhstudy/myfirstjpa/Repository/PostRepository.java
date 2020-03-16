package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends CustomJpaRepository<Post, Long>, PostCustomRepository<Post>, QuerydslPredicateExecutor<Post> {
    // SPRING DATA JPA에서 JpaRepository를 상속받은 interface를 Bean으로 등록해준다.
    // @Import(JpaRepositoriesRegistrar.class)
    // ImportBeanDefinitionRegistrar 인터페이스

    Page<Post> findByTitleContains(String title, Pageable pageable);

    long countByTitleContains(String title);
}

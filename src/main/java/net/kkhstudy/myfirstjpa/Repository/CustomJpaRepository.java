package net.kkhstudy.myfirstjpa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

// JpaRepository를 사용하면서 커스텀한 기능을 추가한 Repository를 생성할 경우
@NoRepositoryBean
public interface CustomJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    boolean contains(T entity);
}

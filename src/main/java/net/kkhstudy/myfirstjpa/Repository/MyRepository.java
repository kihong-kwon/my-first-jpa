package net.kkhstudy.myfirstjpa.Repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

// JPA에서 특정 기능만 생성해주는 커스텀 Repository를 만들경우
// Repository를 상속
@NoRepositoryBean
public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID> {

    <E extends T> E save(@NonNull E entity);

    <E extends T> List<E> findAll();

    long count();

    <E extends T> Optional<E> findById(ID id);
    // @Nullable
    //<E extends T> E findById(ID id);
}

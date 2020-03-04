package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Post;

import java.util.List;

// 커스텀한 Repository를 작성하는 방법
public interface PostCustomRepository<T> {

    List<Post> findMyPost();

    // JpaRepository와 같은 이름의 메소드가 존재하면 커스텀 메서드가 우선된다.
    void delete(T entity);
}

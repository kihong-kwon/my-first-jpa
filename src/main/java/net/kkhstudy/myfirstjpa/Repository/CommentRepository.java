package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Entity.Comment;
import net.kkhstudy.myfirstjpa.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

// JpaRepository를 상속받지 않고 사용할 기능만 정의할 수 있는 기능
@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
//@NamedQuery(name = "Comment.findByTitle", query = "SELECT p FROM Post AS p WHERE p.title = ?1") // JPQL
public interface CommentRepository extends MyRepository<Comment, Long> {
    //@Query(value = "SELECT c FROM Comment AS c", nativeQuery = true) // SQL
    //@Query("SELECT c FROM Comment AS c") // JPQL
    List<Comment> findByCommentContains(String keyword);

    List<Comment> findByCommentContainsIgnoreCase(String keyword);

    List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likeCount);

    Page<Comment> findByLikeCountGreaterThanAndPost(Integer likeCount, Post post, Pageable page);

    Page<Comment> findByLikeCountGreaterThanAndPost_Id(Integer likeCount, Long postId, Pageable page);

    @Async
    Future<List<Comment>> findByCommentContainsIgnoreCaseAndId(String keyword, Long id);



}

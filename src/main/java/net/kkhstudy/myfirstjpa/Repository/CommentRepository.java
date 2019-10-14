package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Comment;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

// JpaRepository를 상속받지 않고 사용할 기능만 정의할 수 있는 기능
@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends MyRepository<Comment, Long> {
}

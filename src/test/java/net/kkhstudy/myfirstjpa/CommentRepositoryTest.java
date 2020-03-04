package net.kkhstudy.myfirstjpa;

import net.kkhstudy.myfirstjpa.Entity.Comment;
import net.kkhstudy.myfirstjpa.Repository.CommentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void crud1() {
        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        long count = commentRepository.count();
        assertThat(count).isEqualTo(1);

        Optional<Comment> byId = commentRepository.findById(100L);
        assertThat(byId).isEmpty();
        comment = byId.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void crud2() {
        Comment comment = new Comment();
        comment.setLikeCount(100);
        comment.setComment("Spring data jpa");
        commentRepository.save(comment);

        List<Comment> comments = commentRepository.findByCommentContains("spring");
        assertThat(comments.size()).isEqualTo(0);

        comments = commentRepository.findByCommentContainsIgnoreCase("spring");
        assertThat(comments.size()).isEqualTo(1);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
        Page<Comment> pageComments = commentRepository.findByLikeCountGreaterThanAndPost_Id(99, null, pageRequest);
        assertThat(pageComments.getNumberOfElements()).isEqualTo(1);
        assertThat(pageComments).first().hasFieldOrPropertyWithValue("likeCount", 100);
    }

}

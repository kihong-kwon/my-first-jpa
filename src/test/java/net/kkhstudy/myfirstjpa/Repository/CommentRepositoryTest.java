package net.kkhstudy.myfirstjpa.Repository;

import net.kkhstudy.myfirstjpa.Comment;
import net.kkhstudy.myfirstjpa.Post;
import net.kkhstudy.myfirstjpa.Repository.CommentRepository;
import net.kkhstudy.myfirstjpa.Repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @Rollback(false)
    public void crud1() {
        Post post = new Post();
        post.setTitle("This is Post!");
        postRepository.save(post);

        this.createComment(100, "Hello Comment", post);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        long count = commentRepository.count();
        assertThat(count).isEqualTo(1);

        Optional<Comment> byId = commentRepository.findById(100L);
        assertThat(byId).isEmpty();

        byId = commentRepository.findById(2L);
        assertThat(byId).isNotEmpty();
        //comment = byId.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void crud2() {
        this.createComment(100, "Spring data jpa", null);

        List<Comment> comments = commentRepository.findByCommentContains("spring");
        assertThat(comments.size()).isEqualTo(0);

        comments = commentRepository.findByCommentContainsIgnoreCase("spring");
        assertThat(comments.size()).isEqualTo(1);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));
        Page<Comment> pageComments = commentRepository.findByLikeCountGreaterThanAndPost_Id(99, null, pageRequest);
        assertThat(pageComments.getNumberOfElements()).isEqualTo(1);
        assertThat(pageComments).first().hasFieldOrPropertyWithValue("likeCount", 100);
    }

    @Test
    public void asyncCrud() throws ExecutionException, InterruptedException {
        this.createComment(100, "commnet1", null);
        this.createComment(55, "commnet2", null);

        Future<List<Comment>> future = commentRepository.findByCommentContainsIgnoreCaseAndId("comment", 1L);
        future.isDone();
        System.out.println("is done? " + future.isDone());

        List<Comment> comments = future.get();
        comments.forEach(System.out::println);

    }

    private void createComment(int likeCount, String comment, Post post) {
        Comment newComment = new Comment();
        newComment.setLikeCount(likeCount);
        newComment.setComment(comment);
        newComment.setPost(post);
        commentRepository.save(newComment);
    }

}

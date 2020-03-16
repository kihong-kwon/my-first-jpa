package net.kkhstudy.myfirstjpa.Repository;

import com.querydsl.core.types.Predicate;
import net.kkhstudy.myfirstjpa.Entity.Post;
import net.kkhstudy.myfirstjpa.QPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(PostRepositoryTestConfig.class)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void applicationContext() {
        Post post = new Post();
        post.setTitle("event");
        assertThat(postRepository.contains(post)).isFalse();
        //PostPublishedEvent event = new PostPublishedEvent(post);
        //applicationContext.publishEvent(event);
        post.publish();
        postRepository.save(post);
        assertThat(postRepository.contains(post)).isTrue();

        postRepository.delete(post);
        postRepository.flush();

    }

    @Test
    @Rollback(false)
    public void crudRepository() {
        // Given
        Post post = new Post();
        post.setTitle("Hello spring boot common");
        assertThat(post.getId()).isNull();

        // When
        Post newPost = postRepository.save(post);

        // Then
        assertThat(newPost.getId()).isNotNull();

        // When
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts).contains(newPost);

        // When
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);

        // When
        page = postRepository.findByTitleContains("spring", PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
    }

    @Test
    public void customRepository() {
        Post post = new Post();
        post.setTitle("Test post");
        assertThat(postRepository.contains(post)).isFalse();
        postRepository.save(post);
        assertThat(postRepository.contains(post)).isTrue();
    }

    @Test
    public void queryDslTest() {
        Post post = new Post();
        post.setTitle("Test post");
        postRepository.save(post);
        Predicate predicate = QPost.post.title.contains("post");
        Optional<Post> ret = postRepository.findOne(predicate);
        assertThat(ret).isNotNull();
    }
}

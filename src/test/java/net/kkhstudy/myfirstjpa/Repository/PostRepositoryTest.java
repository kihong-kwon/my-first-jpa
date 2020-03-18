package net.kkhstudy.myfirstjpa.Repository;

import com.querydsl.core.types.Predicate;
import net.kkhstudy.myfirstjpa.Entity.Post;
import net.kkhstudy.myfirstjpa.Entity.QPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(PostRepositoryTestConfig.class)
@ActiveProfiles("test")
@Transactional
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void saveTest() {
        Post post = new Post();
        post.setTitle("jpa");
        // 엔티티의 @Id 프로퍼티를 찾는다. 해당 프로퍼티가 null이면 Transient 상태로 판단하고
        // id가 null이 아니면 Detached 상태로 판단한다.
        // Transient -> Persistent
        Post savedPost = postRepository.save(post); // insert(EntityManager.persist(E e))

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(savedPost == post).isTrue();

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        // Merged 메소드에 넘긴 그 엔티티의 복사본을 만들고, 그 복사본을 다시 Persistent 상태로 변경하고 그 복사본을 반환한다.
        // Transient -> Persistent
        Post updatedPost = postRepository.save(postUpdate); // update(EntityManager.merge(E e))

        assertThat(entityManager.contains(updatedPost)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(updatedPost == postUpdate).isFalse();

        // updatedPost는 JPA에서 관리되는 객체이기 때문에 jpa hibernate로 업데이트 된다.
        updatedPost.setTitle("jpa hibernate");

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        // 결론, 항상 repository 쿼리를 실행하면 반환되는 결과의 오브젝트로 다음 처리를 실핸하다.
        // 그렇지 않으면 JPA가 persistent의 상태변화를 체크하지 못하게 된다.
    }

    @Test
    public void applicationContextTest() {
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
    public void crudRepositoryTest() {
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
    public void pageableTest() throws Exception {
        Post post = new Post();
        post.setTitle("jpa");
        postRepository.save(post);

        mockMvc.perform(get("/postsPageable")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "created,desc")
                    .param("sort", "title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    jsonPath("$.content[0].title", is("jpa"));
                });
    }

    @Test
    public void customRepositoryTest() {
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

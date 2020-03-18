package net.kkhstudy.myfirstjpa.Controller;

import net.kkhstudy.myfirstjpa.Entity.Post;
import net.kkhstudy.myfirstjpa.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    // DomainClassConverter에 의해 아이디를 엔티티로 엔티티를 아이디로 컨버팅해주는 기능
    // @PathVariable("id")로 엔티티의 아이디를 명시해준다.
    // id에 해당하는 것을 엔티티로 변한해준다.
    @GetMapping("/posts/{id}")
    public String getAPost(@PathVariable("id")Post post) {
        return post.getTitle();
    }
    /*
    @GetMapping("/posts/{id}")
    public String getAPost(@PathVariable Long id) {
        Optional<Post> ret = postRepository.findById(id);
        Post post = ret.get();
        return post.getTitle();
    }*/

    @GetMapping("/postsPageable")
    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping("/posts")
    public PagedResources<Resource<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toResource(postRepository.findAll(pageable));
    }
}

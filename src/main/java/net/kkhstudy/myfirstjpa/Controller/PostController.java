package net.kkhstudy.myfirstjpa.Controller;

import net.kkhstudy.myfirstjpa.Entity.Post;
import net.kkhstudy.myfirstjpa.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts/{id}")
    public String getAPost(@PathVariable("id")Post post) {
        return post.getTitle();
    }

    @GetMapping("/posts")
    public PagedResources<Resource<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
        return assembler.toResource(postRepository.findAll(pageable));
    }
}

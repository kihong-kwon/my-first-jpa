package net.kkhstudy.myfirstjpa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    // cascade : Post가 persistent 상태가 될때 연관된 comment도 같이 persistent 상태로 넘어간다.
    // Fatch : 연관된 엔티티를 가져오는 방식, OneToMany의 경우 디폴트는 Lazy(나중에) , ManyToOne의 경우 디폴트는 Eager(지금)
    //         주의! Fatch모드의 설정에 따라 N+1 문제가 발생하지 않도록 주의, Many의 쿼리가 N번 발생하는 문제.
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.setPost(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}

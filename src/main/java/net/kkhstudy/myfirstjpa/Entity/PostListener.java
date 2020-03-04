package net.kkhstudy.myfirstjpa.Entity;

import org.springframework.context.ApplicationListener;

public class PostListener implements ApplicationListener<PostPublishedEvent> {

    @Override
    public void onApplicationEvent(PostPublishedEvent pub) {
        System.out.println("------------------------");
        System.out.println(pub.getPost().getTitle() + "is published!");
        System.out.println("------------------------");
    }
}

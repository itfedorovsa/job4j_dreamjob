package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostStore;

import java.util.Collection;

public class PostService {
    private static final PostStore INSTANCE = PostStore.instOf();

    private PostService() {
    }

    public static PostStore getInst() {
       return INSTANCE;
    }

    public Post findById(int id) {
        return INSTANCE.findById(id);
    }

    public void update(Post post) {
        INSTANCE.update(post);
    }

    public Collection<Post> findAll() {
        return INSTANCE.findAll();
    }

    public void add(Post post) {
        INSTANCE.add(post);
    }
}

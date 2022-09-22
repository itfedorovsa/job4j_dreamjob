package ru.job4j.dreamjob.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostStore {
    private final AtomicInteger counter = new AtomicInteger(3);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "post1", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "post2", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "post3", LocalDateTime.now()));
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(counter.incrementAndGet());
        posts.putIfAbsent(post.getId(), post);
    }
}

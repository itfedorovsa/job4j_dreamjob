package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Post;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostDBStoreTest {
    @Test
    public void whenFindPostById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(6, "Java Job");
        store.add(post);
        assertThat(store.findById(post.getId()), is(6));
    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post1 = new Post(6, "Java Job");
        Post post2 = new Post(2, "New Java Job");
        store.add(post1);
        store.add(post2);
        List<Post> expected = new ArrayList<>();
        expected.add(post1);
        expected.add(post2);
        assertThat(store.findAll(), is(expected));
    }

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job");
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job");
        store.add(post);
        store.update(new Post(0, "New Java Job"));
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

}
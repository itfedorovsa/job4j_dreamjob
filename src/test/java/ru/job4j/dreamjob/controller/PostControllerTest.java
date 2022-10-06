package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostControllerTest {
    public final Post input = new Post(1, "New post", new City(1, "Atlanta"));

    @Test
    public void whenCreatePost() {
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenAddPost() {
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.addPost(model, session);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFormUpdatePost() {
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findById(1)).thenReturn(input);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, 1, session);
        verify(model).addAttribute("post", input);
        assertThat(page, is("updatePost"));
    }
}
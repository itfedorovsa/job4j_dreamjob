package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        System.out.println(regUser.toString());
        if (regUser.isEmpty()) {
            model.addAttribute("message", "A user with this email already exists");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/formAddUser")
    public String addPost(Model model) {
        model.addAttribute("user", new User(0, "Name", "Email", "Password"));
        return "addUser";
    }

    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("user", new User(0, "Name", "Email", "Password"));
        return "success";
    }

    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("user", new User(0, "Name", "Email", "Password"));
        return "fail";
    }

}

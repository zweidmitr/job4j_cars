package ru.job4j.car.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.car.model.User;
import ru.job4j.car.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("saveUserTest")
    public String saveUserTest(@ModelAttribute User user, HttpSession session) {
        Optional<User> userDb = userService.add(user);
        if (userDb.isEmpty()) {
            return "redirect:/addUser?failSave=true";
        }
        return "redirect:/addUser";
    }

    @GetMapping("/addUser")
    public String addUser(Model model, HttpSession session,
                          @RequestParam(name = "failSave", required = false) Boolean failSave) {
        model.addAttribute("failSave", failSave != null);
        model.addAttribute("users", userService.findAll());
        return "addUser";
    }

    @GetMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") int id) {
        userService.delete(id);
        return "redirect:/addUser";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "failLog", required = false) Boolean failLog,
                            @RequestParam(name = "failSave", required = false) Boolean failSave) {
        model.addAttribute("failLog", failLog != null);
        model.addAttribute("failSave", failSave != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session) {
        Optional<User> userDb = userService.findByEmail(user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?failLog=true";
        }
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute User user, HttpSession session) {
        Optional<User> userDb = userService.add(user);
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?failSave=true";
        }
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}

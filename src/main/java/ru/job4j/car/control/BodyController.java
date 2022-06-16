package ru.job4j.car.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.car.model.Body;
import ru.job4j.car.service.BodyService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class BodyController {
    private final BodyService bodyService;

    public BodyController(BodyService bodyService) {
        this.bodyService = bodyService;
    }

    @GetMapping("/addBody")
    public String addBody(Model model, HttpSession session,
                          @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("bodies", bodyService.findAll());
        return "addBody";
    }

    @PostMapping("saveBody")
    public String saveBody(@ModelAttribute Body body, HttpSession session) {
        body.setName(body.getName().toUpperCase());
        Optional<Body> bodyDb = bodyService.add(body);
        if (bodyDb.isEmpty()) {
            return "redirect:/addBody?fail=true";
        }
        return "redirect:/addBody";
    }

    @GetMapping("/deleteBody/{bodyId}")
    public String deleteBody(@PathVariable("bodyId") int id) {
        bodyService.delete(id);
        return "redirect:/addBody";
    }
}

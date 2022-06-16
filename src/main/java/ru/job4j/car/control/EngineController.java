package ru.job4j.car.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.car.model.Engine;
import ru.job4j.car.service.EngineService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class EngineController {
    private final EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }

    @GetMapping("/addEngine")
    public String addBody(Model model, HttpSession session,
                          @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("engines", engineService.findAll());
        return "addEngine";
    }

    @PostMapping("saveEngine")
    public String saveBody(@ModelAttribute Engine engine, HttpSession session) {
        engine.setName(engine.getName().toUpperCase());
        Optional<Engine> engineDb = engineService.add(engine);
        if (engineDb.isEmpty()) {
            return "redirect:/addEngine?fail=true";
        }
        return "redirect:/addEngine";
    }

    @GetMapping("/deleteEngine/{engineId}")
    public String deleteEngine(@PathVariable("engineId") int id) {
        engineService.delete(id);
        return "redirect:/addEngine";
    }
}

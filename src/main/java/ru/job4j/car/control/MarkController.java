package ru.job4j.car.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.car.model.Mark;
import ru.job4j.car.service.MarkService;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.TreeSet;

@ThreadSafe
@Controller
public class MarkController {
    private final MarkService markService;

    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping("/addMark")
    public String addMark(Model model, HttpSession session,
                          @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("marks", markService.findAll());
        return "addMark";
    }

    @PostMapping("/saveMark")
    public String saveMark(@ModelAttribute Mark mark, HttpSession session) {
        mark.setName(mark.getName().toUpperCase());
        Optional<Mark> markDb = markService.add(mark);
        if (markDb.isEmpty()) {
            return "redirect:/addMark?fail=true";
        }
        return "redirect:/addMark";
    }

    @GetMapping("/deleteMark/{markId}")
    public String deleteMark(@PathVariable("markId") int id) {
        markService.delete(id);
        return "redirect:/addMark";
    }
}

package ru.job4j.car.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.car.model.Transmission;
import ru.job4j.car.service.TransmissionService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class TransmissionController {
    private final TransmissionService transmissionService;

    public TransmissionController(TransmissionService transmissionService) {
        this.transmissionService = transmissionService;
    }

    @GetMapping("/addTransmission")
    public String addBody(Model model, HttpSession session,
                          @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("transmissions", transmissionService.findAll());
        return "addTransmission";
    }

    @PostMapping("saveTransmission")
    public String saveBody(@ModelAttribute Transmission transmission, HttpSession session) {
        transmission.setName(transmission.getName().toUpperCase());
        Optional<Transmission> transmissionDB = transmissionService.add(transmission);
        if (transmissionDB.isEmpty()) {
            return "redirect:/addTransmission?fail=true";
        }
        return "redirect:/addTransmission";
    }

    @GetMapping("/deleteTransmission/{transmissionId}")
    public String deleteTrans(@PathVariable("transmissionId") int id) {
        transmissionService.delete(id);
        return "redirect:/addTransmission";
    }
}

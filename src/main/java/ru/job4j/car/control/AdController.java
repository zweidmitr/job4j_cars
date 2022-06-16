package ru.job4j.car.control;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.car.model.*;
import ru.job4j.car.service.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class AdController {
    private final AdService adService;
    private final MarkService markService;
    private final BodyService bodyService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final PhotoService photoService;

    public AdController(AdService adService,
                        MarkService markService,
                        BodyService bodyService,
                        EngineService engineService,
                        TransmissionService transmissionService,
                        PhotoService photoService) {
        this.adService = adService;
        this.markService = markService;
        this.bodyService = bodyService;
        this.engineService = engineService;
        this.transmissionService = transmissionService;
        this.photoService = photoService;
    }

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ads", adService.findAll());
        return "index";
    }

    @GetMapping("/indexByMark/{idMark}")
    public String indexByMark(Model model, HttpSession session,
                              @PathVariable("idMark") int idMark) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ads", adService.findByMark(idMark));
        return "index";
    }

    @GetMapping("/addAd")
    public String addAd(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        return "addAd";
    }

    @PostMapping("saveAd")
    public String saveAd(@ModelAttribute Advertisement ad,
                         HttpSession session,
                         @RequestParam(value = "idMark", required = false) int idMark,
                         @RequestParam(value = "idBody", required = false) int idBody,
                         @RequestParam(value = "idEngine", required = false) int idEngine,
                         @RequestParam(value = "idTransmission", required = false) int idTransmission,
                         @RequestParam(value = "files") MultipartFile[] files) throws IOException {
        setUpAd(ad, session, idMark, idBody, idEngine, idTransmission);
        adService.add(ad);
        for (MultipartFile file : files) {
            Photo ph = Photo.of(file.getBytes(), ad);
            photoService.add(ph);
        }
        return "redirect:/index";
    }

    @PostMapping("/updateAd")
    public String updateAd(@ModelAttribute Advertisement ad, HttpSession session,
                           @RequestParam(value = "idMark", required = false) int idMark,
                           @RequestParam(value = "idBody", required = false) int idBody,
                           @RequestParam(value = "idEngine", required = false) int idEngine,
                           @RequestParam(value = "idTransmission", required = false) int idTransmission,
                           @RequestParam(value = "files") MultipartFile[] files) throws IOException {
        setUpAd(ad, session, idMark, idBody, idEngine, idTransmission);
        adService.update(ad);
        ad.getPhotos().clear();
        photoService.delete(ad.getId());
        for (MultipartFile file : files) {
            Photo ph = Photo.of(file.getBytes(), ad);
            photoService.add(ph);
        }
        return "redirect:/index";
    }

    private void setUpAd(@ModelAttribute Advertisement ad, HttpSession session,
                         @RequestParam(value = "idMark", required = false) int idMark,
                         @RequestParam(value = "idBody", required = false) int idBody,
                         @RequestParam(value = "idEngine", required = false) int idEngine,
                         @RequestParam(value = "idTransmission", required = false) int idTransmission) {
        Mark mark = markService.findById(idMark);
        Body body = bodyService.findById(idBody);
        Engine engine = engineService.findById(idEngine);
        Transmission transmission = transmissionService.findById(idTransmission);
        User user = (User) session.getAttribute("user");
        ad.setUser(user);
        ad.setMark(mark);
        ad.setBody(body);
        ad.setEngine(engine);
        ad.setTransmission(transmission);
    }

    @GetMapping("/descriptionAd/{adId}")
    public String descriptionAd(Model model, HttpSession session,
                                @PathVariable("adId") int id,
                                @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        var byId = adService.findById(id);
        model.addAttribute("photos", byId.getPhotos());
        model.addAttribute("ad", byId);
        return "descriptionAd";
    }

    @GetMapping("/formUpdateAd/{adId}")
    public String formUpdateAd(Model model, @PathVariable("adId") int id, HttpSession session) {
        model.addAttribute("marks", markService.findAll());
        model.addAttribute("bodies", bodyService.findAll());
        model.addAttribute("engines", engineService.findAll());
        model.addAttribute("transmissions", transmissionService.findAll());
        model.addAttribute("ad", adService.findById(id));
        String check = checkUser(id, session);
        if (check != null) {
            return check;
        }
        return "updateAd";
    }

    private String checkUser(@PathVariable("adId") int id, HttpSession session) {
        Advertisement ad = adService.findById(id);
        User user = (User) session.getAttribute("user");
        String adName = ad.getUser().getName();
        String usName = user.getName();
        if (!usName.equals("Admin") && !adName.equals(usName)) {
            return "redirect:/descriptionAd/" + ad.getId() + "?fail=true";
        }
        return null;
    }

    @GetMapping("/deleteAd/{adId}")
    public String deleteAd(@PathVariable("adId") int id, HttpSession session) {
        String check = checkUser(id, session);
        if (check != null) {
            return check;
        }
        adService.delete(id);
        return "redirect:/index";
    }

    @GetMapping("/setSale/{adId}")
    public String setSale(@PathVariable("adId") int id, HttpSession session) {
        String check = checkUser(id, session);
        if (check != null) {
            return check;
        }
        adService.setSale(id);
        return "redirect:/index";
    }

    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        return "settings";
    }

    @GetMapping("/photoAd/{ph}")
    public ResponseEntity<Resource> download(@PathVariable("ph") Integer ph) {
        byte[] photo = photoService.findById(ph).getPhoto();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(photo.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(photo));
    }

    @GetMapping("/photoAdFirst/{id}")
    public ResponseEntity<Resource> downloadFirst(@PathVariable("id") Integer id) {
        Advertisement ad = adService.findById(id);
        byte[] photo = ad.getPhotos().get(0).getPhoto();
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(photo.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(photo));
    }
}

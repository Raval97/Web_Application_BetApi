package webapplication.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webapplication.bet.model.AppUser;
import webapplication.bet.repo.AppUserRepo;
import webapplication.bet.model.MatchBet;
import webapplication.bet.service.MatchBetService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

@Controller
public class ControllerApi {

    private AppUserRepo appUserRepo;

    @Autowired
    private MatchBetService service2;

    @Autowired
    public ControllerApi(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    public ControllerApi() {
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<MatchBet> listMatches = service2.listAll();
        model.addAttribute("listMatches", listMatches);
        return "index2";
    }

    @RequestMapping("/new")
    public String showNewProductPage(Model model) {
        MatchBet matchBet = new MatchBet();
        model.addAttribute("matchBet", matchBet);
        return "new_product2";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("matchBet") MatchBet matchBet) {
        service2.save(matchBet);
        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_product2");
        MatchBet matchBet = service2.get(id);
        mav.addObject("matchBet", matchBet);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        service2.delete(id);
        return "redirect:/";
    }

    @GetMapping("/hello")
    public  String get(Model model){
        model.addAttribute("name", "HELLO WORLD");
        return "hello";
    }

    @GetMapping("/test")
    public  String test(Model model){
        model.addAttribute("newUser", new AppUser());
        return "registration";
    }

    @GetMapping("/test1")
    @ResponseBody
    public  String test1(){
        return "hello user";
    }

    @GetMapping("/test2")
    @ResponseBody
    public  String test2(){
        return "hello admin";
    }

    @PostMapping("/add-user")
    public String addCar(@ModelAttribute AppUser user) {
        appUserRepo.save(user);
        return "redirect:/hello";
    }
}
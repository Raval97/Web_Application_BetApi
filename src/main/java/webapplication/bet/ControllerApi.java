package webapplication.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webapplication.bet.model.*;
import webapplication.bet.repo.*;
import webapplication.bet.service.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@Controller
public class ControllerApi {

    private AppUserRepo appUserRepo;
    private MatchService matchService;
    private CoursesService coursesService;
    private CouponCourseService couponCourseService;
    private CouponService couponService;

    @Autowired
    public ControllerApi(AppUserRepo appUserRepo, MatchService matchService,
                         CoursesService coursesService, CouponCourseService couponCourseService,
                         CouponService couponService) {
        this.appUserRepo = appUserRepo;
        this.matchService = matchService;
        this.coursesService = coursesService;
        this.couponCourseService = couponCourseService;
        this.couponService = couponService;
    }

    public ControllerApi() {
    }

    @RequestMapping("/admin")
    public String viewHomePageAdmin(Model model) {
        List<Match> listMatch = matchService.listAll();
        List<Courses> listCourses = coursesService.listAll();
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "startAdmin";
    }

    @RequestMapping("/user")
    public String viewHomePageUser(Model model) {
        List<Match> listMatch = matchService.listAll();
        List<Courses> listCourses = coursesService.listAll();
        List<CouponCourse> listCouponCourse = couponCourseService.listAll();
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        model.addAttribute("listCouponCourse", listCouponCourse);
        return "startUser";
    }

    @RequestMapping("/new")
    public String showNewMatchPage(Model model) {
        Match match = new Match();
        Course course = new Course();
        model.addAttribute("match", match);
        model.addAttribute("course", course);
//        //float course1=0;
//        Courses course1 = new Courses();
//        Courses courseX = new Courses();
//        Courses course2 = new Courses();
//        Courses course1X = new Courses();
//        Courses course2X = new Courses();
//        Courses course12 = new Courses();
//        model.addAttribute("course1", course1);
//        model.addAttribute("courseX", courseX);
//        model.addAttribute("course2", course2);
//        model.addAttribute("course1X", course1X);
//        model.addAttribute("course2X", course2X);
//        model.addAttribute("course12", course12);
        return "new_match_courses";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveMatchCourse(@ModelAttribute Match match, @ModelAttribute Course course){
//                                  @ModelAttribute("course1") Courses course1,
//                                  @ModelAttribute("courseX") Courses courseX,
//                                  @ModelAttribute("course2") Courses course2,
//                                  @ModelAttribute("course1X") Courses course1X,
//                                  @ModelAttribute("course2X") Courses course2X,
//                                  @ModelAttribute("course12") Courses course12){
        matchService.save(new Match(match.getTeam1(), match.getTeam2(), match.getLeague(),
                match.getScore(), match.getDate(), match.getTime(),
                new Courses("1", course.getC1(), true),
                new Courses("X", course.getCX(), true),
                new Courses("2", course.getC2(), true),
                new Courses("X1", course.getC1X(), true),
                new Courses("X2", course.getC2X(), true),
                new Courses("12", course.getC12(), true)));
//                        new Courses("1", courseX.getValue()),
//                        new Courses("X", courseX.getValue()),
//                        new Courses("2", course2.getValue()),
//                        new Courses("1X", course1X.getValue()),
//                        new Courses("2X", course2X.getValue()),
//                        new Courses("12", course12.getValue())));
        return "redirect:/user";
    }

    @RequestMapping("/add/{id}")
    public String addBetToCoupon(@PathVariable(name = "id") int id) {
//        couponService.save(new Coupon());
        return "redirect:/user";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditMatchPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_match_courses");
        Match match = matchService.get(id);
        Courses courses1 = coursesService.get((((id)-1)*6)+2);
        Courses coursesX = coursesService.get((((id)-1)*6)+1);
        Courses courses2 = coursesService.get((((id)-1)*6)+6);
        Courses courses1X = coursesService.get((((id)-1)*6)+5);
        Courses courses2X = coursesService.get((((id)-1)*6)+4);
        Courses courses12 = coursesService.get((((id)-1)*6)+3);
        mav.addObject("match", match);
        mav.addObject("course1", courses1);
        mav.addObject("courseX", coursesX);
        mav.addObject("course2", courses2);
        mav.addObject("course1X", courses1X);
        mav.addObject("course2X", courses2X);
        mav.addObject("course12", courses12);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteMatch(@PathVariable(name = "id") int id) {
        matchService.delete(id);
        return "redirect:/user";
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
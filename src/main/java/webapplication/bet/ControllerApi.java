package webapplication.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webapplication.bet.model.*;
import webapplication.bet.service.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;
import java.util.Optional;

@Controller
public class ControllerApi {

    private UserService userService;
    private ClientService clientService;
    private MatchService matchService;
    private CoursesService coursesService;
    private CouponCourseService couponCourseService;
    private CouponService couponService;

    @Autowired
    public ControllerApi(UserService userService, MatchService matchService,
                         CoursesService coursesService, CouponCourseService couponCourseService,
                         CouponService couponService, ClientService clientService) {
        this.userService = userService;
        this.matchService = matchService;
        this.coursesService = coursesService;
        this.couponCourseService = couponCourseService;
        this.couponService = couponService;
        this.clientService = clientService;
    }

    public ControllerApi() {
    }

//############## START USER ##########################################   http://localhost:8080/user/ALL
    @RequestMapping("/user/{league}")
    public String viewStartPage(@PathVariable(name = "league") String leagues, Model model) {
        List<Match> listMatch;
        List<Courses> listCourses;
        if(leagues.equals("ALL")) {
            listMatch = matchService.listAll();
            listCourses = coursesService.listAll();
        }
        else {
            listMatch = matchService.listAllByLeague(leagues);
            listCourses = coursesService.listAllByMatchLeague(leagues);
        }
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "startPage";
    }

//############## START USER ##########################################   http://localhost:8080/user/1/ALL
    @RequestMapping("/user/{user}/{league}")
    public String viewPageUserByLeagues(@PathVariable(name = "league") String leagues,
                                        @PathVariable(name = "user") Long user_id, Model model) {
        User user = userService.get(user_id);
        List<Match> listMatch;
        List<Courses> listCourses;
        if(leagues.equals("ALL")) {
            listMatch = matchService.listAll();
            listCourses = coursesService.listAll();
        }
        else {
            listMatch = matchService.listAllByLeague(leagues);
            listCourses = coursesService.listAllByMatchLeague(leagues);
        }
        model.addAttribute("user", user);
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "startUser";
    }

    @RequestMapping("/newBet/{user}")
    public String newBet(@PathVariable(name = "user") Long user_id) {
        System.out.println("Nowy zakład");
        couponService.newCoupon(user_id);
        int couponId=couponService.getLatsCouponId();
        return "redirect:/user/"+user_id+"/ALL/"+couponId;
    }

//################## START BET USER ########################################  http://localhost:8080/user/1/ALL/1
    @RequestMapping("/user/{user}/{league}/{coupon}")
    public String viewPageUserByLeagues(@PathVariable(name = "league") String leagues,
                                        @PathVariable(name = "user") Long user_id,
                                        @PathVariable(name = "coupon") Long coupon_id, Model model) {
        User user = userService.get(user_id);
        List<Match> listMatch;
        List<Courses> listCourses;
        if(leagues.equals("ALL")) {
            listMatch = matchService.listAll();
            listCourses = coursesService.listAll();
        }
        else {
            listMatch = matchService.listAllByLeague(leagues);
            listCourses = coursesService.listAllByMatchLeague(leagues);
        }
        List<String> listMatchNamed = matchService.listAllMatchNamed(user_id, coupon_id);
//        Coupon coupon = couponService.get(coupon_id);
        Coupon coupon = couponService.get(coupon_id);
        List<Courses> listCoursesOnBet = coursesService.listAllByUserIdInCoupon(user_id, coupon_id);
        List<Long> listCouponCourse = couponCourseService.listAllIdByUserIdInCoupon(user_id, coupon_id);
        model.addAttribute("user", user);
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        model.addAttribute("coupon", coupon);
        model.addAttribute("listMatchNamed", listMatchNamed);
        model.addAttribute("listCoursesOnBet", listCoursesOnBet);
        model.addAttribute("listCouponCourse", listCouponCourse);
        return "startUserBet";
    }

    @RequestMapping(value="/add/{idCoupon}/{idCourse}/{user}/{league}", method = RequestMethod.POST)
    public String addBetToCoupon(@PathVariable(name = "idCoupon") Long idCoupon, @PathVariable(name = "idCourse") Long idCourse,
                                 @PathVariable(name = "league") String league, @PathVariable(name = "user") Long user_id,
                                 Model model) {
        if(couponCourseService.checkIfMatchIsInCoupon(matchService.getIdByCourseId(idCourse), idCoupon))
            couponCourseService.saveNewCouponCourse(idCoupon, idCourse);
        else
            System.out.println("Mecz jest juz na kuponie i nie mozna go ponownie obstawic");
        return "redirect:/user/"+user_id+"/"+league+"/"+idCoupon;
    }

    @RequestMapping(value = "/editAmount/{idCoupon}/{user}", method = RequestMethod.POST)
    public String updateAmountOFCoupon(@ModelAttribute Coupon coupon,
                                       @PathVariable("idCoupon") Long idCoupon,
                                       @PathVariable(name = "user") Long user_id){
        couponService.updateAmount(coupon.getAmount(), idCoupon);
        return "redirect:/user/"+user_id+"/ALL/"+idCoupon;
    }

    @RequestMapping("/deleteBet/{id}/{user}/{coupon}")
    public String deleteBetFromCoupon(@PathVariable(name = "id") int id,
                                      @PathVariable(name = "user") Long user_id,
                                      @PathVariable(name = "coupon") Long coupon_id) {
        couponCourseService.delete(id);
        return "redirect:/user/"+user_id+"/ALL/"+coupon_id;
    }

    @RequestMapping("/confirmBet/{user}/{coupon}")
    public String confirmBetsInCoupon(@PathVariable(name = "user") Long user_id,
                                      @PathVariable(name = "coupon") Long coupon_id) {
        System.out.println("\n\nZakład potwierdzony\n\n");
        couponService.updateDate(coupon_id);
        return "redirect:/user/"+user_id+"/ALL";
    }

//############## USER Settings ##########################################   http://localhost:8080/user/settings/1
    @RequestMapping("/user/settings/{user}")
    public String viewPageUserSettings(@PathVariable(name = "user") Long user_id, Model model) {
        Client client = clientService.get(user_id);
        model.addAttribute("client", client);
        return "user_account";
    }

//############## USER Coupon ##########################################   http://localhost:8080/user/coupons/0
    @RequestMapping("/user/coupons/{user}/{couponId}")
    public String viewPageUserCoupons(@PathVariable(name = "user") Long user_id,
                                      @PathVariable(name = "couponId") Long couponId, Model model) {
        User user = userService.get(user_id);
        List<Coupon> couponList = couponService.listAllByUserId(user_id);
        Coupon coupon=null;// =couponService.get(couponId);
        if(couponId!=0) {
            List<Match> matchList = matchService.listAllByCouponId(couponId);
            List<Courses> coursesList = coursesService.listAllByCouponId(couponId);
            coupon =couponService.get(couponId);
            model.addAttribute("mathList", matchList);
            model.addAttribute("coursesList", coursesList);

        }
        model.addAttribute("coupon", coupon);
        model.addAttribute("user", user);
        model.addAttribute("couponList", couponList);
        return "user_coupons";
    }

//################  ADMIN  ################################################   http://localhost:8080/admin/ALL
    @RequestMapping("/admin/{league}")
    public String viewHomePageAdmin(@PathVariable(name = "league") String leagues, Model model) {
        List<Match> listMatch;
        List<Courses> listCourses;
        if(leagues.equals("ALL")) {
            listMatch = matchService.listAll();
            listCourses = coursesService.listAll();
        }
        else {
            listMatch = matchService.listAllByLeague(leagues);
            listCourses = coursesService.listAllByMatchLeague(leagues);
        }
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "startAdmin";

    }

    @RequestMapping("/new")
    public String showNewMatchPage(Model model) {
        Match match = new Match();
        Course course = new Course();
        model.addAttribute("match", match);
        model.addAttribute("course", course);
        return "new_match_courses";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveMatchCourse(@ModelAttribute Match match, @ModelAttribute Course course){
        matchService.save(new Match(match.getTeam1(), match.getTeam2(), match.getLeague(),
                match.getScore(), match.getDate(), match.getTime(),
                new Courses("1", course.getC1(), "N/A"),
                new Courses("X", course.getCX(), "N/A"),
                new Courses("2", course.getC2(), "N/A"),
                new Courses("X1", course.getC1X(), "N/A"),
                new Courses("X2", course.getC2X(), "N/A"),
                new Courses("12", course.getC12(), "N/A")));
        return "redirect:/admin/ALL";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditMatchPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_match_courses");
        Match match = matchService.get(id);
        Course course = new Course();
        Courses courses1 = coursesService.getByMatchIdAndType(id, "1");
        Courses coursesX = coursesService.getByMatchIdAndType(id, "X");
        Courses courses2 = coursesService.getByMatchIdAndType(id, "2");
        Courses courses1X = coursesService.getByMatchIdAndType(id, "X1");
        Courses courses2X = coursesService.getByMatchIdAndType(id, "X2");
        Courses courses12 = coursesService.getByMatchIdAndType(id, "12");
        mav.addObject("match", match);
        mav.addObject("course", course);
        mav.addObject("course1", courses1);
        mav.addObject("courseX", coursesX);
        mav.addObject("course2", courses2);
        mav.addObject("course1X", courses1X);
        mav.addObject("course2X", courses2X);
        mav.addObject("course12", courses12);
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editMatchCourse(@ModelAttribute ("match") Match match,
                                  @ModelAttribute("course") Course course){
        coursesService.updateCurseValue(course.getC1(),coursesService.getByMatchIdAndType(match.getId(), "1").getId());
        coursesService.updateCurseValue(course.getCX(),coursesService.getByMatchIdAndType(match.getId(), "X").getId());
        coursesService.updateCurseValue(course.getC2(),coursesService.getByMatchIdAndType(match.getId(), "2").getId());
        coursesService.updateCurseValue(course.getC1X(),coursesService.getByMatchIdAndType(match.getId(), "X1").getId());
        coursesService.updateCurseValue(course.getC2X(),coursesService.getByMatchIdAndType(match.getId(), "X2").getId());
        coursesService.updateCurseValue(course.getC12(),coursesService.getByMatchIdAndType(match.getId(), "12").getId());
        matchService.updateMatch(match.getId(), match.getLeague(), match.getScore(), match.getTeam1(),
                match.getTeam2(), match.getDate(), match.getTime());
        return "redirect:/admin/ALL";
    }

    @RequestMapping("/deleteMatch/{id}")
    public String deleteMatch(@PathVariable(name = "id") int id) {
        matchService.delete(id);
        return "redirect:/admin/ALL";
    }

//################  LOGOWANIE & REJESTRACJA  ##################################  http://localhost:8080/hello
    @GetMapping("/hello")
    public  String get(Model model){
        model.addAttribute("name", "HELLO WORLD");
        return "hello";
    }

    @GetMapping("/registration")
    public  String test(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/add-user")
    public String addCar(@ModelAttribute User user, @ModelAttribute Client client) {
        client.setBetAccountBalance(1000);
        User newUser = new User(user.getUsername(), user.getPassword(), "ROLE_USER", client);
        userService.save(newUser);
        return "redirect:/user/ALL";
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

}
package webapplication.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import webapplication.bet.model.*;
import webapplication.bet.security.WebSecurityConfig;
import webapplication.bet.service.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/admin/ALL/actual";
        }
        return "redirect:/client/ALL/actual";
    }

    //############## START USER ##########################################
    @RequestMapping("/user/{league}/{type}")
    public String viewStartPage(@PathVariable(name = "league") String leagues,
                                @PathVariable(name = "type") String type, Model model) {
        List<Match> listMatch;
        List<Courses> listCourses;
        if(type.equals("ended")) {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllFinishedBet();
                listCourses = coursesService.listAllFinishedBet();
            } else {
                listMatch = matchService.listFinishedBetByLeague(leagues);
                listCourses = coursesService.listFinishedBetByLeague(leagues);
            }
        }
        else {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllActualToBet();
                listCourses = coursesService.listAllActualToBet();
            } else {
                listMatch = matchService.listActualToBetByLeague(leagues);
                listCourses = coursesService.listActualToBetByLeague(leagues);
            }
        }
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "startPage";
    }
    //############## START USER ##########################################
    @RequestMapping("/client/{league}/{type}")
    public String viewPageUserByLeagues(@PathVariable(name = "league") String leagues,
                                        @PathVariable(name = "type") String type, Model model) {
        User user = userService.findUserByUsername(User.getUserName());
        List<Match> listMatch;
        List<Courses> listCourses;
        if(type.equals("ended")) {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllFinishedBet();
                listCourses = coursesService.listAllFinishedBet();
            } else {
                listMatch = matchService.listFinishedBetByLeague(leagues);
                listCourses = coursesService.listFinishedBetByLeague(leagues);
            }
        }
        else {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllActualToBet();
                listCourses = coursesService.listAllActualToBet();
            } else {
                listMatch = matchService.listActualToBetByLeague(leagues);
                listCourses = coursesService.listActualToBetByLeague(leagues);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "client";
    }

     @RequestMapping("/newBet/{user}")
    public String newBet(@PathVariable(name = "user") Long user_id) {
        couponService.newCoupon(user_id);
        int couponId=couponService.getLatsCouponId();
        return "redirect:/client/ALL/actual/"+couponId;
    }

    //################## START BET USER ########################################
    @RequestMapping("/client/{league}/{type}/{coupon}")
    public String viewPageUserByLeagues(@PathVariable(name = "league") String leagues,
                                        @PathVariable(name = "coupon") Long coupon_id,
                                        @PathVariable(name = "type") String type, Model model) {
        User user = userService.findUserByUsername(User.getUserName());
        List<Match> listMatch;
        List<Courses> listCourses;
        if(type.equals("ended")) {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllFinishedBet();
                listCourses = coursesService.listAllFinishedBet();
            } else {
                listMatch = matchService.listFinishedBetByLeague(leagues);
                listCourses = coursesService.listFinishedBetByLeague(leagues);
            }
        }
        else {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllActualToBet();
                listCourses = coursesService.listAllActualToBet();
            } else {
                listMatch = matchService.listActualToBetByLeague(leagues);
                listCourses = coursesService.listActualToBetByLeague(leagues);
            }
        }
        List<String> listMatchNamed = matchService.listAllMatchNamed(user.getId(), coupon_id);
        Coupon coupon = couponService.get(coupon_id);
        List<Courses> listCoursesOnBet = coursesService.listAllByUserIdInCoupon(user.getId(), coupon_id);
        List<Long> listCouponCourse = couponCourseService.listAllIdByUserIdInCoupon(user.getId(), coupon_id);
        model.addAttribute("user", user);
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        model.addAttribute("coupon", coupon);
        model.addAttribute("listMatchNamed", listMatchNamed);
        model.addAttribute("listCoursesOnBet", listCoursesOnBet);
        model.addAttribute("listCouponCourse", listCouponCourse);
        return "clientBet";
    }

    @RequestMapping(value="/add/{idCoupon}/{idCourse}/{league}", method = RequestMethod.POST)
    public String addBetToCoupon(@PathVariable(name = "idCoupon") Long idCoupon, @PathVariable(name = "idCourse") Long idCourse,
                                 @PathVariable(name = "league") String league) {
        if(couponCourseService.checkIfMatchIsInCoupon(matchService.getIdByCourseId(idCourse), idCoupon))
            couponCourseService.saveNewCouponCourse(idCoupon, idCourse);
        else
            System.out.println("Mecz jest juz na kuponie i nie mozna go ponownie obstawic");
        return "redirect:/client/"+league+"/actual/"+idCoupon;
    }

    @RequestMapping(value = "/editAmount/{idCoupon}/{league}/{type}", method = RequestMethod.POST)
    public String updateAmountOFCoupon(@ModelAttribute Coupon coupon,
                                       @PathVariable("idCoupon") Long idCoupon,
                                       @PathVariable(name = "league") String league,
                                       @PathVariable(name = "type") String type){
        couponService.updateAmount(coupon.getAmount(), idCoupon);
        return "redirect:/client/"+league+"/"+type+'/'+idCoupon;
    }

    @RequestMapping("/deleteBet/{id}/{league}/{coupon}/{type}")
    public String deleteBetFromCoupon(@PathVariable(name = "id") int id,
                                      @PathVariable(name = "league") String league,
                                      @PathVariable(name = "coupon") Long coupon_id,
                                      @PathVariable(name = "type") String type) {
        couponCourseService.delete(id);
        return "redirect:/client/"+league+"/"+type+'/'+coupon_id;
    }

    @RequestMapping("/confirmBet/{league}/{coupon}/{type}")
    public String confirmBetsInCoupon(@PathVariable(name = "league") String league,
                                      @PathVariable(name = "coupon") Long coupon_id,
                                      @PathVariable(name = "type") String type) {
        if(couponService.checkAvailabilityMoney(coupon_id)) {
            System.out.println("\n\nZakład potwierdzony\n\n");
            couponService.updateDate(coupon_id);
            return "redirect:/client/" + league + "/"+type;
        }
        else {
            System.out.println("\n\nNie wystarczajaca ilosc srodkow na koncie\n\n");
            return "redirect:/client/" + league + "/"+type+'/'+coupon_id;
        }
    }

    //############## USER ACCOUNT ##########################################
    @RequestMapping("/client/settings/{type}")
    public String viewPageUserSettings(@PathVariable(name = "type") int type, Model model) {
        User user = userService.findUserByUsername(User.getUserName());
        Client client = clientService.get(user.getId());
        model.addAttribute("client", client);
        model.addAttribute("type", type);
        return "client_account";
    }

    @RequestMapping("/client/edit/{type}")
    public ModelAndView showEditMatchPage(@PathVariable(name = "type") int type) {
        ModelAndView mav = new ModelAndView("client_edit_date");
        User user = userService.findUserByUsername(User.getUserName());
        Client client = clientService.get(user.getId());
        String oldPassword= new String();
        String newPassword= new String();
        mav.addObject("user", user);
        mav.addObject("client", client);
        mav.addObject("type", type);
        mav.addObject("oldPassword", oldPassword);
        mav.addObject("newPassword", newPassword);
        return mav;
    }

    @RequestMapping(value = "/client/edit_data/{id}", method = RequestMethod.POST)
    public String editClientData(@PathVariable(name = "id") Long id,
                                 @ModelAttribute ("client") Client client){
        User userToChange = userService.get(id);
        userToChange.setClient(client);
        userService.save(userToChange);
        return "redirect:/client/settings/1";
    }

    @RequestMapping(value = "/client/change_password/{id}", method = RequestMethod.POST)
    public String changeClientPassword(@PathVariable(name = "id") Long id,
                                       @ModelAttribute ("user") User user,
                                       @RequestParam  String newPassword,
                                       @RequestParam String oldPassword){
        User userToChange = userService.get(id);
        if(WebSecurityConfig.passwordEncoder().matches(oldPassword, userToChange.getPassword())) {
            userToChange.setPassword(newPassword);
            userService.save(userToChange);
            return "redirect:/client/settings/1";
        }
        else
            return "redirect:/client/edit/3";
    }
    //############## USER Coupon ##########################################
    @RequestMapping("/client/coupons/{couponId}")
    public String viewPageUserCoupons(@PathVariable(name = "couponId") Long couponId, Model model) {
        User user = userService.findUserByUsername(User.getUserName());
        List<Coupon> couponList = couponService.listAllByUserId(user.getId());
        Coupon coupon=null;
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
        return "client_coupons";
    }

    //################  ADMIN  ################################################
    @RequestMapping("/admin/{league}/{type}")
    public String viewHomePageAdmin(@PathVariable(name = "league") String leagues,
                                    @PathVariable(name = "type") String type, Model model) {
        List<Match> listMatch;
        List<Courses> listCourses;
        if(type.equals("ended")) {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllFinishedBet();
                listCourses = coursesService.listAllFinishedBet();
            } else {
                listMatch = matchService.listFinishedBetByLeague(leagues);
                listCourses = coursesService.listFinishedBetByLeague(leagues);
            }
        }
        else {
            if (leagues.equals("ALL")) {
                listMatch = matchService.listAllActualToBet();
                listCourses = coursesService.listAllActualToBet();
            } else {
                listMatch = matchService.listActualToBetByLeague(leagues);
                listCourses = coursesService.listActualToBetByLeague(leagues);
            }
        }
        model.addAttribute("listMatch", listMatch);
        model.addAttribute("listCourses", listCourses);
        return "admin";

    }

    @RequestMapping("/new")
    public String showNewMatchPage(Model model) {
        Match match = new Match();
        Course course = new Course();
        model.addAttribute("match", match);
        model.addAttribute("course", course);
        return "admin_new_match_courses";
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
        return "redirect:/admin/ALL/actual";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditMatchPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("admin_edit_match_courses");
        Match match = matchService.get(id);
        Course course = new Course();
        Courses courses1 = coursesService.getByMatchIdAndType(id, "1");
        Courses coursesX = coursesService.getByMatchIdAndType(id, "X");
        Courses courses2 = coursesService.getByMatchIdAndType(id, "2");
        Courses courses1X = coursesService.getByMatchIdAndType(id, "X1");
        Courses courses2X = coursesService.getByMatchIdAndType(id, "X2");
        Courses courses12 = coursesService.getByMatchIdAndType(id, "12");
        course.setC1(courses1.getValue());
        course.setC2(courses2.getValue());
        course.setCX(coursesX.getValue());
        course.setC1X(courses1X.getValue());
        course.setC2X(courses2X.getValue());
        course.setC12(courses12.getValue());
        mav.addObject("match", match);
        mav.addObject("course", course);
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
        return "redirect:/admin/ALL/actual";
    }

    @RequestMapping("/deleteMatch/{id}")
    public String deleteMatch(@PathVariable(name = "id") int id) {
        matchService.delete(id);
        return "redirect:/admin/ALL/actual";
    }

    //################  LOGOWANIE & REJESTRACJA  ##################################

    @GetMapping("/registration")
    public  String test(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/add-user")
    public String addUser(@ModelAttribute User user, @ModelAttribute Client client) {
        client.setBetAccountBalance(1000);
        User newUser = new User(user.getUsername(), user.getPassword(), "ROLE_USER", client);
        userService.save(newUser);
        return "redirect:/user/ALL";
    }

}
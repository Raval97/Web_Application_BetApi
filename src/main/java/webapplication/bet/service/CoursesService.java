package webapplication.bet.service;

import org.springframework.data.domain.Sort;
import webapplication.bet.model.Courses;
import webapplication.bet.repo.CoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CoursesService {

    @Autowired
    private CoursesRepository repo;

    public List<Courses> listAll() {
        return repo.findAll(Sort.by("type").ascending().and(Sort.by("id").ascending()));
    }

    public void save(Courses courses) {
        repo.save(courses);
    }

    public Courses get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }


    public List<Courses> listAllActualToBet() {
        return repo.findAllActualToBet();
    }

    public List<Courses> listAllFinishedBet() {
        return repo.findAllFinishedBet();
    }

    public List<Courses> listActualToBetByLeague(String league) {
        return repo.findActualToBetByLeague(league);
    }

    public List<Courses> listFinishedBetByLeague(String league) {
        return repo.findFinishedBetByLeague(league);
    }

    public List<Courses> listAllByMatchLeague(String league){
        return repo.findAllByMatch_LeagueOrderByTypeAscIdAsc(league);
    }

    public List<Courses> listAllByCouponId(Long id){
        return repo.findAllByCouponId(id);
    }

    public List<Courses> listAllByUserIdInCoupon(Long idUser, Long couponId){
        return repo.findAllByUserIdInCouponId(idUser, couponId);
    }

    public Courses getByMatchIdAndType(Long id, String type){
        return repo.findByMatch_IdAndType(id, type);
    }

    public void updateCurseValue(float value, Long id){
        repo.updateValue(value, id);
    }

}
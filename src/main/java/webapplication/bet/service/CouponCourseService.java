package webapplication.bet.service;

import webapplication.bet.model.CouponCourse;
import webapplication.bet.repo.CouponCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CouponCourseService {

    @Autowired
    private CouponCourseRepository repo;

    public List<CouponCourse> listAll() {
        return repo.findAll();
    }

    public void save(CouponCourse couponCourse) {
        repo.save(couponCourse);
    }

    public CouponCourse get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }


    public List<Long> listAllIdByUserIdInCoupon(Long idUser, Long couponId){
        return repo.findAllIdByUserIdInCoupon(idUser, couponId);
    }

    public void saveNewCouponCourse(Long idCoupon, Long idCourse){
        repo.newCouponCourse(idCoupon, idCourse);
    }

    public boolean checkIfMatchIsInCoupon(Long idMatch, Long idCoupon){
        if(repo.checkMatchInCoupon(idMatch, idCoupon)==1)
            return true;
        else
            return false;
    }
}
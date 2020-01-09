package webapplication.bet.service;

import webapplication.bet.model.Coupon;
import webapplication.bet.repo.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CouponService {

    @Autowired
    private CouponRepository repo;

    public List<Coupon> listAll() {
        return repo.findAll();
    }

    public void save(Coupon coupon) {
        repo.save(coupon);
    }

    public Coupon get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }


    public List<Coupon> listAllByUserId(Long id){
        return repo.findAllByIdUser(id);
    }

    public boolean checkAvailabilityMoney(Long id){
        if(repo.checkAvailabilityMoney(id).equals("true"))
            return true;
        else
            return false;
    }

    public void  updateAmount(float amount, Long id){
        repo.updateAmount(amount, id);
    }

    public void  updateDate(Long id){
        repo.updateDate(id);
    }

    public void newCoupon(Long id){
        repo.addCoupon(id);
    }

    public int getLatsCouponId(){
        return repo.getLastCouponId();
    }
}
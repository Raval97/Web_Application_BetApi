package webapplication.bet.security;

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
}
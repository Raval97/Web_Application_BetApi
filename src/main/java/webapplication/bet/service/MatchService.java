package webapplication.bet.service;

import org.springframework.data.repository.query.Param;
import webapplication.bet.model.Match;
import webapplication.bet.repo.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class MatchService {

    @Autowired
    private MatchRepository repo;

    public List<Match> listAll() {
        return repo.findAll();
    }

    public void save(Match match) {
        repo.save(match);
    }

    public Match get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }


    public List<Match> listAllActualToBet() {
        return repo.findAllActualToBet();
    }

    public List<Match> listAllFinishedBet() {
        return repo.findAllFinishedBet();
    }

    public List<Match> listActualToBetByLeague(String league) {
        return repo.findActualToBetByLeague(league);
    }

    public List<Match> listFinishedBetByLeague(String league) {
        return repo.findFinishedBetByLeague(league);
    }

    public List<Match> listAllByLeague(String league){
        return  repo.findByLeague(league);
    }

    public List<Match> listAllByCouponId(Long id){
        return repo.findAllByCouponId(id);
    }

    public List<String> listAllMatchNamed(Long idUser, Long couponId){
        return repo.findAllMatchNamed(idUser, couponId);
    }

    public Long getIdByCourseId(Long id){
        return repo.findIdByCourses_Id(id);
    }

    public void updateMatch(Long id,  String league, String score, String team1,
                            String team2, LocalDate date, LocalTime time){
        repo.updateMatch(id, league, score, team1, team2, date, time);
    }

}
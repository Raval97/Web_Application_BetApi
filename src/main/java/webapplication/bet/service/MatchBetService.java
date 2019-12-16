package webapplication.bet.service;

import webapplication.bet.model.MatchBet;
import webapplication.bet.repo.MatchBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MatchBetService {

    @Autowired
    private MatchBetRepository repo;

    public List<MatchBet> listAll() {
        return repo.findAll();
    }

    public void save(MatchBet matchBet) {
        repo.save(matchBet);
    }

    public MatchBet get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
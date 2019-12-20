package webapplication.bet.security;

import webapplication.bet.model.Match;
import webapplication.bet.repo.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
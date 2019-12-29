package webapplication.bet.service;

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
        return repo.findAll();
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
}
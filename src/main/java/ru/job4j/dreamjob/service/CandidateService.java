package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDBStore store;
    private final CityService cityService;

    private CandidateService(CandidateDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = store.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return candidates;
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }
}

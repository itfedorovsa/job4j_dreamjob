package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore CANDIDATE_INST = new CandidateStore();

    private final AtomicInteger counter = new AtomicInteger(3);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "John", "desc1", LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Jade", "desc2", LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Jim", "desc3", LocalDateTime.now()));
    }

    public static CandidateStore instanceOf() {
        return CANDIDATE_INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void add(Candidate candidate) {
        candidate.setId(counter.incrementAndGet());
        candidates.putIfAbsent(candidate.getId(), candidate);
    }

    public void update(Candidate candidate) {
        candidate.setCreated(LocalDateTime.now());
        candidates.replace(candidate.getId(), candidate);
    }
}

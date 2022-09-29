package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CandidateDBStore {
    private final BasicDataSource pool;
    private static final String INSERT = "INSERT INTO candidates(name, description, created, visible, city_id, photo) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM candidates";
    private static final String UPDATE =
            "UPDATE candidates SET name = ?, description = ?, created = ?, visible = ?, city_id = ?, photo = ? WHERE id = ?";
    private static final String SELECT_ID = "SELECT * FROM candidates WHERE id = ?";
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class.getName());

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(newCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAll()", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBoolean(4, candidate.isVisible());
            ps.setInt(5, candidate.getCity().getId());
            ps.setBytes(6, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in add()", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBoolean(4, candidate.isVisible());
            ps.setInt(5, candidate.getCity().getId());
            ps.setBytes(6, candidate.getPhoto());
            ps.setInt(7, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in update()", e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return newCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findById()", e);
        }
        return null;
    }

    private Candidate newCandidate(ResultSet rslSet) throws SQLException {
        return new Candidate(rslSet.getInt("id"),
                rslSet.getString("name"),
                rslSet.getString("description"),
                rslSet.getTimestamp("created").toLocalDateTime(),
                rslSet.getBoolean("visible"),
                new City(rslSet.getInt("city_id")),
                rslSet.getBytes("photo"));
    }
}

package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class UserDBStore {
    private final BasicDataSource pool;
    private static final String INSERT = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM users";
    private static final String UPDATE = "UPDATE users SET name = ?, password = ? WHERE email = ?";
    private static final String SELECT_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (PSQLException e) {
            if (e.getMessage().contains("ERROR: duplicate key value violates unique constraint \"users_email_key\"")) {
                return Optional.empty();
            }
        } catch (Exception e) {
            LOG.error("Exception in add()", e);
        }
        return Optional.ofNullable(user);
    }

    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in update()", e);
        }
    }

    public User findByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_EMAIL)
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return newUser(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findByEmail()", e);
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SELECT_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(newUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in findAll()", e);
        }
        return users;
    }

    private User newUser(ResultSet rslSet) throws SQLException {
        return new User(rslSet.getInt("id"),
                rslSet.getString("name"),
                rslSet.getString("email"),
                rslSet.getString("password"));
    }
}

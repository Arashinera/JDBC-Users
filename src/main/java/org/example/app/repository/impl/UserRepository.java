package org.example.app.repository.impl;

import org.example.app.database.DBConn;
import org.example.app.entity.User;
import org.example.app.repository.AppRepository;
import org.example.app.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements AppRepository<User> {

    private final static String TABLE_USERS = "users";

    @Override
    public String create(User user) {
        String sql = "INSERT INTO " + TABLE_USERS +
                " (userName, email) VALUES(?, ?)";

        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();

            return Constants.DATA_INSERT_MSG;
        } catch (SQLException exception) {
            return exception.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement stmt = DBConn.connect().createStatement()) {
            List<User> list = new ArrayList<>();

            String sql = "SELECT id, userName, email FROM "
                    + TABLE_USERS;

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(new User(
                                rs.getLong("id"),
                                rs.getString("userName"),
                                rs.getString("email")
                        )
                );
            }

            return Optional.of(list);
        } catch (SQLException exception) {
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {

        if (readById(user.getId()).isEmpty()) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            String sql = "UPDATE " + TABLE_USERS +
                    " SET userName = ?, email = ?" +
                    " WHERE id = ?";

            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getEmail());
                pst.setLong(3, user.getId());

                pst.executeUpdate();

                return Constants.DATA_UPDATE_MSG;
            } catch (SQLException exception) {
                return exception.getMessage();
            }
        }
    }

    @Override
    public String delete(Long id) {
        if (!isIdExists(id)) {
            return Constants.DATA_ABSENT_MSG;
        } else {
            String sql = "DELETE FROM " + TABLE_USERS +
                    " WHERE id = ?";

            try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
                pst.setLong(1, id);
                pst.executeUpdate();

                return Constants.DATA_DELETE_MSG;
            } catch (SQLException exception) {
                return exception.getMessage();
            }
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        String sql = "SELECT id, userName, email FROM "
                + TABLE_USERS + " WHERE id = ?";
        try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            User user = new User(
                    rs.getLong("id"),
                    rs.getString("userName"),
                    rs.getString("email")
            );

            return Optional.of(user);
        } catch (SQLException exception) {
            return Optional.empty();
        }
    }

    private boolean isIdExists(Long id) {
        String sql = "SELECT COUNT(id) FROM " + TABLE_USERS +
                " WHERE id = ?";
        try {
            PreparedStatement pst = DBConn.connect().prepareStatement(sql);
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException exception) {
            return false;
        }
        return false;
    }
}

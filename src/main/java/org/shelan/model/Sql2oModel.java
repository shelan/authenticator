package org.shelan.model;

import org.shelan.exception.AuthentcatorException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by shelan on 1/4/17.
 */
public class Sql2oModel implements Model {

    private Sql2o sql2o;

    public Sql2oModel() {
        this.sql2o = new Sql2o("jdbc:mysql://localhost:3306/auth", "root", "");
    }

    @Override
    public boolean addUser(String username, String hash) throws AuthentcatorException {
        String insertSql =
                "INSERT INTO user(username, hash) " +
                        "VALUES (:username, :hash)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("username", username)
                    .addParameter("hash", hash)
                    .executeUpdate();
            return true;
        } catch (Sql2oException e) {
            throw new AuthentcatorException("Error occuered while adding the user", e);
        }

    }

    @Override
    public boolean userExisit(String username) throws AuthentcatorException {
        String checkUserSql =
                "SELECT username FROM user WHERE username=:username";

        try (Connection con = sql2o.open()) {
            List<User> user = con.createQuery(checkUserSql)
                    .addParameter("username", username)
                    .executeAndFetch(User.class);
            return !user.isEmpty() && user.get(0).getUsername().equals(username);
        } catch (Sql2oException e) {
            throw new AuthentcatorException("Error occuered while checking the user", e);
        }
    }

    @Override
    public List<AccessLog> getlastLoginAttempts(String username, int noOfLastSuccessAttempts) throws
            AuthentcatorException {
        String checkUserSql =
                "SELECT timestamp FROM accesslog WHERE username=:username AND  ORDER BY timestamp LIMIT 5";

        try (Connection con = sql2o.open()) {
            List<AccessLog> loginAttempts = con.createQuery(checkUserSql)
                    .addParameter("username", username)
                    .executeAndFetch(AccessLog.class);

            return loginAttempts;
        } catch (Sql2oException e) {
            throw new AuthentcatorException("Error occuered while checking the user", e);
        }
    }

    @Override
    public String getPasswordHash(String username) throws AuthentcatorException {
        String checkUserSql =
                "select hash from user where username=:username";

        try (Connection con = sql2o.open()) {
            List<User> user = con.createQuery(checkUserSql)
                    .addParameter("username", username)
                    .executeAndFetch(User.class);
            if (!user.isEmpty()) {
                return user.get(0).getHash();
            } else {
                return "";
            }
        } catch (Sql2oException e) {
            throw new AuthentcatorException("Error occuered while checking the user", e);
        }
    }

    @Override
    public boolean addAccessLog(String username, boolean isSuccesful) throws AuthentcatorException {

        String insertSql =
                "INSERT INTO accesslog(username, loggingSuccess) " +
                        "VALUES (:username, :hash)";

        //converting success to 0 or 1
        int successCode = isSuccesful ? 1 : 0;

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("username", username)
                    .addParameter("loggingSuccess", successCode)
                    .executeUpdate();
            return true;
        } catch (Sql2oException e) {
            throw new AuthentcatorException("Error occuered while adding the user", e);
        }

    }
}

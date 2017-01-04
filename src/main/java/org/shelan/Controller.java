package org.shelan;

import org.shelan.exception.AuthentcatorException;
import org.shelan.model.AccessLog;
import org.shelan.model.Model;
import org.shelan.model.Sql2oModel;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by shelan on 1/4/17.
 */
public class Controller {

    Model model = new Sql2oModel();
  //  final static Logger logger = Logger.getLogger(Controller.class);

    public Status addNewUser(String username, String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            if (userExist(username)) {
                return Status.USER_EXISTS;
            }
            boolean isSuccess = model.addUser(username, hashedPassword);
            if (isSuccess) {
                return Status.SUCCESS;
            } else {
                return Status.FAILED;
            }
        } catch (AuthentcatorException e) {
            return Status.FAILED;
        }
    }

    public boolean userExist(String userName) {
        try {
            return model.userExisit(userName);
        } catch (AuthentcatorException e) {
            return false;
        }
    }

    public Status authenticate(String username, String password) {
        String passwordHash = null;
        try {
            passwordHash = model.getPasswordHash(username);
            if (passwordHash != null && BCrypt.checkpw(password, passwordHash)) {
                model.addAccessLog(username, true);
                return Status.SUCCESS;
            } else {
                model.addAccessLog(username, false);
                return Status.FAILED;
            }
        } catch (AuthentcatorException e) {
            return Status.FAILED;
        }
    }

    public List<AccessLog> getLastLoginAttempts(String user) {
        try {
            return model.getlastLoginAttempts(user, 5);
        } catch (AuthentcatorException e) {
            return new ArrayList<AccessLog>();
        }
    }
}

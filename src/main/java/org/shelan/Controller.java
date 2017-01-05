package org.shelan;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.shelan.exception.AuthentcatorException;
import org.shelan.model.AccessLog;
import org.shelan.model.Model;
import org.shelan.model.Sql2oModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static spark.Spark.halt;


/**
 * Created by shelan on 1/4/17.
 */
public class Controller {

    Model model = new Sql2oModel();
    Logger logger = LoggerFactory.getLogger(Controller.class);

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
            logger.error(e.getMessage(), e);
            return Status.FAILED;
        }
    }

    public boolean userExist(String userName) {
        try {
            return model.userExisit(userName);
        } catch (AuthentcatorException e) {
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
            return Status.FAILED;
        }
    }

    public String generateToken(String username) {
        try {
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("username", username)
                    .sign(Algorithm.HMAC256("secret"));
            return token;
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (UnsupportedEncodingException e) {
            logger.error("Unspported Encoding", e);
        }
        //TODO: need to handle this better, how to send to client
        return "";
    }

    public String getUserFromJwtToken(String authHeader) {
        //TODO : move this to controller and handle corner cases;

        try {
            String[] splitTokens = authHeader.split(" ");

            String token = splitTokens[1];

            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret"))
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("username").asString();
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
            return null;
        } catch (UnsupportedEncodingException e) {
            logger.error("Error while encoding jwt", e);
        }
        return null;
    }

    public List<AccessLog> getLastLoginAttempts(String user) {
        try {
            return model.getlastLoginAttempts(user, 5);
        } catch (AuthentcatorException e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}

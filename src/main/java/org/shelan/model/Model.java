package org.shelan.model;

import org.shelan.exception.AuthentcatorException;

import java.util.List;

/**
 * Created by shelan on 1/4/17.
 */
public interface Model {

    /**
     * Adding a new User to the system
     *
     * @param username username
     * @param hash     hashed password
     * @return true/false to indicate the success of user addition
     * @throws AuthentcatorException
     */
    public boolean addUser(String username, String hash) throws AuthentcatorException;

    /**
     * Check if the user exsist in the system
     *
     * @param username to be checked
     * @return true/false to indicate if user exsists
     * @throws AuthentcatorException
     */
    public boolean userExisit(String username) throws AuthentcatorException;

    /**
     * get the last successful login attempts
     *
     * @param username                username of the user to retrieve logins
     * @param noOfLastSuccessAttempts no of attempts to be returned
     * @return list of AccessLog objects which has timestamp values.
     * @throws AuthentcatorException
     */
    public List<AccessLog> getlastLoginAttempts(String username, int noOfLastSuccessAttempts) throws AuthentcatorException;

    /**
     * Get password hash for a user
     *
     * @param username username of the user
     * @return hashed and salted password
     * @throws AuthentcatorException
     */
    public String getPasswordHash(String username) throws AuthentcatorException;

    /**
     * Add access log for a user
     *
     * @param username
     * @param isSuccesful
     * @return
     * @throws AuthentcatorException
     */
    public boolean addAccessLog(String username, boolean isSuccesful) throws AuthentcatorException;

}

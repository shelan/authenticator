package org.shelan.model;

import org.shelan.exception.AuthentcatorException;

import java.util.List;

/**
 * Created by shelan on 1/4/17.
 */
public interface Model {

    public boolean addUser(String username, String hash) throws AuthentcatorException;

    public boolean userExisit(String username) throws AuthentcatorException;

    public List<AccessLog> getlastLoginAttempts(String username, int noOfLastSuccessAttempts) throws AuthentcatorException;

    public String getPasswordHash(String username) throws AuthentcatorException;

    public boolean addAccessLog(String username, boolean isSuccesful) throws AuthentcatorException;

}

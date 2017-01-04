package org.shelan.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by shelan on 1/4/17.
 */
public interface Model {

    public void addUser();

    public boolean userExisit();

    public List<Timestamp> getlastLoginAttempts(int noOfLastSuccessAttempts);

    public String getPasswordHash(String username);

}

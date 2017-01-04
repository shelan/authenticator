package org.shelan.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created by shelan on 1/4/17.
 */
public class AccessLog {
    private Timestamp timestamp;
    private UUID userId;
    private boolean loggingSuccess;
}

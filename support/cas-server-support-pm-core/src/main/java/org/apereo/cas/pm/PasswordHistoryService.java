package org.apereo.cas.pm;

import org.apereo.cas.pm.impl.history.PasswordHistoryEntity;

import java.util.Collection;

/**
 * This is {@link PasswordHistoryService}.
 *
 * @author Misagh Moayyed
 * @since 6.1.0
 */
public interface PasswordHistoryService {

    /**
     * Determine whether password request
     * can be accepted based on history requirements and tracking.
     *
     * @param changeRequest the change request
     * @return the boolean
     */
    boolean exists(PasswordChangeRequest changeRequest);

    /**
     * Store password request in history.
     *
     * @param changeRequest the change request
     * @return the boolean
     */
    boolean store(PasswordChangeRequest changeRequest);

    /**
     * Fetch all collection.
     *
     * @return the collection
     */
    Collection<PasswordHistoryEntity> fetchAll();

    /**
     * Fetch collection.
     *
     * @param username the username
     * @return the collection
     */
    Collection<PasswordHistoryEntity> fetch(String username);
}

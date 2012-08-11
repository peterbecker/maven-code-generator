package maven.code.generator;

/**
 * A Data Access Object to access database objects using numeric primary keys.
 *
 * @param <T> The type of object managed by this DAO.
 */
public interface Dao<T> {
    /**
     * Retrieve an object by its ID.
     *
     * @param id The ID of the object to load. Not null.
     * @return The object retrieved from the database. Null if no object matches.
     */
    T load(long id);

    /**
     * Save (persist) an object into the database.
     *
     * @param object The object to save. Not null.
     */
    void save(T object);

    /**
     * Update an object with the information from the database.
     *
     * @param object The object to update. Not null.
     */
    void update(T object);
}

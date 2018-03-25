package api;

import java.util.List;

/**
 * Data access interface (API)
 * The api package contains Data Access Objects for each entity in the model.  The DAOs separate low-level data access
 * operations from the rest of the application's code.  This separation allows for changing the underlying data store
 * without touching the main application code.
 *
 * @param <T> Model object type
 */
public interface IDao<T> {
    /* Get all of the objects */
    List<T> getAll();

    /* Create a new object */
    T create();

    /* Get an existing object */
    T get(int id);

    /* Update an existing object */
    void update(T object);

    /* Delete an existing object */
    void delete(T object);
}

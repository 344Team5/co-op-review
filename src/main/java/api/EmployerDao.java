package api;

import db.FakeDB;
import model.Employer;

import java.util.List;

/**
 * DAO for Employer objects
 */
public class EmployerDao implements IDao<Employer> {

    /**
     * Get all Employers in the system
     */
    @Override
    public List<Employer> getAll() {
        return FakeDB.getFakeDB().employerList;
    }

    /**
     * Create a new Employer
     */
    @Override
    public Employer create() {
        return null; // Not implemented for R1
    }

    /**
     * Get an existing Employer by id
     */
    @Override
    public Employer get(int id) {
        for (Employer e : FakeDB.db.employerList ) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    /**
     * Update an existing Employer
     */
    @Override
    public void update(Employer object) {
        // Not implemented for R1
    }

    /**
     * Delete an existing Employer
     */
    @Override
    public void delete(Employer object) {
        // Not implemented for R1
    }
}

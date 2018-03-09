package dao;

import db.FakeDB;
import model.Coop;

import java.util.List;

/**
 * DAO for Co-op objects
 */
public class CoopDao implements IDao<Coop> {

    /**
     * Get all Co-ops in the system
     */
    @Override
    public List<Coop> getAll() {
        return FakeDB.getFakeDB().coopList;
    }

    /**
     * Create a new Co-op
     */
    @Override
    public Coop create() {
        return null; // Not implemented for R1
    }

    /**
     * Get an existing Co-op by id
     */
    @Override
    public Coop get(int id) {
        for (Coop c : FakeDB.db.coopList ) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    /**
     * Get an existing Co-op by token (for External Reviewers)
     */
    public Coop get(String token) {
        for (Coop c : FakeDB.db.coopList ) {
            if (c.getReviewToken().equals(token)) return c;
        }
        return null;
    }

    /**
     * Update an existing Co-op
     */
    @Override
    public void update(Coop object) {
        // Not implemented for R1
    }

    /**
     * Delete a Co-op
     */
    @Override
    public void delete(Coop object) {
        // Not implemented for R1
    }
}

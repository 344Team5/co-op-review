package dao;

import db.FakeDB;
import model.Coop;

import java.util.List;

/**
 * DAO for Co-op objects
 */
public class CoopDao implements IDao<Coop> {

    /** Get all Co-ops in the system */
    @Override
    public List<Coop> getAll() {
        return FakeDB.getFakeDB().coopList;
    }

    /** Create a new Co-op */
    @Override
    public Coop create() {
        return null; // Not implemented for R1
    }

    /** Get an existing Co-op by id */
    @Override
    public Coop get(int id) {
        return FakeDB.getFakeDB().coopList.get(0);
    }

    /** Get an existing Co-op by token (for External Reviewers) */
    public Coop get(String token) {
        return FakeDB.getFakeDB().coopList.get(0);
    }

    /** Update an existing Co-op */
    @Override
    public void update(Coop object) {
        // Not implemented for R1
    }

    /** Delete a Co-op */
    @Override
    public void delete(Coop object) {
        // Not implemented for R1
    }
}

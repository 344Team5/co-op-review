package dao;

import db.FakeDB;
import model.Employer;

import java.util.List;

public class EmployerDao implements IDao<Employer> {
    @Override
    public List<Employer> getAll() {
        return FakeDB.getFakeDB().employerList;
    }

    @Override
    public Employer create() {
        return null; // Not implemented for R1
    }

    @Override
    public Employer get(int id) {
        return FakeDB.getFakeDB().employerList.get(0);
    }

    @Override
    public void update(Employer object) {
        // Not implemented for R1
    }

    @Override
    public void delete(Employer object) {
        // Not implemented for R1
    }
}

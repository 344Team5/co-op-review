package dao;

import db.FakeDB;
import model.Coop;

import java.util.ArrayList;
import java.util.List;

public class CoopDao implements IDao<Coop> {
    @Override
    public List<Coop> getAll() {
        return FakeDB.getFakeDB().coopList;
    }

    @Override
    public Coop create() {
        return null; // Not implemented for R1
    }

    @Override
    public Coop get(int id) {
        return FakeDB.getFakeDB().coopList.get(0);
    }

    @Override
    public void update(Coop object) {
        // Not implemented for R1
    }

    @Override
    public void delete(Coop object) {
        // Not implemented for R1
    }
}

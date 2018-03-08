package dao;

import db.FakeDB;
import model.Student;

import java.util.List;

public class StudentDao implements IDao<Student> {
    @Override
    public List<Student> getAll() {
        return FakeDB.getFakeDB().studentList;
    }

    @Override
    public Student create() {
        return null; // Not implemented for R1
    }

    @Override
    public Student get(int id) {
        return FakeDB.getFakeDB().studentList.get(0);
    }

    @Override
    public void update(Student object) {
        // Not implemented for R1
    }

    @Override
    public void delete(Student object) {
        // Not implemented for R1
    }
}

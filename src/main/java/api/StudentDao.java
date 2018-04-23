package api;

import db.FakeDB;
import model.Student;

import java.util.List;

/**
 * DAO for Student objects
 */
public class StudentDao implements IDao<Student> {

    /**
     * Get all Students in the system
     */
    @Override
    public List<Student> getAll() {
        return FakeDB.getFakeDB().studentList;
    }

    /**
     * Create a new Student
     */
    @Override
    public Student create() {
        return null; // Not implemented for R1
    }

    /**
     * Get an existing Student by id
     */
    @Override
    public Student get(int id) {
        for (Student s : FakeDB.db.studentList ) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    /**
     * Update an existing Student
     */
    @Override
    public void update(Student object) {
        // Not implemented for R1
    }

    /**
     * Delete an existing Student
     */
    @Override
    public void delete(Student object) {
        // Not implemented for R1
    }
}
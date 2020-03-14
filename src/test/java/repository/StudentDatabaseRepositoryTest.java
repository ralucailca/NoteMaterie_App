package repository;

import domain.Student;
import junit.framework.TestCase;
import validator.ValidationException;
import validator.ValidatorStudent;

import java.util.ArrayList;
import java.util.List;

public class StudentDatabaseRepositoryTest extends TestCase {
    private ValidatorStudent validatorStudent=new ValidatorStudent();
    private StudentDatabaseRepository studentDatabaseRepository=new StudentDatabaseRepository(validatorStudent);

    public void testSave() {
        try {
            Student s = new Student("Damian", "Flavius", "223", "flavius@email", "Alina");
            s.setId(5);
            studentDatabaseRepository.save(s);
            List<Student> studenti=new ArrayList<>();
            studentDatabaseRepository.findAll().forEach(studenti::add);
            assertEquals(studenti.size(),5);
        }catch(ValidationException e){
            assert(false);
        }
    }

    public void testDelete() {
        testSave();
        try{
            studentDatabaseRepository.delete(5);
            List<Student> studenti=new ArrayList<>();
            studentDatabaseRepository.findAll().forEach(studenti::add);
            assertEquals(studenti.size(),4);
        }catch(ValidationException e){
            assert(false);
        }
    }
}
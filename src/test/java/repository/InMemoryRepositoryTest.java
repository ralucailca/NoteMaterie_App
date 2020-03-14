package repository;

import domain.Student;
import junit.framework.TestCase;
import validator.ValidationException;
import validator.ValidatorStudent;

import java.util.ArrayList;

public class InMemoryRepositoryTest extends TestCase {
    private ValidatorStudent validatorStudent=new ValidatorStudent();
    private CrudRepository<Integer,Student> studentRepository=new InMemoryRepository<>(validatorStudent);

    public void setUp() throws Exception {
        super.setUp();
        Student s1=new Student("Popescu","Dan","224","popescudan@yahoo.com","Alina");
        Student s2=new Student("Pop","Daniel","234","popdaniel@yahoo.com","Alina");
        Student s3=new Student("Duma","Andrei","224","duma@yahoo.com","Alin");
        Student s4=new Student("Ionescu","Maria","226","maria@yahoo.com","Alin");
        Student s5=new Student("Cristescu","Ana","224","ana@yahoo.com","Alina");
        Student s6=new Student("","Ana","224","ana@yahoo.com","Alina");

        s1.setId(1);
        s2.setId(2);
        s3.setId(3);
        s4.setId(4);
        s5.setId(5);
        s6.setId(-2);

        try {
            studentRepository.save(s1);
            studentRepository.save(s2);
            studentRepository.save(s3);
            studentRepository.save(s4);
            studentRepository.save(s5);
        }catch(ValidationException e){
            assertTrue(false);
        }

        try{
            studentRepository.save(s6);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }

    public void testFindOne() {
        Student s=studentRepository.findOne(1);
        assertEquals(s.getNume(),"Popescu");
        assertEquals(s.getPrenume(),"Dan");
    }

    public void testFindAll() {
        ArrayList<Student> students= new ArrayList<>();
        studentRepository.findAll().forEach(x->students.add(x));
        assertEquals(students.size(),5);
        assertEquals(students.get(0).getId(),(Integer)1);
    }

    public void testDelete() {
        studentRepository.delete((Integer)3);
        ArrayList<Student> students = new ArrayList<>();
        for(Student s:studentRepository.findAll()) {
            students.add(s);
        }

        assertEquals(students.size(),4);
        assertEquals(students.get(2).getId(),(Integer)4);
    }

    public void testUpdate() {
        Student s=new Student("Pop1","Dana","223","popescudan@yahoo.com","Alina");
        Student st=new Student("Pop1","Dana","223","","Alina");
        st.setId(-2);
        s.setId((Integer)1);
        try {
            studentRepository.update(s);
            ArrayList<Student> students= new ArrayList<>();
            studentRepository.findAll().forEach(x->students.add(x));
            assertEquals(students.size(),5);
            assertEquals(students.get(0).getId(),(Integer)1);
            assertEquals(students.get(0).getNume(),"Pop1");
        }catch(ValidationException e){
            assertTrue(false);
        }
        try {
            studentRepository.update(st);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }
}
package repository;

import config.ApplicationContext;
import domain.Student;
import junit.framework.TestCase;
import validator.ValidationException;
import validator.ValidatorStudent;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StudentFileRepositoryTest extends TestCase {
    private ValidatorStudent validatorStudent=new ValidatorStudent();
    private StudentFileRepository studentFileRepository=new StudentFileRepository(validatorStudent,
            ApplicationContext.getPROPERTIES().getProperty("data.testFileRepo"));
    public void setUp() throws Exception {
        //golire fisier
        try{
            new PrintWriter(ApplicationContext.getPROPERTIES().getProperty("data.testFileRepo")).close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
       }
        //test save
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
            studentFileRepository.save(s1);
            studentFileRepository.save(s2);
            studentFileRepository.save(s3);
            studentFileRepository.save(s4);
            studentFileRepository.save(s5);
        }catch(ValidationException e){
            assertTrue(false);
        }

        try{
            studentFileRepository.save(s6);
        }catch(ValidationException e){
            assertTrue(true);
        }

    }

    public void testDelete() {
        studentFileRepository.delete((Integer)3);
        ArrayList<Student> students = new ArrayList<>();
        for(Student s:studentFileRepository.findAll()) {
            students.add(s);
        }

        assertEquals(students.size(),4);
        assertEquals(students.get(2).getId(),(Integer)4);

        StudentFileRepository studentFileRepository1=new StudentFileRepository(validatorStudent,
                ApplicationContext.getPROPERTIES().getProperty("data.testFileRepo"));
        ArrayList<Student> students1 = new ArrayList<>();
        studentFileRepository1.findAll().forEach(s->students1.add(s));
        assertEquals(students1.size(),4);
        assertEquals(students1.get(2).getId(),(Integer)4);
    }

    public void testUpdate() {
        Student s=new Student("Pop1","Dana","223","popescudan@yahoo.com","Alina");
        Student st=new Student("Pop1","Dana","223","","Alina");
        st.setId(-2);
        s.setId((Integer)1);
        try {
            studentFileRepository.update(s);
            ArrayList<Student> students= new ArrayList<>();
            studentFileRepository.findAll().forEach(x->students.add(x));
            assertEquals(students.size(),5);
            assertEquals(students.get(0).getId(),(Integer)1);
            assertEquals(students.get(0).getNume(),"Pop1");

            StudentFileRepository studentFileRepository1=new StudentFileRepository(validatorStudent,
                    ApplicationContext.getPROPERTIES().getProperty("data.testFileRepo"));
            ArrayList<Student> students1 = new ArrayList<>();
            studentFileRepository1.findAll().forEach(stu->students1.add(stu));
            assertEquals(students1.size(),5);
            assertEquals(students1.get(0).getId(),(Integer)1);
            assertEquals(students1.get(0).getNume(),"Pop1");

        }catch(ValidationException e){
            assertTrue(false);
        }
        try {
            studentFileRepository.update(st);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }
}
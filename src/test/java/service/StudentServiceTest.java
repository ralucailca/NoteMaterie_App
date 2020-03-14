package service;

import config.ApplicationContext;
import domain.Student;
import junit.framework.TestCase;
import repository.StudentXMLFileRepository;
import validator.ValidationException;
import validator.ValidatorStudent;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceTest extends TestCase {
    private ValidatorStudent validatorStudent=new ValidatorStudent();
    private StudentXMLFileRepository studentFileRepository=new StudentXMLFileRepository(validatorStudent,
            ApplicationContext.getPROPERTIES().getProperty("data.testStudentService"));
    private StudentService studentService=new StudentService(studentFileRepository);

    public void testSave() {
        try{
            Student s1=new Student("Pop","Mihaela","224","mihaela@email","Dan");
            s1.setId(1);
            Student s2=new Student("Popescu","Daniel","225","popescu@email","Dan");
            s2.setId(2);
            Student s3=new Student("Giurcan","Andrei","225","andrei@email","Alina");
            s3.setId(3);
            studentService.save(s1);
            studentService.save(s2);
            studentService.save(s3);
        }catch(ValidationException e){
            fail();
        }
    }

    public void testDelete() {
        testSave();
        studentService.delete(2);
        ArrayList<Student> students=new ArrayList<>();
        studentService.findAll().forEach(s->students.add(s));
        assertEquals(students.size(),2);
    }

    public void testUpdate() {
        testSave();
        Student s1=new Student("Pop","Mihaela-Andreea","227","mihaela@email","Dan");
        s1.setId(1);
        studentService.update(s1);
        ArrayList<Student> students=new ArrayList<>();
        studentService.findAll().forEach(s->students.add(s));
        assertEquals(students.size(),3);
        assertEquals(students.get(0).getPrenume(),"Mihaela-Andreea");
    }

    public void testStudentGrupa(){
        testSave();
        List<Student> students=studentService.filtruGrupa("225");
        assertEquals(students.size(),2);
        assertEquals(students.get(0).getNume(),"Popescu");
        assertEquals(students.get(1).getNume(),"Giurcan");
    }

}
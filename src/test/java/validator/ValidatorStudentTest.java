package validator;

import domain.Student;
import junit.framework.TestCase;

public class ValidatorStudentTest extends TestCase {
    public void testValidate() {
        ValidatorStudent validatorStudent=new ValidatorStudent();
        Student s=new Student("Ionescu","Maria","226","maria@yahoo.com","Alin");
        s.setId(1);
        Student st=new Student("","Maria","","maria@yahoo.com","Alin");
        try {
            validatorStudent.validate(s);
        }catch(ValidationException e){
            assertFalse(true);
            assertEquals(e,"");
        }
        try{
            validatorStudent.validate(st);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }
}
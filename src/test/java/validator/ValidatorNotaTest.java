package validator;

import domain.IdNota;
import domain.Nota;
import junit.framework.TestCase;

import java.time.LocalDateTime;

public class ValidatorNotaTest extends TestCase {

    public void testValidate() {
        LocalDateTime data=LocalDateTime.now();
        Nota n1=new Nota(data,"Alina",(double)7);
        ValidatorNota validatorNota=new ValidatorNota();
        n1.setId(new IdNota(1,1));
        try{
            validatorNota.validate(n1);
        }catch(ValidationException e){
            fail();
        }
        Nota n2=new Nota(data,"",(double)8);
        try{
            validatorNota.validate(n2);
        }catch(ValidationException e){
            assertTrue(true);
        }
        Nota n4=new Nota(data,"Alina",20.0);
        n4.setId(new IdNota(4,5));
        try{
            validatorNota.validate(n4);
        }catch(ValidationException e){
            assertTrue(true);
            assertEquals(e.getMessage(),"Valoarea notei e invalida.\n");
        }
    }
}
package validator;

import domain.Tema;
import junit.framework.TestCase;

public class ValidatorTemaTest extends TestCase {
    public void testValidate() {
        ValidatorTema validatorTema=new ValidatorTema();
        Tema t=new Tema("lab4", 4,11);
        Tema t1=new Tema("", 4,1);
        Tema t2=new Tema("", 4,15);
        Tema t3=new Tema("",4,-3);
        try{
            validatorTema.validate(t);
        }catch(ValidationException e){
            assertTrue(false);
        }
        try{
            validatorTema.validate(t1);
        }catch(ValidationException e){
            assertTrue(true);
        }
        try{
            validatorTema.validate(t2);
        }catch(ValidationException e){
            assertTrue(true);
        }
        try{
            validatorTema.validate(t3);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }
}
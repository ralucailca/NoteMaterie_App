package service;

import config.ApplicationContext;
import domain.StructuraAnUniversitar;
import domain.Tema;
import junit.framework.TestCase;
import repository.TemaXMLFileRepository;
import validator.ValidationException;
import validator.ValidatorTema;

import java.util.ArrayList;

public class TemaServiceTest extends TestCase {
    private ValidatorTema validatorTema=new ValidatorTema();
    private TemaXMLFileRepository temaFileRepository=new TemaXMLFileRepository(validatorTema,
            ApplicationContext.getPROPERTIES().getProperty("data.testTemaService"));
    private StructuraAnUniversitar structuraAnUniversitar=new StructuraAnUniversitar(2019);
    private TemaService temaServiceTest=new TemaService(temaFileRepository, structuraAnUniversitar);


    public void testSave() {

        try{
            int deadline=structuraAnUniversitar.getWeek();
            if(deadline<14)
                deadline++;
            System.out.println(deadline);
            temaServiceTest.save(1, "lab 4", deadline);
            temaServiceTest.save(2,"lab 6", deadline);
            temaServiceTest.save(3,"lab 7", deadline);
        }catch(ValidationException e){
            fail();
        }
        try{
            temaServiceTest.save(1,"",1);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }

    public void testDelete() {
        testSave();
        temaServiceTest.delete(2);
        ArrayList<Tema> teme=new ArrayList<>();
        temaServiceTest.findAll().forEach(t->teme.add(t));
        assertEquals(teme.size(),2);
    }

    public void testUpdate() {
        testSave();
        int deadline=structuraAnUniversitar.getWeek();
        if(deadline<14)
            deadline++;
        try {
            temaServiceTest.update(3, "lab7 modificat", deadline);
        }catch(ValidationException e){
            fail();
        }
        try{
            temaServiceTest.update(1,"lab4 modificat",1);
        }catch(ValidationException e){
            assertTrue(true);
        }
        ArrayList<Tema> teme=new ArrayList<>();
        temaServiceTest.findAll().forEach(t->teme.add(t));
        assertEquals(teme.size(),3);
        assertEquals(teme.get(2).getDescriere(),"lab7 modificat");
    }

}
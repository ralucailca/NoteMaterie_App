package repository;

import config.ApplicationContext;
import domain.Tema;
import junit.framework.TestCase;
import validator.ValidationException;
import validator.ValidatorTema;

import java.util.ArrayList;
import java.util.List;

public class TemaXMLFileRepositoryTest extends TestCase {
    private ValidatorTema validatorTema=new ValidatorTema();
    private TemaXMLFileRepository temaXMLFileRepository=new TemaXMLFileRepository(validatorTema,
            ApplicationContext.getPROPERTIES().getProperty("data.testTemaXML"));

    public void testSave() {
        try{
            Tema t1=new Tema("lab 1", 1, 3 );
            t1.setId(1);
            Tema t2=new Tema("lab 2", 2,4);
            t2.setId(2);
            Tema t3=new Tema("lab 3", 3,6);
            t3.setId(3);
            temaXMLFileRepository.save(t1);
            temaXMLFileRepository.save(t2);
            temaXMLFileRepository.save(t3);
        }catch(ValidationException e){
            fail();
        }

        TemaXMLFileRepository temaXMLFileRepository1=new TemaXMLFileRepository(validatorTema,
                ApplicationContext.getPROPERTIES().getProperty("data.testTemaXML"));
        List<Tema> teme=new ArrayList<>();
        temaXMLFileRepository1.findAll().forEach(x->teme.add(x));
        assertEquals(teme.size(),3);
    }

    public void testDelete() {
        testSave();
        try{
            temaXMLFileRepository.delete(2);
        }catch(ValidationException e){
            fail();
        }
        TemaXMLFileRepository temaXMLFileRepository1=new TemaXMLFileRepository(validatorTema,
                ApplicationContext.getPROPERTIES().getProperty("data.testTemaXML"));
        List<Tema> teme=new ArrayList<>();
        temaXMLFileRepository1.findAll().forEach(x->teme.add(x));
        assertEquals(teme.size(),2);
    }

    public void testUpdate() {
        try{
            Tema t1=new Tema("lab 1", 4, 10 );
            t1.setId(1);
            temaXMLFileRepository.update(t1);
        }catch(ValidationException e){
            fail();
        }
        TemaXMLFileRepository temaXMLFileRepository1=new TemaXMLFileRepository(validatorTema,
                ApplicationContext.getPROPERTIES().getProperty("data.testTemaXML"));
        List<Tema> teme=new ArrayList<>();
        temaXMLFileRepository1.findAll().forEach(x->teme.add(x));
        assertEquals(teme.size(),2);
        assertEquals(teme.get(0).getDeadlineWeek(),10);
    }
}
package repository;

import config.ApplicationContext;
import domain.IdNota;
import domain.Nota;
import junit.framework.TestCase;
import validator.ValidationException;
import validator.ValidatorNota;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotaXMLFileRepositoryTest extends TestCase {
    private ValidatorNota validatorNota=new ValidatorNota();
    private NotaXMLFileRepository notaXMLFileRepository=new NotaXMLFileRepository(validatorNota,
            ApplicationContext.getPROPERTIES().getProperty("data.testNotaXML"));

    public void testSave() {
        Nota n1=new Nota( LocalDateTime.of(2019,11,4,0,0), "Alina",10.0);
        n1.setId(new IdNota(1,1));
        Nota n2=new Nota( LocalDateTime.of(2019,11,4,0,0), "Alina",9.50);
        n2.setId(new IdNota(2,1));
        try{
            notaXMLFileRepository.save(n1);
            notaXMLFileRepository.save(n2);
        }catch(ValidationException e){
            fail();
        }
    }

    public void testDelete() {
        testSave();
        try{
            notaXMLFileRepository.delete(new IdNota(1,1));
        }catch(ValidationException e){
            fail();
        }
        NotaXMLFileRepository notaXMLFileRepository1=new NotaXMLFileRepository(validatorNota,
                ApplicationContext.getPROPERTIES().getProperty("data.testNotaXML"));
        List<Nota> note=new ArrayList<>();
        notaXMLFileRepository1.findAll().forEach(x->note.add(x));
        assertEquals(note.size(),1);
    }

    public void testUpdate() {
        testSave();
        Nota n2=new Nota( LocalDateTime.of(2019,11,8,0,0), "Dan",8.50);
        n2.setId(new IdNota(2,1));
        try{
            notaXMLFileRepository.update(n2);
        }catch(ValidationException e){
            fail();
        }
        NotaXMLFileRepository notaXMLFileRepository1=new NotaXMLFileRepository(validatorNota,
                ApplicationContext.getPROPERTIES().getProperty("data.testNotaXML"));
        List<Nota> note=new ArrayList<>();
        notaXMLFileRepository1.findAll().forEach(x->note.add(x));
        assertEquals(note.size(),2);
        assertEquals(note.get(0).getValoare(),8.50D);
    }
}
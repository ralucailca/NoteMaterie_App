package service;

import config.ApplicationContext;
import domain.IdNota;
import domain.Nota;
import domain.StructuraAnUniversitar;
import domain.Student;
import junit.framework.TestCase;
import repository.NotaXMLFileRepository;
import repository.StudentXMLFileRepository;
import repository.TemaXMLFileRepository;
import validator.ValidationException;
import validator.ValidatorNota;
import validator.ValidatorStudent;
import validator.ValidatorTema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotaServiceTest extends TestCase {
    private ValidatorStudent validatorStudent=new ValidatorStudent();
    private StudentXMLFileRepository studentFileRepository=new StudentXMLFileRepository(validatorStudent,
            ApplicationContext.getPROPERTIES().getProperty("data.testStudentService"));
    private ValidatorTema validatorTema=new ValidatorTema();
    private TemaXMLFileRepository temaFileRepository=new TemaXMLFileRepository(validatorTema,
            ApplicationContext.getPROPERTIES().getProperty("data.testNotaTemeServiceXML"));
    private StructuraAnUniversitar structuraAnUniversitar=new StructuraAnUniversitar(2019);
    private ValidatorNota validatorNota=new ValidatorNota();
    private NotaXMLFileRepository notaFileRepository=new NotaXMLFileRepository(validatorNota,
            ApplicationContext.getPROPERTIES().getProperty("data.testNotaServiceXML"));
    private NotaService notaService=new NotaService(studentFileRepository,temaFileRepository,notaFileRepository,structuraAnUniversitar);

    public void testSave() {
        Nota n1=new Nota(LocalDateTime.of(2019,11,4,0,0), "Alina",10.0);
        n1.setId(new IdNota(1,1));
        Nota n2=new Nota( LocalDateTime.of(2019,11,4,0,0), "Alina",10.0);
        n2.setId(new IdNota(14,15));
        Nota n3=new Nota( LocalDateTime.of(2019,11,11,0,0), "Alina",10.0);
        n3.setId(new IdNota(3,1));
        Nota n4=new Nota(LocalDateTime.of(2019,11,25,0,0), "Alina",6.0);
        n4.setId(new IdNota(2,1));
        notaService.save(n1);
        try{
            notaService.save(n2);
        }catch(ValidationException e){
            assertTrue(true);
        }
        try{
            notaService.save(n1);
        }catch(ValidationException e){
            assertTrue(true);
        }
        try{
            notaService.save(n3);
        }catch(ValidationException e){
            assertTrue(true);
            assertEquals(e.getMessage(),"Valoare notei e invalida!");
        }
        try{
            notaService.save(n4 );
        }catch(ValidationException e){
            assertTrue(true);
            assertEquals(e.getMessage(),"Predare mai tarziu de 2 saptamani. Tema nu mai poate fi predata.");
        }
        Nota n5=new Nota(LocalDateTime.of(2019,11,4,0,0), "Alina",9.50);
        n5.setId(new IdNota(2,1));
        Nota n6=new Nota(LocalDateTime.of(2019,11,4,0,0), "Alina",8.50);
        n6.setId(new IdNota(3,1));
        Nota n7=new Nota(LocalDateTime.of(2019,11,4,0,0), "Alina",7.50);
        n7.setId(new IdNota(1,2));
        notaService.save(n5);
        notaService.save(n6 );
        notaService.save(n7);
    }

    public void testSaveMotivated(){
        testSave();
        LocalDateTime startData=LocalDateTime.of(2019,11,11,0,0);
        LocalDateTime finalData=LocalDateTime.of(2019,11,23,0,0);
        LocalDateTime data=LocalDateTime.of(2019,12,11,0,0);
        assertEquals(notaService.calculMotivare(2, startData, finalData),2);

        assertEquals(notaService.calculNotaMaximaMotivare(2,data,startData,finalData),8);
        Nota n1=new Nota(data, "Dan",8.0);
        n1.setId(new IdNota(3,2));
        try{
            notaService.saveMotivated(n1, startData, finalData);
        }catch(ValidationException e){
            fail();
        }
        Nota n2=new Nota(LocalDateTime.of(2019,12,20,0,0), "Dan",7.0);
        n2.setId(new IdNota(2,2));
        try{
            notaService.saveMotivated(n2, startData, finalData);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"Predare mai tarziu de 2 saptamani. Tema nu mai poate fi predata.");
        }
    }

    public void testCalculIntarzaiere() {
        testSave();
        assertEquals(notaService.calculIntarzaiere(1, LocalDateTime.of(2019,11,11,0,0) ),1);
        assertEquals(notaService.calculIntarzaiere(1, LocalDateTime.of(2019,11,4,0,0) ),0);
        assertEquals(notaService.calculIntarzaiere(1, LocalDateTime.of(2019,11,18,0,0) ),2);
        assertEquals(notaService.calculIntarzaiere(1, LocalDateTime.of(2019,11,25,0,0) ),3);
        try{
            notaService.calculIntarzaiere(18, LocalDateTime.of(2019,11,25,0,0) );
        }catch(ValidationException e){
            assertTrue(true);
        }
        try{
            notaService.calculIntarzaiere(1, LocalDateTime.of(2019,10,25,0,0) );
        }catch(ValidationException e){
            assertTrue(true);
        }
    }

    public void testCalculNotaMaxima() {
        testSave();
        assertEquals(notaService.calculNotaMaxima(1, LocalDateTime.of(2019,11,11,0,0) ),9);
        assertEquals(notaService.calculNotaMaxima(1, LocalDateTime.of(2019,11,18,0,0) ),8);
        assertEquals(notaService.calculNotaMaxima(1, LocalDateTime.of(2019,11,25,0,0) ),7);
    }

    public void testFindAll() {
        testSave();
        List<Nota> note=new ArrayList<>();
        notaService.findAll().forEach(n->note.add(n));
        assertEquals(note.size(),4);
    }

    public void testFindOne() {
        testSave();
        Nota n=notaService.findOne(1,1);
        assertEquals(n.getValoare(),10,0);
    }

    public void testDelete() {
        testSave();
        try{
            notaService.delete(2, 1);
        }catch(ValidationException e){
            fail();
        }
        try{
            notaService.findOne(2, 1);
        }catch(ValidationException e){
            assertTrue(true);
        }
    }

    public void testUpdate() {
        testSave();
        Nota n1=new Nota(LocalDateTime.of(2019,11,4,0,0),"Dan",7.50);
        n1.setId(new IdNota(3,1));
        try{
            notaService.update(n1);
        }catch(ValidationException e){
            fail();
        }
        Nota n= notaService.findOne(3,1);
        assertEquals(n.getValoare(),7.50);
        assertEquals(n.getProfesor(),"Dan");
    }

    public void testStudentTema(){
        testSave();
        List<Student> students=notaService.filtruTema(1);
        assertEquals(students.size(),3);
        List<Student> students1=notaService.filtruTema(2);
        assertEquals(students1.size(),1);
        List<Student> students2=notaService.filtruTema(3);
        assertEquals(students2.size(),0);
    }

    public void testFiltruTemaProfesor(){
        List<Student> students=notaService.filtruTemaProfesor(1,"Alina");
        assertEquals(students.size(),1);
        List<Student> students3=notaService.filtruTemaProfesor(1,"Dan");
        assertEquals(students3.size(),2);
        List<Student> students1=notaService.filtruTemaProfesor(2,"Alina");
        assertEquals(students1.size(),1);
        List<Student> students2=notaService.filtruTemaProfesor(2,"Dan");
        assertEquals(students2.size(),0);
    }

    public void testFiltruNote(){
        List<Nota> n1=notaService.filtruNote(1,6);
        assertEquals(n1.size(),3);
        List<Nota> n2=notaService.filtruNote(1,7);
        assertEquals(n2.size(),0);
        List<Nota> n3=notaService.filtruNote(2,7);
        assertEquals(n3.size(),0);
    }
}
package domain;

import junit.framework.TestCase;

import java.time.LocalDateTime;

public class NotaTest extends TestCase {
    LocalDateTime datanow=LocalDateTime.now();
    private Nota nota=new Nota(LocalDateTime.now(),"Alina",(double)8);

    public void testGetData() {
        assertEquals(nota.getData(),datanow);
    }

    public void testSetData() {
        LocalDateTime d=LocalDateTime.of(2019,12,20,0,0);
        nota.setData(d);
        assertEquals(nota.getData(),d);
    }

    public void testGetProfesor() {
        assertEquals(nota.getProfesor(),"Alina");
    }

    public void testSetProfesor() {
        nota.setProfesor("Dan");
        assertEquals(nota.getProfesor(),"Dan");
    }

    public void testSetGetId() {
        nota.setId(new IdNota(1,1));
        assertEquals(nota.getId().toString(),"1 1");
        IdNota id1=new IdNota(1,5);
        IdNota id2=new IdNota(1,5);
        IdNota id3=new IdNota(1,2);
        assertEquals(id1,id2);
        assertNotSame(id1,id3);
    }

    public void testSetValoare(){
        nota.setValoare((double)7);
        assertEquals(nota.getValoare(),7.0);
    }

    public void testGetValoare(){
        assertEquals(nota.getValoare(),8.0);
    }
}
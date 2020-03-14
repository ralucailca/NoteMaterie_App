package domain;

import junit.framework.TestCase;
import validator.ValidationException;

public class TemaTest extends TestCase {
    private Tema t=new Tema("lab4",2,14);

    public void testGetId() {
        t.setId(1);
        assertEquals(t.getId(),(Integer)1);
    }

    public void testSetId() {
        t.setId(2);
        assertEquals(t.getId(),(Integer)2);
    }

    public void testGetDescriere() {
        assertEquals(t.getDescriere(),"lab4");
    }

    public void testSetDescriere() {
        t.setDescriere("laborator4");
        assertEquals(t.getDescriere(),"laborator4");
    }

    public void testGetStartWeek() {
        assertEquals(t.getStartWeek(),2);
    }

    public void testSetStertWeek() {
        t.setStartWeek(4);
        assertEquals(t.getStartWeek(),4);
    }

    public void testGetDeadlineWeek() {
        assertEquals(t.getDeadlineWeek(),14);
    }

    public void testSetDeadlineWeek() {
        t.setDeadlineWeek(13);
        assertEquals(t.getDeadlineWeek(),13);
        try{
            t.setDeadlineWeek(1);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"DeadlineWeek mai mic decat saptamana curenta!");
        }
    }

    public void testTestToString() {
        t.setId(25);
        assertEquals(t.toString(), "25 lab4 2 14");
    }

}
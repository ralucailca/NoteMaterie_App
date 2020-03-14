package domain;

import junit.framework.TestCase;

public class StudentTest extends TestCase {
    private Student s=new Student("Popescu","Dan","224","popescudan@yahoo.com","Alina");
    private Student st=new Student("Popescu","Dan","224","popescudan@yahoo.com","Alina");
    private Student st2=new Student("Popescu","Dan","224","popescudan@yahoo.com","Alina");

    public void testGetNume() {
        assertEquals(s.getNume(),"Popescu");
    }

    public void testSetNume() {
        s.setNume("Damian");
        assertEquals(s.getNume(),"Damian");
    }

    public void testGetPrenume() {
        assertEquals(s.getPrenume(),"Dan");
    }

    public void testSetPrenume() {
        s.setPrenume("Daniel");
        assertEquals(s.getPrenume(),"Daniel");
    }

    public void testGetGrupa() {
        assertEquals(s.getGrupa(),"224");
    }

    public void testSetGrupa() {
        s.setGrupa("223");
        assertEquals(s.getGrupa(),"223");
    }

    public void testGetEmail() {
        assertEquals(s.getEmail(),"popescudan@yahoo.com");
    }

    public void testSetEmail() {
        s.setEmail("damiandaniel@yahoo.com");
        assertEquals(s.getEmail(),"damiandaniel@yahoo.com");
    }

    public void testGetCadruDidacticIndrumatorLab() {
        assertEquals(s.getCadruDidacticIndrumatorLab(),"Alina");
    }

    public void testSetCadruDidacticIndrumatorLab() {
        s.setCadruDidacticIndrumatorLab("Alin");
        assertEquals(s.getCadruDidacticIndrumatorLab(),"Alin");
    }

    public void testEquals() {
        assertNotSame(s,st);
    }

    public void testToString() {
        st.setId(10);
        assertEquals(st.toString(),"10 Popescu Dan 224 popescudan@yahoo.com Alina");
    }

    public void testSetId() {
        s.setId(2);
        assertEquals(s.getId(),(Integer) 2);
    }

    public void testGetId() {
        s.setId(2);
        assertEquals(s.getId(),(Integer) 2);
    }
}
package domain;

import config.ApplicationContext;
import junit.framework.TestCase;
import validator.ValidationException;

import java.time.LocalDateTime;

public class StructuraSemestruTest extends TestCase {

    public void testGetDataWeek() {
        LocalDateTime d1=LocalDateTime.of(2019,8,20,0,0);
        LocalDateTime d2=LocalDateTime.of(2019,12,25,0,0);
        LocalDateTime d3=LocalDateTime.of(2020,3,20,0,0);
        LocalDateTime d4=LocalDateTime.of(2020,1,8,0,0);

        StructuraSemestru sem1=new StructuraSemestru(2019,1, ApplicationContext.getPROPERTIES().getProperty("data.semestru1"));
        StructuraSemestru sem2=new StructuraSemestru(2020,2, ApplicationContext.getPROPERTIES().getProperty("data.semestru2"));

        try{
            sem1.getDataWeek(d1);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"Nu va aflati in timpul semestrului.");
        }
        try{
            sem1.getDataWeek(d2);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"Nu va aflati in timpul semestrului.");
        }
        try{
            sem1.getDataWeek(d3);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"Nu va aflati in timpul semestrului.");
        }
        assertEquals(sem1.getDataWeek(d4),13);

        LocalDateTime d5=LocalDateTime.of(2020,2,25,0,0);
        LocalDateTime d6=LocalDateTime.of(2020,4,28,0,0);
        LocalDateTime d7=LocalDateTime.of(2020,6,5,0,0);
        assertEquals(sem2.getDataWeek(d5),1);
        assertEquals(sem2.getDataWeek(d6),9);
        assertEquals(sem2.getDataWeek(d7),14);

    }
}
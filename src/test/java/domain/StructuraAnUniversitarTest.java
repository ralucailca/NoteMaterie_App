package domain;

import junit.framework.TestCase;
import validator.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StructuraAnUniversitarTest extends TestCase {
    private StructuraAnUniversitar structuraAnUniversitar=new StructuraAnUniversitar(2019);

    public void testGetAnUniversitar() {
        assertEquals(structuraAnUniversitar.getAnUniversitar(),2019);
    }

    public void testSetAnUniversitar() {
        structuraAnUniversitar.setAnUniversitar(2020);
        assertEquals(structuraAnUniversitar.getAnUniversitar(),2020);
    }

    public void testGetSem1() {
        StructuraSemestru sem1=structuraAnUniversitar.getSem1();
        assertEquals(sem1.getAnUniversitar(),2019);
        assertEquals(sem1.getSemestru(),1);
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        assertEquals(sem1.getStartSemester().format(format),"2019-09-30");
        assertEquals(sem1.getBeginHoliday().format(format),"2019-12-23");
    }

    public void testGetSem2() {
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StructuraSemestru sem2=structuraAnUniversitar.getSem2();
        assertEquals(sem2.getSemestru(),2);
        assertEquals(sem2.getStartSemester().format(format),"2020-02-24");
    }

    public void testGetDataWeek() {
        LocalDateTime d1=LocalDateTime.of(2019,8,20,0,0);
        LocalDateTime d2=LocalDateTime.of(2019,12,25,0,0);
        LocalDateTime d4=LocalDateTime.of(2020,1,8,0,0);

        try{
            structuraAnUniversitar.getDataWeek(d1);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"Nu va aflati in timpul semestrului.");
        }
        try{
            structuraAnUniversitar.getDataWeek(d2);
        }catch(ValidationException e){
            assertEquals(e.getMessage(),"Nu va aflati in timpul semestrului.");
        }
        assertEquals(structuraAnUniversitar.getDataWeek(d4),13);

        LocalDateTime d5=LocalDateTime.of(2020,2,25,0,0);
        LocalDateTime d6=LocalDateTime.of(2020,4,28,0,0);
        LocalDateTime d7=LocalDateTime.of(2020,6,5,0,0);
        assertEquals(structuraAnUniversitar.getDataWeek(d5),1);
        assertEquals(structuraAnUniversitar.getDataWeek(d6),9);
        assertEquals(structuraAnUniversitar.getDataWeek(d7),14);
    }
}
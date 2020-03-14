package domain;

import validator.ValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class StructuraSemestru {
    int anUniversitar;
    int semestru;
    private String datafile;
    private LocalDateTime startSemester;
    private LocalDateTime beginHoliday;
    private LocalDateTime endHoliday;
    private LocalDateTime endSemester;


    public StructuraSemestru(int anUniversitar, int semestru, String datafile) {
        this.anUniversitar = anUniversitar;
        this.semestru = semestru;
        this.datafile = datafile;
        extractData();
    }

    public int getAnUniversitar() {
        return anUniversitar;
    }

    public int getSemestru() {
        return semestru;
    }

    public LocalDateTime getStartSemester() {
        return startSemester;
    }

    public LocalDateTime getBeginHoliday() {
        return beginHoliday;
    }

    public LocalDateTime getEndHoliday() {
        return endHoliday;
    }

    public LocalDateTime getEndSemester() {
        return endSemester;
    }

    private void extractData(){
        Path path= Paths.get(datafile);
        try{
            List<String> lines= Files.readAllLines(path);
            //DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
            //startSemester= LocalDateTime.parse(lines.get(0));

            String[] d1=(lines.get(0)).split("-");
            startSemester=LocalDateTime.of(Integer.parseInt(d1[0]),Integer.parseInt(d1[1]),Integer.parseInt(d1[2]),0,0);
            String[] d2=(lines.get(1)).split("-");
            beginHoliday=LocalDateTime.of(Integer.parseInt(d2[0]),Integer.parseInt(d2[1]),Integer.parseInt(d2[2]),0,0);
            String[] d3=(lines.get(2)).split("-");
            endHoliday=LocalDateTime.of(Integer.parseInt(d3[0]),Integer.parseInt(d3[1]),Integer.parseInt(d3[2]),0,0);
            String[] d4=(lines.get(3)).split("-");
            endSemester=LocalDateTime.of(Integer.parseInt(d4[0]),Integer.parseInt(d4[1]),Integer.parseInt(d4[2]),0,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getWeek(){
        LocalDateTime dataCurenta=LocalDateTime.now();
        return getDataWeek(dataCurenta);
    }

    public int getDataWeek(LocalDateTime dataCurenta){
        //LocalDateTime dataCurenta=LocalDateTime.now();
        //verificam ca data curenta sa fie in semestru
        if(dataCurenta.compareTo(startSemester)<0 ||
                (dataCurenta.compareTo(beginHoliday)>=0 && dataCurenta.compareTo(endHoliday)<=0) ||
                dataCurenta.compareTo(endSemester)>0)
            throw new ValidationException("Nu va aflati in timpul semestrului.");
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        //verificam daca suntem dupa vacanta
        if(dataCurenta.compareTo(endHoliday)>0){
            int week=dataCurenta.get(weekFields.weekOfWeekBasedYear())-endHoliday.get(weekFields.weekOfWeekBasedYear());
            //plusam cu numarul de saptamani anteriaore rezultatul
            week=week+(beginHoliday.get(weekFields.weekOfWeekBasedYear())-startSemester.get(weekFields.weekOfWeekBasedYear()));
            return week;
        }
        //suntem inainte de vacanta
        return (dataCurenta.get(weekFields.weekOfWeekBasedYear())-startSemester.get(weekFields.weekOfWeekBasedYear())+1);
    }

}

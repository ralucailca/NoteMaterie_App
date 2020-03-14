package domain;

import config.ApplicationContext;
import validator.ValidationException;

import java.time.LocalDateTime;

public class StructuraAnUniversitar extends Entity<Integer> {
    private int anUniversitar;
    private StructuraSemestru sem1;
    private StructuraSemestru sem2;

    public StructuraAnUniversitar(int anUniversitar) {
        this.anUniversitar = anUniversitar;
        sem1=new StructuraSemestru(anUniversitar,1, ApplicationContext.getPROPERTIES().getProperty("data.semestru1"));
        sem2=new StructuraSemestru(anUniversitar,2, ApplicationContext.getPROPERTIES().getProperty("data.semestru2"));
    }

    //seteaza anul curent
    //are ca parametru un intreg
    public void setAnUniversitar(int anUniversitar) {
        this.anUniversitar = anUniversitar;
    }

    //returneaza anul universitar - intreg
    public int getAnUniversitar() {
        return anUniversitar;
    }

    //returneaza semestrul 1 de tip structura semestru
    public StructuraSemestru getSem1() {
        return sem1;
    }

    //returneaza semestrul 2 de tip structura semestru
    public StructuraSemestru getSem2() {
        return sem2;
    }

    //Returneaza saptamana curenta din anul universitar: intreg
    //Arunca exceptie daca suntem in vacante/ in afara anului universitar de studiu ex:in vacanta de vara
    public int getWeek(){
        try {
            return sem1.getWeek();
            //daca data curenta nu se gaseste in semestrul 1 atunci se poate sa fim in semestrul 2
        }catch(ValidationException e){
            return sem2.getWeek();
            //daca nu suntem nici in semestrul 2 cu data curenta atunci cu siguranta suntem in vacanta/afara semestrului
        }
    }

    public int getDataWeek(LocalDateTime data){
        try {
            return sem1.getDataWeek(data);
            //daca data curenta nu se gaseste in semestrul 1 atunci se poate sa fim in semestrul 2
        }catch(ValidationException e){
            return sem2.getDataWeek(data);
            //daca nu suntem nici in semestrul 2 cu data curenta atunci cu siguranta suntem in vacanta/afara semestrului
        }
    }
}

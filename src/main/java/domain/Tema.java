package domain;

import validator.ValidationException;

import java.util.Objects;

public class Tema extends Entity<Integer>{
    private String descriere;
    private int startWeek;
    private int deadlineWeek;

    //constructor- parametrii: string:descirere si int:deadlineweek
    //calculeaza startweek cu aju
    public Tema(String descriere, int startWeek, int deadlineWeek) {
        this.descriere = descriere;
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
    }

    //returneaza descrierea temei-string
    public String getDescriere() {
        return descriere;
    }

    //seteaza descrierea temei- parametru string
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    //returneaza startweek: int
    public int getStartWeek() {
        return startWeek;
    }

    //seteaza startWeek:
    public void setStartWeek(int startWeek){
        this.startWeek=startWeek;
    }

    //returneaza deadlineweek -int
    public int getDeadlineWeek() {
        return deadlineWeek;
    }

    //seteaza dealineweek- parametru int
    public void setDeadlineWeek(int deadlineWeek) {
        if(deadlineWeek<startWeek){
            throw new ValidationException("DeadlineWeek mai mic decat saptamana curenta!");
        }
        this.deadlineWeek = deadlineWeek;
    }

    //suprascrie equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tema tema = (Tema) o;
        return startWeek == tema.startWeek &&
                deadlineWeek == tema.deadlineWeek &&
                Objects.equals(descriere, tema.descriere);
    }

    //suprascrie hashcode
    @Override
    public int hashCode() {
        return Objects.hash(descriere, startWeek, deadlineWeek);
    }

    //suprascrie tostring; returneaza stringul rezultat
    @Override
    public String toString() {
        return  getId().toString()+' '+descriere + ' ' + startWeek + ' ' + deadlineWeek;
    }

    //returneaza id-ul
    @Override
    public Integer getId() {
        return super.getId();
    }

    //seteaza id-ul
    @Override
    public void setId(Integer integer) {
        super.setId(integer);
    }
}

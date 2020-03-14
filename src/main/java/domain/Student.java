package domain;

import java.util.Objects;

public class Student extends Entity<Integer> {
    private String nume;
    private String prenume;
    private String grupa;
    private String email;
    private String cadruDidacticIndrumatorLab;

    //constructor - primeste drept parametrii 5 string-uri
    public Student(String nume, String prenume, String grupa, String email, String cadruDidacticIndrumatorLab) {
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    //returneaza un string
    public String getNume() {
        return nume;
    }

    //seteaza numerele studentului- primeste drept parametru un string
    public void setNume(String nume) {
        this.nume = nume;
    }

    //returneaza prenumele -string
    public String getPrenume() {
        return prenume;
    }

    //seteaza prenumele -primeste drept parametru un string
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    //returneaza grupa studentului-string
    public String getGrupa() {
        return grupa;
    }

    //seteaza grupa- primeste drept parametru un string
    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    //returneaza email-ul -string
    public String getEmail() {
        return email;
    }

    //seteaza emailul -primeste parametru un string
    public void setEmail(String email) {
        this.email = email;
    }

    //returneaza cardul didactic indrumator-string
    public String getCadruDidacticIndrumatorLab() {
        return cadruDidacticIndrumatorLab;
    }

    //seteaza cadrul didactic indrumator-string
    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        this.cadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    //suprescrie metoda equals, returneaza true sau false daca cele doua obiecte sunt egale sau nu
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(nume, student.nume) &&
                Objects.equals(prenume, student.prenume) &&
                Objects.equals(grupa, student.grupa) &&
                Objects.equals(email, student.email) &&
                Objects.equals(cadruDidacticIndrumatorLab, student.cadruDidacticIndrumatorLab);
    }

    //suprascrie hashCode
    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, grupa, email, cadruDidacticIndrumatorLab);
    }

    //suprascrie toString, returneaza stringul rezultat
    @Override
    public String toString() {
        return getId().toString()+' '+nume + ' ' + prenume + ' ' + grupa + ' ' + email + ' ' + cadruDidacticIndrumatorLab;

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

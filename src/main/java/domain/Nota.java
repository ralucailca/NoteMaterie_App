package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

//id-ul este un string compus din 2 id-uri, adica doua numere intregi de forma "Intreg Intreg"
//primul intreg este id-ul studentului, al doilea intreg este id-ul temei
public class Nota extends Entity<IdNota> {
    private LocalDateTime data;
    private String profesor;
    private Double valoare;

    public Nota(LocalDateTime data, String profesor, Double valoare) {
        this.data = data;
        this.profesor = profesor;
        this.valoare=valoare;
    }

    public Double getValoare() {
        return valoare;
    }

    public void setValoare(Double valoare) {
        this.valoare = valoare;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return   data.format(format) + " " + profesor+" "+valoare.toString();
    }

    @Override
    public IdNota getId() {
        return super.getId();
    }

    @Override
    public void setId(IdNota idNota) {
        super.setId(idNota);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota = (Nota) o;
        return Double.compare(nota.valoare, valoare) == 0 &&
                Objects.equals(data, nota.data) &&
                Objects.equals(profesor, nota.profesor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, profesor, valoare);
    }
}

package domain;

import java.util.Objects;

public class IdNota implements Comparable<IdNota> {
    private Integer IdStudent;
    private Integer IdTema;

    public IdNota(Integer idStudent, Integer idTema) {
        IdStudent = idStudent;
        IdTema = idTema;
    }

    public Integer getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(Integer idStudent) {
        IdStudent = idStudent;
    }

    public Integer getIdTema() {
        return IdTema;
    }

    public void setIdTema(Integer idTema) {
        IdTema = idTema;
    }

    @Override
    public String toString() {
        return IdStudent + " " + IdTema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdNota idNota = (IdNota) o;
        return Objects.equals(IdStudent, idNota.IdStudent) &&
                Objects.equals(IdTema, idNota.IdTema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdStudent, IdTema);
    }

    @Override
    public int compareTo(IdNota o) {
        if(this.getIdStudent()!=o.getIdStudent())
            return this.getIdStudent()-o.getIdStudent();
        return this.getIdTema()-o.getIdTema();
    }
}

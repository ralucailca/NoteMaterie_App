package domain;

import java.util.Objects;

public class NotaDTO {
    private String nume;
    private String prenume;
    private Integer temaID;
    private Double nota;
    private String profesor;
    private String grupa;
    private Integer studentID;

    public NotaDTO(Integer studentID,String nume, String prenume, Integer temaID, Double nota, String profesor, String grupa) {
        this.nume = nume;
        this.prenume = prenume;
        this.temaID = temaID;
        this.nota = nota;
        this.profesor = profesor;
        this.grupa = grupa;
        this.studentID=studentID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Integer getTemaID() {
        return temaID;
    }

    public void setTemaID(Integer temaID) {
        this.temaID = temaID;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaDTO notaDTO = (NotaDTO) o;
        return Objects.equals(nume, notaDTO.nume) &&
                Objects.equals(prenume, notaDTO.prenume) &&
                Objects.equals(temaID, notaDTO.temaID) &&
                Objects.equals(nota, notaDTO.nota) &&
                Objects.equals(profesor, notaDTO.profesor) &&
                Objects.equals(grupa, notaDTO.grupa) &&
                Objects.equals(studentID, notaDTO.studentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, temaID, nota, profesor, grupa, studentID);
    }

    @Override
    public String toString() {
        return "NotaDTO{" +
                "nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", temaID=" + temaID +
                ", nota=" + nota +
                ", profesor='" + profesor + '\'' +
                ", grupa='" + grupa + '\'' +
                ", studentID=" + studentID +
                '}';
    }
}

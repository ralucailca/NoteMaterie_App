package repository;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validator.Validator;

public class StudentXMLFileRepository extends XMLFileRepository<Integer, Student> {

    public StudentXMLFileRepository(Validator<Student> validator, String filename) {
        super(validator, filename);
    }

    @Override
    protected Element createElementFromEntity(Document document, Student student) {
        Element element=document.createElement("student");
        element.setAttribute("id",student.getId().toString());
        Element nume=document.createElement("nume");
        nume.setTextContent(student.getNume());
        element.appendChild(nume);
        Element prenume=document.createElement("prenume");
        prenume.setTextContent(student.getPrenume());
        element.appendChild(prenume);
        Element grupa=document.createElement("grupa");
        grupa.setTextContent(student.getGrupa());
        element.appendChild(grupa);
        Element email=document.createElement("email");
        email.setTextContent(student.getEmail());
        element.appendChild(email);
        Element cadruDidacticIndrumatorLab=document.createElement("cadruDidacticIndrumatorLab");
        cadruDidacticIndrumatorLab.setTextContent(student.getCadruDidacticIndrumatorLab());
        element.appendChild(cadruDidacticIndrumatorLab);
        return element;
    }

    @Override
    protected Student createEntityFromElement(Element elem) {
        String id=elem.getAttribute("id");
        String nume=elem.getElementsByTagName("nume").item(0).getTextContent();
        String prenume=elem.getElementsByTagName("prenume").item(0).getTextContent();
        String grupa=elem.getElementsByTagName("grupa").item(0).getTextContent();
        String email=elem.getElementsByTagName("email").item(0).getTextContent();
        String prof=elem.getElementsByTagName("cadruDidacticIndrumatorLab").item(0).getTextContent();
        Student s=new Student(nume,prenume,grupa,email,prof);
        s.setId(Integer.parseInt(id));
        return s;
    }
}

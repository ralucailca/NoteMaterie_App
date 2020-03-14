package repository;

import domain.IdNota;
import domain.Nota;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validator.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotaXMLFileRepository extends XMLFileRepository<IdNota, Nota> {

    public NotaXMLFileRepository(Validator<Nota> validator, String filename) {
        super(validator, filename);
    }

    @Override
    protected Element createElementFromEntity(Document document, Nota nota) {
        Element element=document.createElement("Nota");
        element.setAttribute("id", nota.getId().toString());
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Element data=document.createElement("data");
        data.setTextContent(nota.getData().format(format));
        element.appendChild(data);
        Element profesor=document.createElement("profesor");
        profesor.setTextContent(nota.getProfesor());
        element.appendChild(profesor);
        Element valoare=document.createElement("valoare");
        valoare.setTextContent(nota.getValoare().toString());
        element.appendChild(valoare);
        return element;
    }

    @Override
    protected Nota createEntityFromElement(Element elem) {
        String id=elem.getAttribute("id");
        String data=elem.getElementsByTagName("data").item(0).getTextContent();
        String profesor=elem.getElementsByTagName("profesor").item(0).getTextContent();
        String valoare=elem.getElementsByTagName("valoare").item(0).getTextContent();
        String[] cmp=data.split("-");
        LocalDateTime date=LocalDateTime.of(Integer.parseInt(cmp[0]),Integer.parseInt(cmp[1]),Integer.parseInt(cmp[2]),0,0);
        Nota n=new Nota(date, profesor, Double.parseDouble(valoare));
        String[] ids=id.split(" ");
        IdNota idnota=new IdNota(Integer.parseInt(ids[0]),Integer.parseInt(ids[1]));
        n.setId(idnota);
        return n;
    }
}

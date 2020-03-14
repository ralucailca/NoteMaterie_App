package repository;

import domain.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validator.Validator;

public class TemaXMLFileRepository extends XMLFileRepository<Integer, Tema> {

    public TemaXMLFileRepository(Validator<Tema> validator, String filename) {
        super(validator, filename);
    }

    @Override
    protected Element createElementFromEntity(Document document, Tema tema) {
        Element element=document.createElement("Tema");
        element.setAttribute("id",tema.getId().toString());
        Element descriere=document.createElement("descriere");
        descriere.setTextContent(tema.getDescriere());
        element.appendChild(descriere);
        Element startweek=document.createElement("startWeek");
        startweek.setTextContent(String.valueOf(tema.getStartWeek()));
        element.appendChild(startweek);
        Element deadline=document.createElement("deadlineWeek");
        deadline.setTextContent(String.valueOf(tema.getDeadlineWeek()));
        element.appendChild(deadline);
        return element;
    }

    @Override
    protected Tema createEntityFromElement(Element elem) {
        String id=elem.getAttribute("id");
        String descriere=elem.getElementsByTagName("descriere").item(0).getTextContent();
        String startWeek=elem.getElementsByTagName("startWeek").item(0).getTextContent();
        String deadline=elem.getElementsByTagName("deadlineWeek").item(0).getTextContent();
        Tema t=new Tema(descriere,Integer.parseInt(startWeek),Integer.parseInt(deadline));
        t.setId(Integer.parseInt(id));
        return t;
    }
}

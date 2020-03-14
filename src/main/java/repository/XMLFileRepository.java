package repository;

import domain.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import validator.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public abstract class XMLFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    private String filename;

    public XMLFileRepository(Validator<E> validator,String filename) {
        super(validator);
        this.filename=filename;
        loadData();
    }

    private void loadData(){
        try{
            Document document= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
            Element root=document.getDocumentElement();
            NodeList children=root.getChildNodes();

            for(int i=0; i<children.getLength(); ++i){
                Node elem=children.item(i);
                if(elem instanceof Element){
                    E entity=createEntityFromElement((Element)elem);
                    super.save(entity);
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData(){
        try{
            Document document=DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root=document.createElement("entity");
            document.appendChild(root);
            super.findAll().forEach(e->{
                Element elem=createElementFromEntity(document, e);
                root.appendChild(elem);
            });
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            Source source=new DOMSource(document);
            transformer.transform(source, new StreamResult(filename));
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    protected abstract Element createElementFromEntity(Document document, E e);

    protected abstract E createEntityFromElement(Element elem);

    @Override
    public E save(E entity) {
        E e= super.save(entity);
        if(e==null) {
            writeData();
        }
        return e;
    }

    @Override
    public E delete(ID id) {
        E e=super.delete(id);
        if(e!=null) {
            writeData();
        }
        return e;
    }

    @Override
    public E update(E entity) {
        E e=super.update(entity);
        if(e==null) {
            writeData();
        }
        return e;
    }
}

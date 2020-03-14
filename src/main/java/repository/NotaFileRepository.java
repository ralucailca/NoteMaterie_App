package repository;

import domain.IdNota;
import domain.Nota;
import validator.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotaFileRepository extends FileRepository<IdNota, Nota> {

    public NotaFileRepository(Validator<Nota> validator, String filename) {
        super(validator, filename);
    }

    @Override
    protected Nota stringToEntity(String line) {
        String[] cmp=line.split(";");
        String[] data=cmp[1].split("-");
        Nota nota=new Nota(LocalDateTime.of(Integer.parseInt(data[0]),Integer.parseInt(data[1]),
                Integer.parseInt(data[2]),0,0),cmp[2],Double.parseDouble(cmp[3]));
        String[] ids=cmp[0].split(" ");
        IdNota idnota=new IdNota(Integer.parseInt(ids[0]),Integer.parseInt(ids[1]));
        nota.setId(idnota);
        return nota;
    }

    @Override
    protected String entityToString(Nota entity) {
        String n="";
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        n+=entity.getId().toString()+";";
        n+=entity.getData().format(format)+";";
        n+=entity.getProfesor()+";";
        n+=entity.getValoare().toString();
        return n;
    }
}

package repository;

import domain.Tema;
import validator.Validator;

public class TemaFileRepository extends FileRepository<Integer, Tema> {
    public TemaFileRepository(Validator<Tema> validator, String filename) {
        super(validator, filename);
    }

    @Override
    protected Tema stringToEntity(String line) {
        String[] cmp=line.split(";");
        Tema tema=new Tema(cmp[1],Integer.parseInt(cmp[2]),Integer.parseInt(cmp[3]));
        tema.setId(Integer.parseInt(cmp[0]));
        return tema;
    }

    @Override
    protected String entityToString(Tema entity) {
        String t="";
        t+=entity.getId().toString()+";";
        t+=entity.getDescriere()+";";
        t+=entity.getStartWeek()+";";
        t+=entity.getDeadlineWeek()+";";
        return t;
    }
}

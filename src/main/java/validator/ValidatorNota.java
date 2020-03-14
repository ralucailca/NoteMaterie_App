package validator;

import domain.Nota;

public class ValidatorNota implements Validator<Nota> {

    @Override
    public void validate(Nota entity) throws ValidationException {
        String err="";
        if(entity==null)
            err+="Entitate invalida\n";
        if(entity.getProfesor()=="" || entity.getProfesor()==null)
            err+="Denumirea profesorului este invalida\n";
        if(entity.getData()==null)
            err+="Data invalida\n";
        if(entity.getId()==null)
            err+="ID invalid";
//        if(entity.getId()!=null)
//            if(!entity.getId().matches("^([0-9]+) ([0-9]+)$"))
//                err+="Format id (Integer Integer) invalid\n";
        if(entity.getValoare()<1 || entity.getValoare()>10)
            err+="Valoarea notei e invalida.\n";
        if(err.length()>0)
            throw new ValidationException(err);
    }
}

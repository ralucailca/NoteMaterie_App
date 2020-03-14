package validator;

import domain.Student;

public class ValidatorStudent implements Validator<Student> {

    /*
    Valideaza un obiect de tip student
    Daca un camp al studentului este null se arunca exceptie
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        String err="";
        if(entity==null)
            err+="Student null\n";
        if (entity.getId()==null)
            err+="Id null\n";
        else
            if(entity.getId()<0)
                err+="Id invalid!\n";
        if(entity.getNume().length()==0)
            err+="Nume invalid!\n";
        if(entity.getPrenume().length()==0)
            err+="Prenume invalid!\n";
        if(entity.getGrupa().length()==0)
            err+="Grupa invalida!\n";
        if(entity.getEmail().length()==0)
            err+="Email invalid!\n";
        if(entity.getCadruDidacticIndrumatorLab().length()==0)
            err+="Cadru Didactic Indrumator Lab invalid!\n";
        if(err.length()>0)
            throw new ValidationException(err);
    }
}

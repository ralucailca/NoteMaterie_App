package validator;

import domain.Tema;

public class ValidatorTema implements Validator<Tema> {

    /*
    Valideaza un obiect de tip tema
    Daca sartWeek si dealineWeek nu sunt cuprinse in intervalul 1-14 se arunca execeptie
    Daca startweek este mai mare decat deadline week se arunca exceptie
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        String err="";
        if(entity.getStartWeek()<1 || entity.getStartWeek()>14)
            err+="StartWeek invalid!\n";
        if(entity.getDeadlineWeek()<1 || entity.getDeadlineWeek()>14)
            err+="DeadlineWeek invalid!\n";
        if(entity.getStartWeek()>entity.getDeadlineWeek())
            err+="DeadlineWeek mai mic decat StartWeek!\n";
        if(err.length()>0)
            throw new ValidationException(err);
    }
}
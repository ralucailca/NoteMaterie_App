package validator;

public interface Validator<E> {
    /*
    valideaza un obiect de tip entity
    arunca o eroare de tipul validationException
     */
    void validate(E entity) throws ValidationException;
}
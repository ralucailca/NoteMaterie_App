package domain;

public class Entity<ID> {
    private ID id;
    /*
    returneaza un obiect/element de tip id
     */
    public ID getId(){
        return id;
    }
    /*
    seteaza un obiect/element de tip id cu o valoare
     */
    public void setId(ID id) {
        this.id=id;
    }
}

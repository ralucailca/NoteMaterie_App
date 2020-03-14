package repository;

import domain.Entity;
import validator.ValidationException;
import validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements CrudRepository<ID,E> {
    Map<ID, E> entities;
    Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        this.entities=new HashMap<>();
    }

    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * * @throws IllegalArgumentException
     * if id is null.
     */
    @Override
    public E findOne(ID id) {
        if(id==null) throw new IllegalArgumentException("Entity must be not null!");
        return entities.get(id);
    }

    /**
     *
     * @return all entities
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     *
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * * @throws ValidationException
     * if the entity is not valid
     * * @throws IllegalArgumentException
     *  if the given entity is null. *
     **/
    @Override
    public E save(E entity) throws ValidationException {
        if(entity==null) throw new IllegalArgumentException("Entity must be not null!");
        E oldValue=entities.get(entity.getId());
        if(oldValue==null) {
            validator.validate(entity);
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;
    }

    /**
     *  * removes the entity with the specified id
     *  * @param id
     *  * id must be not null
     *  * @return the removed entity or null if there is no entity with the
     * given id
     *  * @throws IllegalArgumentException
     *  * if the given id is null.
     *  */
    @Override
    public E delete(ID id) {
        if(id==null) throw new IllegalArgumentException("Entity must be not null!");
        return entities.remove(id);
    }

    /**
     *  *
     *  * @param entity
     *  * entity must not be null
     *  * @return null - if the entity is updated,
     *  * otherwise returns the entity - (e.g id does not
     * exist).
     *  * @throws IllegalArgumentException
     *  * if the given entity is null.
     *  * @throws ValidationException
     *  * if the entity is not valid.
     *  */
    @Override
    public E update(E entity) {
        if(entity==null) throw new IllegalArgumentException("Entity must be not null!");
        E e=entities.get(entity.getId());
        if(e==null)
            return entity;
        validator.validate(entity);
        entities.replace(entity.getId(),entity);
        return null;
    }
}

package repository;

import domain.Entity;
import validator.ValidationException;
import validator.Validator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class FileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private String filename;
    public FileRepository(Validator<E> validator, String filename) {
        super(validator);
        this.filename=filename;
        loadData();
    }

    protected abstract E stringToEntity(String line);

    private void loadData(){
        Path path= Paths.get(filename);
        List<String> lines;
        try{
            lines= Files.readAllLines(path);
            lines.forEach(l->{
                E entity=stringToEntity(l);
                super.save(entity);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeData(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(filename))) {
            super.findAll().forEach(entity->{
                String s=entityToString(entity);
                try {
                    bw.write(s);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract String entityToString(E entity);

    @Override
    public E save(E entity) throws ValidationException {
        E e= super.save(entity);
        writeData();
        return e;
    }

    @Override
    public E delete(ID id) {
        E e=super.delete(id);
        writeData();
        return e;
    }

    @Override
    public E update(E entity) {
        E e=super.update(entity);
        writeData();
        return e;
    }
}

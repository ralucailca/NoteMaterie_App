package repository;

import domain.Student;
import validator.Validator;

public class StudentFileRepository extends FileRepository<Integer, Student> {

    public StudentFileRepository(Validator<Student> validator, String filename) {
        super(validator, filename);
    }

    @Override
    protected Student stringToEntity(String line) {
        String[] cmp=line.split(";");
        Student student=new Student(cmp[1],cmp[2],cmp[3],cmp[4],cmp[5]);
        student.setId(Integer.parseInt(cmp[0]));
        return student;
    }

    @Override
    protected String entityToString(Student entity) {
        String s="";
        s+=entity.getId().toString()+";";
        s+=entity.getNume()+";";
        s+=entity.getPrenume()+";";
        s+=entity.getGrupa()+";";
        s+=entity.getEmail()+";";
        s+=entity.getCadruDidacticIndrumatorLab();
        return s;
    }
}

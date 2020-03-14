package service;

import domain.NotaDTO;
import domain.Student;
import observer.Observable;
import observer.Observer;
import repository.StudentXMLFileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService implements Observable {
    private StudentXMLFileRepository studentFileRepository;
    private List<Observer> observers=new ArrayList<>();

    public StudentService(StudentXMLFileRepository studentFileRepository) {
        this.studentFileRepository = studentFileRepository;
    }

    public Student findOne(Integer i){
        return studentFileRepository.findOne(i);
    }

    public Iterable<Student> findAll(){
        return studentFileRepository.findAll();
    }

    public Student save(Student student){
        Student s=studentFileRepository.save(student);
        if(s==null)
            notifyObservers();
        return s;
    }

    public Student delete(Integer id){
        Student s=studentFileRepository.delete(id);
        if(s!=null)
            notifyObservers();
        return s;
    }

    public Student update(Student student){
        Student s=studentFileRepository.update(student);
        if(s==null)
            notifyObservers();
        return s;
    }


    public List<Student> filtruGrupa(String grupa){
        List<Student> allStudents=new ArrayList<>();
        studentFileRepository.findAll().forEach(x->allStudents.add(x));
        List<Student> students=allStudents.stream()
                .filter(x->x.getGrupa().equals(grupa))
                .collect(Collectors.toList());
        return students;
    }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(o->o.update());
    }

    public List<NotaDTO> getNotaDTO() {
        List<Student> students = new ArrayList<>();
        studentFileRepository.findAll().forEach(s -> students.add(s));
        List<NotaDTO> notaDTOList=students.stream()
                .map(s->new NotaDTO(s.getId(),s.getNume(),s.getPrenume(),null,null,s.getCadruDidacticIndrumatorLab(),s.getGrupa()))
                .collect(Collectors.toList());
        return notaDTOList;
    }
}

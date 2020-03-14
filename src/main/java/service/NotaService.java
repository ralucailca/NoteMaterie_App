package service;

import domain.*;
import observer.Observable;
import observer.Observer;
import repository.NotaXMLFileRepository;
import repository.StudentXMLFileRepository;
import repository.TemaXMLFileRepository;
import validator.ValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.json.simple.JSONException;
//import org.json.simple.JSONObject;

public class NotaService implements Observable {
    private StudentXMLFileRepository studentFileRepository;
    private TemaXMLFileRepository temaFileRepository;
    private NotaXMLFileRepository notaFileRepository;
    private StructuraAnUniversitar structuraAnUniversitar;
    private List<Observer> observers=new ArrayList<>();

    public NotaService(StudentXMLFileRepository studentFileRepository, TemaXMLFileRepository temaFileRepository,
                       NotaXMLFileRepository notaFileRepository, StructuraAnUniversitar structuraAnUniversitar) {
        this.studentFileRepository = studentFileRepository;
        this.temaFileRepository = temaFileRepository;
        this.notaFileRepository = notaFileRepository;
        this.structuraAnUniversitar = structuraAnUniversitar;
    }

    //save()
       // this.save(..., LocalDateTime.now())

    public Nota save(Nota n){
        //cazul in care studentul/tema nu exista
        if(studentFileRepository.findOne(n.getId().getIdStudent())==null)
            throw new ValidationException("Nu exista studentul cu id-ul: "+n.getId().getIdStudent());
        if(temaFileRepository.findOne(n.getId().getIdTema())==null)
            throw new ValidationException("Nu exista tema cu id-ul: "+n.getId().getIdTema());
        //nota intre 1 si nota maxima posibila
        if(n.getValoare()>calculNotaMaxima(n.getId().getIdTema(), n.getData()) || n.getValoare()<1)
            throw new ValidationException("Valoare notei e invalida!");
        //cazul in care nota este predata cu mai mult de 2 saptamani dupa deadline
        if(calculIntarzaiere(n.getId().getIdTema(), n.getData())>2)
            throw new ValidationException("Predare mai tarziu de 2 saptamani. Tema nu mai poate fi predata.");
        //adaugare nota in fisier-ul cu note dat de numele studentului

        Nota nota=notaFileRepository.save(n);
        if(nota==null)
            notifyObservers();
        return nota;
    }

    //salveaza o nota luand in calcul faptul ca studentul a lipsit motivat in decursul saptamanilor respective
    //startDate - data de inceput a motivarii
    //finalDate - data de final a motivarii
    public Nota saveMotivated(Nota n, LocalDateTime startData, LocalDateTime finalData) {
        //cazul in care studentul/tema nu exista
        if(studentFileRepository.findOne(n.getId().getIdStudent())==null)
            throw new ValidationException("Nu exista studentul cu id-ul: "+n.getId().getIdStudent());
        if(temaFileRepository.findOne(n.getId().getIdTema())==null)
            throw new ValidationException("Nu exista tema cu id-ul: "+n.getId().getIdTema());
        //nota intre 1 si nota maxima posibila
        if(n.getValoare()>calculNotaMaximaMotivare(n.getId().getIdTema(), n.getData(), startData, finalData) || n.getValoare()<1)
            throw new ValidationException("Valoare notei e invalida!");
        //cazul in care nota este predata cu mai mult de 2 saptamani dupa deadline
        if(calculIntarzaiere(n.getId().getIdTema(), n.getData())-calculMotivare(n.getId().getIdTema(),startData,finalData)>2)
            throw new ValidationException("Predare mai tarziu de 2 saptamani. Tema nu mai poate fi predata.");
        //adaugare nota in fisier-ul cu note dat de numele studentului

        Nota nota=notaFileRepository.save(n);
        if(nota==null)
            notifyObservers();
        return nota;
    }

    public int calculMotivare(Integer idT, LocalDateTime startData, LocalDateTime finalData){
           int deadline=temaFileRepository.findOne(idT).getDeadlineWeek();
           int startw=structuraAnUniversitar.getDataWeek(startData);
           int finalw=structuraAnUniversitar.getDataWeek(finalData);
           if(startw<deadline){
               startw=deadline;
           }
           return finalw-startw+1;
    }


    //Calculeaza cate saptamani de intarzaiere are pentru tema respectiva
    //returneaza un intreg = numarul de saptamani de intarzaiere
    //idT - id-ul temei la care va fi pusa nota
    //data - data la care a fost data nota
    //Arunca exceptie daca nu exista tema  pentru care se doreste sa se puna nota
    //Arunca exvceptie daca data cand a fost data nota este incorecta, inainte de data de adaugare a temei
    //cazul in care profesorul introduce nota in alta saptamana decat cea a predarii este rezolvat deoarece se calculeaza
    //dupa data in care a fost predata tema introdusa de la tastatura
    public int calculIntarzaiere(Integer idT, LocalDateTime data){
        //int curentWeek=structuraAnUniversitar.getWeek();
        int dataWeek=structuraAnUniversitar.getDataWeek(data);
        Tema tema=temaFileRepository.findOne(idT);
        if(tema==null)
            throw new ValidationException("Nu exista tema cu id-ul: "+idT);
        if(dataWeek<tema.getStartWeek())
            throw new ValidationException("Data introdusa de predare este incorecta (inainte de stratWeek)");
        int deadlinetweek=tema.getDeadlineWeek();
        return dataWeek-deadlinetweek;
    }

    //calculeaza nota maxima pe care o poate lua studentul la tema respectiva in data predarii temei
    public int calculNotaMaxima(Integer idT, LocalDateTime data){
           Integer notamax=10;
           if(calculIntarzaiere(idT, data)>=0)  return notamax-calculIntarzaiere(idT, data);
           return notamax;
    }

    public int calculNotaMaximaMotivare(Integer idT, LocalDateTime data, LocalDateTime startData, LocalDateTime finalData){
        return calculNotaMaxima(idT,data)+calculMotivare(idT, startData, finalData);
    }

    public void salvareFisier(Nota n, String feedback) {
        Integer idS=n.getId().getIdStudent();
        Integer idT=n.getId().getIdTema();
        LocalDateTime data=n.getData();
        Double nota=n.getValoare();
        Tema t=temaFileRepository.findOne(idT);
        Student s=studentFileRepository.findOne(idS);
        //JSONObject json=new JSONObject();
        Map<String,String> map=new LinkedHashMap<>();
        try {
            map.put("Tema ", t.getId().toString()+"\n");
            map.put("Nota ",nota.toString()+"\n");
            map.put("Predata in saptamana ",String.valueOf(structuraAnUniversitar.getDataWeek(data))+"\n");
            map.put("Deadline ",String.valueOf(t.getDeadlineWeek())+"\n");
            map.put("Feedback ",feedback+"\n");
            String filename="TxtFiles/"+s.getNume()+s.getPrenume()+".txt";
            Path path= Paths.get(filename);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            Files.write(path, map.toString().getBytes(), StandardOpenOption.APPEND);
            Files.write(path, "\n".getBytes(), StandardOpenOption.APPEND);
        }catch( IOException e){
            e.printStackTrace();
        }
    }

    public Iterable<Nota> findAll(){
        return notaFileRepository.findAll();
    }

    public Nota findOne(Integer idS, Integer idT){
        return notaFileRepository.findOne(new IdNota(idS, idT));
    }

    public Nota delete(Integer idS, Integer idT){
        Nota nota= notaFileRepository.delete(new IdNota(idS, idT));
        if(nota!=null)
            notifyObservers();
        return nota;
    }

    public Nota update(Nota n){
        Integer idS=n.getId().getIdStudent();
        Integer idT=n.getId().getIdTema();
        LocalDateTime data=n.getData();
        Double nota=n.getValoare();
        if(studentFileRepository.findOne(idS)==null)
            throw new ValidationException("Nu exista studentul cu id-ul: "+idS);
        if(temaFileRepository.findOne(idT)==null)
            throw new ValidationException("Nu exista tema cu id-ul: "+idT);
        //nota intre 1 si nota maxima posibila
        if(nota>calculNotaMaxima(idT, data) || nota<1)
            throw new ValidationException("Valoare notei e invalida!");
        //cazul in care nota este predata cu mai mult de 2 saptamani dupa deadline
        if(calculIntarzaiere(idT, data)>2)
            throw new ValidationException("Predare mai tarziu de 2 saptamani. Tema nu mai poate fi predata.");

        Nota n1=notaFileRepository.update(n);
        if(n1==null)
            notifyObservers();
        return n1;
    }


    public void stergeDupaStudent(Integer id) {
        List<Nota> note=new ArrayList<>();
        notaFileRepository.findAll().forEach(n->note.add(n));
        for(Nota n:note){
            if(id.compareTo(n.getId().getIdStudent())==0)
                notaFileRepository.delete(n.getId());
        }
        notifyObservers();
    }

    public void stergeDupaTema(Integer id) {
        List<Nota> note=new ArrayList<>();
        notaFileRepository.findAll().forEach(n->note.add(n));
        for(Nota n:note){
            if(id.compareTo(n.getId().getIdTema())==0)
                notaFileRepository.delete(n.getId());
        }
    }

    public List<Student> filtruTema(Integer idT){
        List<Nota> note=new ArrayList<>();
        notaFileRepository.findAll().forEach(x->note.add(x));
        List<Student> students=note.stream()
                .filter(x->idT.equals(x.getId().getIdTema()))
                .map(x->studentFileRepository.findOne(x.getId().getIdStudent()))
                .collect(Collectors.toList());
        return students;
    }

    public List<Student> filtruTemaProfesor(Integer idT, String profesor){
        List<Nota> note=new ArrayList<>();
        notaFileRepository.findAll().forEach(x->note.add(x));
        List<Student> students=note.stream()
                .filter(x->(idT.equals(x.getId().getIdTema()) && x.getProfesor().equals(profesor)))
                .map(x->studentFileRepository.findOne(x.getId().getIdStudent()))
                .collect(Collectors.toList());
        return students;
    }

    public List<Nota> filtruNote(Integer idT, int saptamana){
        List<Nota> all=new ArrayList<>();
        notaFileRepository.findAll().forEach(x->all.add(x));
        List<Nota> note=all.stream()
                .filter(x->{
                    //dupa saptamana
                    LocalDateTime data=x.getData();
                    int sapt=structuraAnUniversitar.getDataWeek(data);
                    return idT.equals(x.getId().getIdTema()) && sapt==saptamana;
                })
                .collect(Collectors.toList());
        return note;
    }

    public List<NotaDTO> getNotaDTO(){
        List<Nota> all=new ArrayList<>();
        notaFileRepository.findAll().forEach(n->all.add(n));
        List<NotaDTO> noteDTO=all.stream()
                .map(n->{
                    Student student=studentFileRepository.findOne(n.getId().getIdStudent());
                    Tema tema=temaFileRepository.findOne(n.getId().getIdTema());
                    return new NotaDTO(student.getId(),student.getNume(),student.getPrenume(),tema.getId(),n.getValoare(),n.getProfesor(),student.getGrupa());
                })
                .collect(Collectors.toList());
        return noteDTO;
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

    /*
    Nota la laborator pentru fiecare student (media ponderata a notelor de la
    temele de laborator; pondere tema=nr de saptamani alocate temei)
     */
    public List<NotaDTO> raport1(){
        List<Nota> all=new ArrayList<>();
        notaFileRepository.findAll().forEach(all::add);
        List<Student> students=new ArrayList<>();
        studentFileRepository.findAll().forEach(students::add);
        List<Tema> teme=new ArrayList<>();
        temaFileRepository.findAll().forEach(teme::add);
        List<NotaDTO> notaDTOList=new ArrayList<>();
        Double ponderi=teme.stream()
                .map(t->(double)(t.getDeadlineWeek()-t.getStartWeek()))
                .reduce(0D,(v1,v2)->v1+v2);
        students.forEach(s->{
            List<Nota> note=all.stream()
                    .filter(n->n.getId().getIdStudent()==s.getId())
                    .collect(Collectors.toList());
            Double suma=note.stream()
                    .map(n->{
                        Tema t=temaFileRepository.findOne(n.getId().getIdTema());
                        return n.getValoare()*(t.getDeadlineWeek()-t.getStartWeek());
                    })
                    .reduce(0D,(v1,v2)->v1+v2);

            Double mediaPonderata=suma/ponderi;
            notaDTOList.add(new NotaDTO(s.getId(),s.getNume(),s.getPrenume(),null,mediaPonderata,s.getCadruDidacticIndrumatorLab(),s.getGrupa()));
        });
        return notaDTOList;
    }

    /*
     Cea mai grea tema: media notelor la tema respectiva este cea mai mica.
     */
    public List<NotaDTO> raport2(){
        List<Student> students=new ArrayList<>();
        studentFileRepository.findAll().forEach(students::add);
        List<Nota> all=new ArrayList<>();
        notaFileRepository.findAll().forEach(all::add);
        List<Tema> teme=new ArrayList<>();
        temaFileRepository.findAll().forEach(teme::add);
        List<NotaDTO> notaDTOList=new ArrayList<>();
        Double min=10.0;
        for(Tema t: teme){
            List<Nota> note=all.stream()
                    .filter(n->n.getId().getIdTema()==t.getId())
                    .collect(Collectors.toList());
            Double suma=note.stream()
                    .map(n->n.getValoare())
                    .reduce(0D,(v1,v2)->v1+v2);
            Double medieNote;
            if(students.size()==0)
                medieNote=0D;
            else
                medieNote=suma/students.size();
            if(medieNote<min){
                min=medieNote;
                notaDTOList.clear();
                notaDTOList.add(new NotaDTO(null,null,null, t.getId(), medieNote,null,null));
            }else if(medieNote==min)
                notaDTOList.add(new NotaDTO(null,null,null, t.getId(), medieNote,null,null));

        }
        return notaDTOList;
    }

    /*
    : Studentii care pot intra in examen (media mai mare sau egala cu 4).
     */
    public List<Student> raport3(){
        List<NotaDTO> notaDTOList=raport1();
        List<Student> students= notaDTOList.stream()
                .filter(n->n.getNota()>=4)
                .map(n->studentFileRepository.findOne(n.getStudentID()))
                .collect(Collectors.toList());
        return students;
    }

    public List<Student> raport4(){
        List<Nota> all=new ArrayList<>();
        notaFileRepository.findAll().forEach(all::add);
        List<Tema> teme=new ArrayList<>();
        temaFileRepository.findAll().forEach(teme::add);
        Map<Student, List<Nota>> map=all.stream()
                .collect(Collectors.groupingBy(x->studentFileRepository.findOne(x.getId().getIdStudent())));
        List<Student> students=new ArrayList<>();
        map.entrySet().forEach(x->{
            List<Nota> note=x.getValue();
            //un student trebuie sa aiba o nota la fiecare tema pentru a avea toate temele predate
            if(note.size()==teme.size()) {
                Boolean ok=true;
                for(Nota n:note){
                    Tema t=temaFileRepository.findOne(n.getId().getIdTema());
                    if(structuraAnUniversitar.getDataWeek(n.getData())>t.getDeadlineWeek()){
                        ok=false;
                        break;
                    }
                }
                if(ok) students.add(x.getKey());
            }
        });
        return students;
    }

}

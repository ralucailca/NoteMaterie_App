package service;

import domain.StructuraAnUniversitar;
import domain.Tema;
import repository.TemaXMLFileRepository;

import java.util.ArrayList;
import java.util.List;

public class TemaService {
    private TemaXMLFileRepository temaFileRepository;
    private StructuraAnUniversitar structuraAnUniversitar;

    public TemaService(TemaXMLFileRepository temaFileRepository, StructuraAnUniversitar structuraAnUniversitar) {
        this.temaFileRepository = temaFileRepository;
        this.structuraAnUniversitar=structuraAnUniversitar;
    }

    public Tema findOne(Integer id){
        return temaFileRepository.findOne(id);
    }

    public Iterable<Tema> findAll(){
        return temaFileRepository.findAll();
    }

    public Tema save(Integer id, String descriere, int deadlineWeek){
        Tema tema=new Tema(descriere, structuraAnUniversitar.getWeek(), deadlineWeek);
        tema.setId(id);
        return temaFileRepository.save(tema);
    }

    public Tema delete(Integer id){
        return temaFileRepository.delete(id);
    }

    public Tema update(Integer id, String descriere, int deadlineWeek){
        //pastram startweek-ul temei anterioare
        Tema t=temaFileRepository.findOne(id);
        int startweek;
        if(t==null) startweek=structuraAnUniversitar.getWeek();
        else
            startweek=t.getStartWeek();
        Tema tema=new Tema(descriere, startweek, deadlineWeek);
        tema.setId(id);
        return temaFileRepository.update(tema);
    }

    //returneaza indexul din lista a temei care are deadline in saptamana curenta
    public int getIndexTemaCurenta(){
        List<Tema> teme=new ArrayList<>();
        temaFileRepository.findAll().forEach(t->teme.add(t));
        for (int i=0; i<teme.size(); i++) {
            if(teme.get(i).getDeadlineWeek()==structuraAnUniversitar.getWeek())
                return i;
        }
        return 0;
    }
}

package ui;
/*
import domain.Nota;
import domain.Student;
import domain.Tema;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import validator.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consola {
    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;

    public Consola(StudentService studentService, TemaService temaService, NotaService notaService) {
        this.studentService = studentService;
        this.temaService = temaService;
        this.notaService = notaService;
    }

    private void adaugaStudent(String[] param){
        if(param.length!=7)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Student s=studentService.save(id, param[2], param[3],param[4],param[5],param[6]);
            if(s==null)
                System.out.println("Studentul a fost adaugat.");
            else
                System.out.println("Studentul exista deja salvat.");
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void stergeStudent(String[] param){
        if(param.length!=2)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Student s=studentService.delete(id);
            if(s==null)
                System.out.println("Studentul nu exista.");
            else {
                System.out.println("Studentul " + s + " a fost sters.");
                notaService.stergeDupaStudent(id);
            }
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void modificaStudent(String[] param) {
        if(param.length!=7)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Student s=studentService.update(id, param[2], param[3],param[4],param[5],param[6]);
            if(s==null)
                System.out.println("Studentul a fost modificat.");
            else
                System.out.println("Studentul "+s+ " nu exista");
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void cautaStudent(String[] param) {
        if(param.length!=2)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Student s=studentService.findOne(id);
            if(s==null)
                System.out.println("Studentul cautat nu exista");
            else
                System.out.println(s);
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    public static <E> void  afisare(Iterable<E> l) {
        l.forEach((E e)-> System.out.println(e));
    }

    private void cautaTema(String[] param) {
        if(param.length!=2)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Tema t=temaService.findOne(id);
            if(t==null)
                System.out.println("Tema cautata nu exista");
            else
                System.out.println(t);
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void modificaTema(String[] param) {
        if(param.length!=4)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Integer deadline=Integer.parseInt(param[3]);
            Tema t=temaService.update(id, param[2], deadline);
            if(t==null)
                System.out.println("Tema a fost modificata.");
            else
                System.out.println("Tema "+t+" nu exista salvata.");
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void stergeTema(String[] param) {
        if(param.length!=2)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Tema t=temaService.delete(id);
            if(t==null)
                System.out.println("Tema cautata nu exista");
            else {
                System.out.println("Tema " + t + "a fost stearsa");
                notaService.stergeDupaTema(id);
            }
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void adaugaTema(String[] param) {
        if(param.length!=4)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer id=Integer.parseInt(param[1]);
            Integer deadline=Integer.parseInt(param[3]);
            Tema t=temaService.save(id, param[2], deadline);
            if(t==null)
                System.out.println("tema a fost adaugata.");
            else
                System.out.println("Tema exista deja salvata.");
        }catch (NumberFormatException e){
            System.out.println("Parametrii invalizi");
        }
    }

    private void adaugaNota() {
        try {
            Nota n;
            Double nota;
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            //daca are motivare sau nu
            System.out.println("Introduceti id-ul studentului");
            Integer idS = Integer.parseInt(br.readLine());
            System.out.println("Introduceti id-ul temei");
            Integer idT = Integer.parseInt(br.readLine());
            System.out.println("Profesorul a intarzaiat cu introducerea notei? Da/Nu");
            String answer=br.readLine();
            LocalDateTime data;
            String[] cmp;
            if(answer.equals("Da")) {
                System.out.println("Introduceti data predarii de format: yyyy-MM-dd");
                String d = br.readLine();
                cmp = d.split("-");
                data = LocalDateTime.of(Integer.parseInt(cmp[0]), Integer.parseInt(cmp[1]), Integer.parseInt(cmp[2]), 0, 0);
            }
            else{
                data=LocalDateTime.now();
            }
            System.out.println("Introdeucti numele profesorului");
            String profesor = br.readLine();
            if(notaService.calculIntarzaiere(idT,data)>2){
                System.out.println("Tema nu mai poate fi predata deoarece a depasit 2 saptamani de intarzaiere. Are studentul motivare medicala?");
                String rasp=br.readLine();
                if(rasp.compareTo("nu")==0 || rasp.compareTo("Nu")==0 || rasp.compareTo("NU")==0){
                    System.out.println("Nota nu poate fi adaugata.");
                    return;
                }
                //adaugam cu motivare
                System.out.println("Introduceti perioada: data de inceput si data de incheiere, format data: yyyy-MM-dd");
                System.out.println("Data inceput:");
                String startd=br.readLine();
                System.out.println("Data sfarsit");
                String finald=br.readLine();
                cmp = startd.split("-");
                LocalDateTime startData = LocalDateTime.of(Integer.parseInt(cmp[0]), Integer.parseInt(cmp[1]), Integer.parseInt(cmp[2]), 0, 0);
                cmp = startd.split("-");
                LocalDateTime finalData = LocalDateTime.of(Integer.parseInt(cmp[0]), Integer.parseInt(cmp[1]), Integer.parseInt(cmp[2]), 0, 0);
                System.out.println("Nota maxima care poate fi acordata temei este: "+notaService.calculNotaMaximaMotivare(idT,data,startData,finalData));
                System.out.println("Introduceti nota");
                nota = Double.parseDouble(br.readLine());
                n=notaService.saveMotivated(idS, idT, data, profesor, nota,startData,finalData);
            }else{
                System.out.println("Nota maxima care poate fi acordata temei este: "+notaService.calculNotaMaxima(idT,data));
                System.out.println("Introduceti nota");
                nota = Double.parseDouble(br.readLine());

                n = notaService.save(idS, idT, data, profesor, nota);
            }
            if(n==null) {
                System.out.println("Nota a fost adaugata.");
                System.out.println("Dati un feedback studentului: ");
                String feedback=br.readLine();
                notaService.salvareFisier(idS, idT, data, nota,feedback);
            }else
                System.out.println("Nota exista deja.");
        }catch(NumberFormatException | IOException e){
            e.printStackTrace();
        }catch(ArrayIndexOutOfBoundsException | DateTimeException e){
            System.out.println("Data incorecta.");
        }

    }

    private void stergeNota(String[] param) {
        if(param.length!=3)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer idS = Integer.parseInt(param[1]);
            Integer idT = Integer.parseInt(param[2]);
            Nota n=notaService.delete(idS,idT);
            if(n==null)
                System.out.println("Nota nu exista.");
            else
                System.out.println("Nota "+n+" a fost stearsa");
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void cautaNota(String[] param) {
        if(param.length!=3)
            throw new ValidationException("Numar invalid de parametrii!");
        try{
            Integer idS = Integer.parseInt(param[1]);
            Integer idT = Integer.parseInt(param[2]);
            Nota n=notaService.findOne(idS,idT);
            if(n==null)
                System.out.println("Nota nu exista.");
            else
                System.out.println(n);
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void modificaNota(String[] param) {
        if(param.length!=6)
            throw new ValidationException("Numar invalid de parametrii!");
        try {
            Integer idS = Integer.parseInt(param[1]);
            Integer idT = Integer.parseInt(param[2]);
            LocalDateTime data;
            String[] cmp = param[3].split("-");
            data = LocalDateTime.of(Integer.parseInt(cmp[0]), Integer.parseInt(cmp[1]), Integer.parseInt(cmp[2]), 0, 0);
            String profesor = param[4];
            Double nota = Double.parseDouble(param[5]);
            Nota n = notaService.update(idS, idT, data, profesor, nota);
            if(n==null)
                System.out.println("Nota a fost modificata.");
            else
                System.out.println("Nota nu exista.");
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }

    private void afisareNote(Iterable<Nota> note){
        DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        note.forEach(x->{
            System.out.println("Student: "+studentService.findOne(x.getId().getIdStudent()));
            System.out.println("Tema: "+ temaService.findOne(x.getId().getIdTema()));
            System.out.println("Data: "+x.getData().format(format));
            System.out.println("Profesor: "+x.getProfesor());
            System.out.println("Nota: "+x.getValoare());
            System.out.println();
        });
    }

    private void filtruGrupa(String[] param){
        if(param.length!=2)
            throw new ValidationException("Numar invalid de parametrii: filtruGrupa + grupa");
        afisare(studentService.filtruGrupa(param[1]));
    }

    private void filtruTremaProf(String[] param) {
        if(param.length!=3)
            throw new ValidationException("Numar invalid de parametrii: filtruTemaProf + id tema  + profesor");
        afisare(notaService.filtruTemaProfesor(Integer.parseInt(param[1]),param[2]));
    }

    private void filtruTema(String[] param) {
        if(param.length!=2)
            throw new ValidationException("Numar invalid de parametrii: filtruTema + id tema");
        afisare(notaService.filtruTema(Integer.parseInt(param[1])));
    }

    private void filtruNote(String[] param){
        if(param.length!=3)
            throw new ValidationException("Numar invalid de parametrii: filtruNote + id tema  + saptamana");
        afisareNote(notaService.filtruNote(Integer.parseInt(param[1]),Integer.parseInt(param[2])));
    }

    public void run(){
        System.out.println(
                "exit\n"+
                "adaugaStudent "+
                "stergeStudent "+
                "modificaStudent "+
                "cautaStudent "+
                "afisareStudenti\n"+
                "adaugaTema "+
                "cautaTema "+
                "stergeTema "+
                "modificaTema "+
                "afiseazaTeme\n"+
                "adaugaNota "+
                "stergeNota "+
                "cautaNota "+
                "modificaNota "+
                "afisareNote\n"+
                "filtruGrupa "+
                "filtruTema "+
                "filtruTemaProf "+
                "filtruNote\n"
        );
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        while(true){
            try{
                String cmd=br.readLine();
                String[] param=cmd.split(" ");

                switch(param[0]){
                    case "exit":
                        return;
                    case "adaugaStudent":
                        adaugaStudent(param);
                        break;
                    case "stergeStudent":
                        stergeStudent(param);
                        break;
                    case "modificaStudent":
                        modificaStudent(param);
                        break;
                    case "cautaStudent":
                        cautaStudent(param);
                        break;
                    case "afisareStudenti":
                        afisare( studentService.findAll() );
                        break;
                    case "adaugaTema":
                        adaugaTema(param);
                        break;
                    case "stergeTema":
                        stergeTema(param);
                        break;
                    case "modificaTema":
                        modificaTema(param);
                        break;
                    case "cautaTema":
                        cautaTema(param);
                        break;
                    case "afisareTeme":
                        afisare( temaService.findAll() );
                        break;
                    case "adaugaNota":
                        adaugaNota();
                        break;
                    case "stergeNota":
                        stergeNota(param);
                        break;
                    case "cautaNota":
                        cautaNota(param);
                        break;
                    case "modificaNota":
                        modificaNota(param);
                        break;
                    case "afisareNote":
                        afisareNote(notaService.findAll());
                        //afisare(notaService.findAll());
                        break;
                    case "filtruGrupa":
                        filtruGrupa(param);
                        break;
                    case "filtruTema":
                        filtruTema(param);
                        break;
                    case "filtruTemaProf":
                        filtruTremaProf(param);
                        break;
                    case "filtruNote":
                        filtruNote(param);
                        break;
                    default:
                        System.out.println("Comanda invalida");
                        break;
                }
            }catch(IOException e){
                e.printStackTrace();
            }catch(ValidationException e){
                System.out.println(e.getMessage());
            }catch(NumberFormatException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
*/
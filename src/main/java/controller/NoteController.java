package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import observer.Observer;
import service.NotaService;
import service.StudentService;
import service.TemaService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteController implements Observer {
    private StudentService studentService;
    private NotaService notaService;
    private TemaService temaService;
    private Student student;
    private String mesajNotaMaxima="";

    private ObservableList<NotaDTO> modelNote= FXCollections.observableArrayList();
    private ObservableList<NotaDTO> modelStudenti= FXCollections.observableArrayList();
    private ObservableList<Integer> modelTeme= FXCollections.observableArrayList();

    @FXML
    private Button btnStudenti;

    @FXML
    private TableView<NotaDTO> tblNoteDTO;

    @FXML
    private TableColumn<String, String> tblColumnName;

    @FXML
    private TableColumn<String, String> tblColumnPrenume;

    @FXML
    private TableColumn<String, Integer> tblColumnTema;

    @FXML
    private TableColumn<String, Double> tblColumnNota;

    @FXML
    private TableColumn<String, String> tblColumnProf;

    @FXML
    private TableColumn<String, String> tblColumnGrupa;

    @FXML
    private TableColumn<String, Integer> tblStudentId;

    @FXML
    private ComboBox<Integer> comboBoxTeme;

    @FXML
    private TextField txtName;

    @FXML
    private CheckBox checkIntarzaiere;

    @FXML
    private CheckBox checkMotivare;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker finalDate;

    @FXML
    private TextField txtNota;

    @FXML
    private Button btnAdd;

    @FXML
    private DatePicker dataPredare;

    @FXML
    private TextField txtProf;

    @FXML
    private Button btnStudent;

    @FXML
    private RadioButton feedback1;

    @FXML
    private RadioButton feedback2;

    @FXML
    private TextArea txtArea;

    @FXML
    private Button btnRaport1;

    @FXML
    private Button btnRaport2;

    @FXML
    private Button btnRaport3;

    @FXML
    private Button btnRaport4;


    public void setService(StudentService studentService, TemaService temaService, NotaService notaService){
        this.studentService=studentService;
        this.notaService=notaService;
        this.temaService=temaService;
        initModel();
        notaService.addObserver(this);
        comboBoxTeme.getSelectionModel().select(temaService.getIndexTemaCurenta());
        startDate.setDisable(true);
        finalDate.setDisable(true);
        dataPredare.setDisable(true);
    }

    private void initModel() {
        modelNote.setAll(notaService.getNotaDTO());
        List<Tema> teme=new ArrayList<>();
        temaService.findAll().forEach(t->teme.add(t));
        modelTeme.setAll( teme.stream()
                .map(t->t.getId())
                .collect(Collectors.toList())
        );
        modelStudenti.setAll(studentService.getNotaDTO());
    }

    private void initTable(){
        tblColumnName.setCellValueFactory(new PropertyValueFactory<String, String>("nume"));
        tblColumnPrenume.setCellValueFactory(new PropertyValueFactory<String, String>("prenume"));
        tblColumnTema.setCellValueFactory(new PropertyValueFactory<String, Integer>("temaID"));
        tblColumnNota.setCellValueFactory(new PropertyValueFactory<String, Double>("nota"));
        tblColumnProf.setCellValueFactory(new PropertyValueFactory<String,String>("profesor"));
        tblColumnGrupa.setCellValueFactory(new PropertyValueFactory<String,String>("grupa"));
        tblStudentId.setCellValueFactory(new PropertyValueFactory<String, Integer>("studentID"));
        tblNoteDTO.setItems(modelNote);
    }

    @FXML
    public void initialize(){
        initTable();
        comboBoxTeme.setItems(modelTeme);
        checkIntarzaiere.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if(checkIntarzaiere.isSelected()){
                dataPredare.setDisable(false);
            }
            else{
                dataPredare.setDisable(true);
            }
        }));
        checkMotivare.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if(checkMotivare.isSelected()){
                startDate.setDisable(false);
                finalDate.setDisable(false);
            }else{
                startDate.setDisable(true);
                finalDate.setDisable(true);
            }
        }));
        txtName.textProperty().addListener(((observable, oldValue, newValue) -> filterHandle()));
        btnRaport1.setTooltip(new Tooltip(" Nota finala la laborator pentru fiecare student."));
        btnRaport2.setTooltip(new Tooltip(" Cea mai grea tema."));
        btnRaport3.setTooltip(new Tooltip(" Studentii care pot intra in examen."));
        btnRaport4.setTooltip(new Tooltip(" Studentii care au predat la timp toate temele"));
    }

    private void filterHandle() {
        tblNoteDTO.setItems(modelStudenti);
        List<NotaDTO> all=studentService.getNotaDTO();
        modelStudenti.setAll(all.stream()
            .filter(n->n.getNume().startsWith(txtName.getText()))
            .collect(Collectors.toList())
        );
    }

    @FXML
    void handleAdd(ActionEvent event) {
        if(student==null)
            MessageAlert.showErrorMessage(null,"Nu este nici un student selectat. Pentru a adauga o nota " +
                    "selectati din tabel un student caruia ii va fi adaugata nota.");
        else
        try {
            LocalDateTime data = LocalDateTime.now();
            LocalDateTime datas = LocalDateTime.now();
            LocalDateTime dataf = LocalDateTime.now();
            LocalDate d, ds, df;
            int notaMax;
            if (checkIntarzaiere.isSelected()) {
                d = dataPredare.getValue();
                if (d == null) {
                    MessageAlert.showErrorMessage(null, "Nu a fost selectata data predarii.");
                    return;
                }
                data = d.atStartOfDay();
            }
            Nota n = new Nota(data, txtProf.getText(), Double.parseDouble(txtNota.getText()) );
            n.setId(new IdNota(student.getId(), comboBoxTeme.getSelectionModel().getSelectedItem()));
            Tema tema=temaService.findOne(comboBoxTeme.getSelectionModel().getSelectedItem());
            System.out.println("Tema este: "+ tema.toString());
            if (checkMotivare.isSelected()) {
                ds = startDate.getValue();
                df = finalDate.getValue();
                if (df == null || ds == null) {
                    MessageAlert.showErrorMessage(null, "Nu a fost selectate datele de inceput si final ale motivarii.");
                    return;
                }
                datas = ds.atStartOfDay();
                dataf = df.atStartOfDay();
                //notaMax=notaService.calculNotaMaximaMotivare(n.getId().getIdTema(),n.getData(),datas,dataf);
                //String text=txtArea.getText();
                //txtArea.setText("\nNota maxima care poate fi acordata este: "+notaMax);
                adaugaNota(n, tema,true, datas, dataf, txtArea.getText());
            }
            else{
                //notaMax = notaService.calculNotaMaxima(n.getId().getIdTema(),n.getData());
                //String text=txtArea.getText();
                //txtArea.setText("\nNota maxima care poate fi acordata este: "+notaMax);
                adaugaNota(n,tema,false, datas, dataf, txtArea.getText());
            }
        }catch (NumberFormatException e){
            MessageAlert.showErrorMessage(null, "Nota scrisa nu este un numar real.");
       }
    }

    //deschide o noua fereastra pentru verificare si din care se adauga notele
    private void adaugaNota(Nota n,Tema tema, boolean motivare, LocalDateTime startData, LocalDateTime finalData, String feedback) {
       try {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/previewNotaView.fxml"));
           AnchorPane paneRoot = loader.load();
           Stage stage = new Stage();
           stage.setTitle("Adaugare nota:");
           stage.initModality(Modality.WINDOW_MODAL);
           stage.setScene(new Scene(paneRoot));

           PreviewNotaController previewNotaController = loader.getController();
           previewNotaController.setService(notaService, n, student, tema, motivare, startData, finalData, feedback);

           stage.show();
           txtArea.setText("");
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    @FXML
    void handleFeedback1(ActionEvent event) {
        if(feedback1.isSelected()){
            feedback2.setSelected(false);

        String text1="De apreciat: " +" "+" \n"+
                "\nMinusuri: "+" \n"+
                "\nPoate fi imbunatatit: "+" \n";
        txtArea.setText(text1+""+mesajNotaMaxima);
        }else{
            txtArea.setText("");
        }
    }

    @FXML
    void handleFeedback2(ActionEvent event) {
        if(feedback2.isSelected()) {
            feedback1.setSelected(false);
            String text2 = "Student bine/slab pregatit: " + " " +
                    "\nPunctaje acordate pe fiecare functionalitate: " + " \n" +
                    "\nTrebuie perfectionat: " + " \n" +
                    "\nTrebuie refacut: " + " \n";
            txtArea.setText(text2+"\n"+mesajNotaMaxima);
        }else{
            txtArea.setText("");
        }
    }

    @FXML
    void handleGestionare(ActionEvent event) {
        try {
            FXMLLoader studentsLoader = new FXMLLoader();
            studentsLoader.setLocation(getClass().getResource("/studentView.fxml"));
            AnchorPane studentsLayout = studentsLoader.load();
            Stage studentStage=new Stage();
            studentStage.setTitle("Gestionare Studenti");
            studentStage.initModality(Modality.WINDOW_MODAL);
            studentStage.setScene(new Scene(studentsLayout));

            StudentController studentController = studentsLoader.getController();
            studentController.setService(studentService, notaService);

            studentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleStudent(ActionEvent event) {
        NotaDTO selected=tblNoteDTO.getSelectionModel().getSelectedItem();
        if(selected!=null){
            student=studentService.findOne(selected.getStudentID());
            if(student==null)
                System.out.println("Nu s-a selectat nici un student");
            else
                System.out.println(student.toString());
        }else{
            MessageAlert.showErrorMessage(null,"Nu s-a selectat nici un student.");
        }
        tblNoteDTO.setItems(modelNote);
    }

    @Override
    public void update() {
        initModel();
    }

    @FXML
    void handleAfisareNotaMaxima(MouseEvent event) {
        LocalDateTime data = LocalDateTime.now();
        LocalDateTime datas;
        LocalDateTime dataf;
        LocalDate d, ds, df;
        int notaMax;
        if (checkIntarzaiere.isSelected()) {
            d = dataPredare.getValue();
            if (d == null) {
                MessageAlert.showErrorMessage(null, "Nu a fost selectata data predarii.");
                return;
            }
            data = d.atStartOfDay();
        }
        Integer temaID= comboBoxTeme.getSelectionModel().getSelectedItem();
        if (checkMotivare.isSelected()) {
            ds = startDate.getValue();
            df = finalDate.getValue();
            if (df == null || ds == null) {
                MessageAlert.showErrorMessage(null, "Nu a fost selectate datele de inceput si final ale motivarii.");
                return;
            }
            datas = ds.atStartOfDay();
            dataf = df.atStartOfDay();
            notaMax=notaService.calculNotaMaximaMotivare(temaID,data,datas,dataf);
            //String text=txtArea.getText();
            mesajNotaMaxima="Nota maxima care poate fi acordata este: "+notaMax;
            txtArea.setText("\nNota maxima care poate fi acordata este: "+notaMax);
        }
        else{
            notaMax = notaService.calculNotaMaxima(temaID,data);
            //String text=txtArea.getText();
            mesajNotaMaxima="Nota maxima care poate fi acordata este: "+notaMax;
            txtArea.setText("\nNota maxima care poate fi acordata este: "+notaMax);
        }
    }

    @FXML
    void handleRaport1(ActionEvent event) {
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/mediiView.fxml"));
            AnchorPane root=loader.load();
            Stage stage=new Stage();
            stage.setTitle("Raport studenti medii:");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.show();

            MediiController mediiController=loader.getController();
            mediiController.setList(notaService.raport1());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRaport2(ActionEvent event) {
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/raportTemaView.fxml"));
            AnchorPane root=loader.load();
            Stage stage=new Stage();
            stage.setTitle("Raport cea mai grea tema:");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.show();

            String text="";
            for(NotaDTO n : notaService.raport2()){
                text+="Tema: "+temaService.findOne(n.getTemaID()).toString()+" cu nota medie: "+
                        n.getNota().toString()+"\n";
            }

            RaportTemaController raportTemaController=loader.getController();
            raportTemaController.setTxtArea(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRaport3(ActionEvent event) {
        afisareStudenti(notaService.raport3(),"Studentii care intra in examen:");
    }

    @FXML
    void handleRaport4(ActionEvent event) {
        afisareStudenti(notaService.raport4(),"Studentii care au predat la timp toate temele:");
    }

    private void afisareStudenti(List<Student> students, String text) {
        try{
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("/raportStudentiView.fxml"));
            AnchorPane root=loader.load();
            Stage stage=new Stage();
            stage.setTitle("Raport studenti:");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.show();

            RaportStudentiController raportStudentiController=loader.getController();
            raportStudentiController.setLista(students,text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    @FXML
    void handleStudent(ActionEvent event) {
        try {
            FXMLLoader studentsLoader = new FXMLLoader();
            studentsLoader.setLocation(getClass().getResource("/studentView.fxml"));
            AnchorPane studentsLayout = studentsLoader.load();
            Stage studentStage=new Stage();
            studentStage.setTitle("Gestionare Studenti");
            studentStage.initModality(Modality.APPLICATION_MODAL);
            studentStage.setScene(new Scene(studentsLayout));

            StudentController studentController = studentsLoader.getController();
            studentController.setService(studentService);

            studentStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    System.out.println("S-a inchis");
                   // student = studentController.getStudentSelectat();
                    //System.out.println(student);
                }
            });
            studentStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
        if(student==null)
            MessageAlert.showErrorMessage(null,"Nu s-a selectat nici un student");
        else
            System.out.println("Studentul ales este "+student);
    }
    */
}

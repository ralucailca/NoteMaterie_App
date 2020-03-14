package controller;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import observer.Observer;
import service.NotaService;
import service.StudentService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentController implements Observer, Initializable {
    private StudentService studentService;
    private NotaService notaService;
    private ObservableList<Student> students= FXCollections.observableArrayList();
    private Student studentAles;

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, Integer> tableColumnId;
    @FXML
    private TableColumn<Student, String> tableColumnNume;
    @FXML
    private TableColumn<Student, String> tableColumnPrenume;
    @FXML
    private TableColumn<Student, String> tableColumnProfesor;
    @FXML
    private TableColumn<Student, String> tableColumnEmail;
    @FXML
    private TableColumn<Student, String> tableColumnGrupa;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button bntFindOne;

    @FXML
    private TextField txtFindOne;
    @FXML
    private TextField txtSearchName;
    @FXML
    private Button btnAlege;

    public void setService(StudentService studentService, NotaService notaService){
        this.studentService=studentService;
        this.notaService=notaService;
        studentService.addObserver(this);
        initModel();
    }

    private void initModel() {
        List<Student> all=new ArrayList<>();
        studentService.findAll().forEach(s->all.add(s));
        students.setAll(all);
    }

    @Override
    public void update() {
        initModel();
    }

    public void initializareTabel(){
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student,String>("prenume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student,String>("grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<Student,String>("cadruDidacticIndrumatorLab"));
        tableView.setItems(students);
    }

    @FXML
    void handleDelete(ActionEvent event) {
        Student selected=(Student) tableView.getSelectionModel().getSelectedItem();
        if(selected!=null){
            notaService.stergeDupaStudent(selected.getId());
            Student s=studentService.delete(selected.getId());
            if(s!=null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Stergere:","Studentul a fost sters");
        }else MessageAlert.showErrorMessage(null,"Nu e selectat nici un Student!");
    }

    @FXML
    void handleSave(ActionEvent event) {
        editStudents(null);
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        Student selected=(Student) tableView.getSelectionModel().getSelectedItem();
        if(selected!=null)
            editStudents(selected);
        else
            MessageAlert.showErrorMessage(null,"Nu a fost selectat nici un student pentru a fi modificat!");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializareTabel();
        txtSearchName.textProperty().addListener(((observable, oldValue, newValue) -> handleFilter()));
    }

    private void handleFilter() {
        List<Student> all= new ArrayList<>();
        studentService.findAll().forEach(x->all.add(x));
        students.setAll( all.stream()
                .filter(s->s.getNume().startsWith(txtSearchName.getText()))
                .collect(Collectors.toList())
        );
    }

    @FXML
    void handleFindOne(ActionEvent event) {
        if(!txtFindOne.getText().isEmpty()) {
            Integer id = Integer.parseInt(txtFindOne.getText());
            Student s = studentService.findOne(id);
            editStudents(s);
        }else
            MessageAlert.showErrorMessage(null,"Nu a fost introdus niciun id de cautare a studentului!");
    }

    private void editStudents(Student student) {
        try{
            FXMLLoader editLoader=new FXMLLoader();
            editLoader.setLocation(getClass().getResource("/editStudentsView.fxml"));
            AnchorPane editroot=editLoader.load();
            Stage editStage=new Stage();
            editStage.setTitle("Editare student");
            editStage.initModality(Modality.WINDOW_MODAL);
            editStage.setScene(new Scene(editroot));

            EditStudentsController editStudentsController=editLoader.getController();
            editStudentsController.setService(studentService,editStage,student);

            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleAlege(){
        Student studentAles=(Student) tableView.getSelectionModel().getSelectedItem();
        if(studentAles==null)
            MessageAlert.showErrorMessage(null,"Nu a fost selectat nici un student");
        else {
            Stage stage= (Stage) btnAlege.getScene().getWindow();
            stage.close();
        }
    }

    public Student getStudentSelectat(){
        return studentAles;
    }

}

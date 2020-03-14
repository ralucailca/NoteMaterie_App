package controller;

import domain.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.StudentService;
import validator.ValidationException;

public class EditStudentsController {
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNume;

    @FXML
    private TextField txtPreume;

    @FXML
    private TextField txtGrupa;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtProfesor;

    private StudentService studentService;
    private Stage primaryStage;
    private Student student;

    public void setService(StudentService studentService, Stage primaryStage, Student student){
        this.studentService=studentService;
        this.primaryStage=primaryStage;
        this.student=student;
        if(student!=null){
            setFields(student);
            txtId.setEditable(false);
        }
    }

    private void setFields(Student student) {
        txtId.setText(student.getId().toString());
        txtNume.setText(student.getNume());
        txtPreume.setText(student.getPrenume());
        txtGrupa.setText(student.getGrupa());
        txtEmail.setText(student.getEmail());
        txtProfesor.setText(student.getCadruDidacticIndrumatorLab());
    }

    @FXML
    void handleSave(ActionEvent event) {
        try {
            Integer id = Integer.parseInt(txtId.getText());
            String nume=txtNume.getText();
            String prenume=txtPreume.getText();
            String grupa=txtGrupa.getText();
            String email=txtEmail.getText();
            String profesor=txtProfesor.getText();
            Student s=new Student(nume, prenume, grupa, email, profesor);
            s.setId(id);
            if(student==null)
                saveStudent(s);
            else
                updateStudent(s);
        }catch(NumberFormatException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }

    private void updateStudent(Student s) {
        try{
            if(studentService.update(s)==null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Mesaj modifica:","Studentul a fost modificat");
            else
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Mesaj modifica:","Studentul nu a fost modificat");
        }catch(ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }

    private void saveStudent(Student s) {
        try{
            if(studentService.save(s)==null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Mesaj adauga:", "Studentul a fost adaugat!");
            else
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Mesaj adauga:", "Studentul nu a fost adaugat!");
        }catch(ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
    }


}

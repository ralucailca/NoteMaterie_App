package controller;

import domain.Nota;
import domain.Student;
import domain.Tema;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import service.NotaService;
import validator.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PreviewNotaController {
    private NotaService notaService;
    private Nota nota;
    private Boolean motivare;
    private LocalDateTime startData;
    private LocalDateTime finalData;
    private String feedback;
    private Student student;
    private Tema tema;

    @FXML
    private Label lblStudent;

    @FXML
    private Label lblTema;

    @FXML
    private Label lblData;

    @FXML
    private Label lblNota;

    @FXML
    private Label lblProf;

    @FXML
    private Label lblMotivare;

    @FXML
    private Label lblNotaMax;

    @FXML
    private Button btnOk;

    @FXML
    private Button btnCancel;

    public void setService(NotaService notaService, Nota nota, Student student, Tema tema, Boolean motivare, LocalDateTime startData, LocalDateTime finalData, String feedback){
        this.notaService=notaService;
        this.nota=nota;
        this.motivare=motivare;
        this.startData=startData;
        this.finalData=finalData;
        this.feedback=feedback;
        this.tema=tema;
        this.student=student;
        initializare();
    }

    private void initializare() {
        lblStudent.setText(student.getNume()+" "+student.getPrenume()+" "+student.getGrupa()+" "+student.getEmail());
        lblTema.setText(String.valueOf(tema.getId())+" startWeek: "+tema.getStartWeek()+" deadlineWeek: " +tema.getDeadlineWeek());
        lblData.setText(nota.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lblProf.setText(nota.getProfesor());
        lblNota.setText(String.valueOf(nota.getValoare()));
        if(motivare) {
            lblMotivare.setText("Studentul are motivare: "+startData.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" "+finalData.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            lblNotaMax.setText(String.valueOf(notaService.calculNotaMaximaMotivare(nota.getId().getIdTema(), nota.getData(),startData,finalData)));
        }else{
            lblMotivare.setText("Studentul nu are motivare.");
            lblNotaMax.setText(String.valueOf(notaService.calculNotaMaxima(nota.getId().getIdTema(), nota.getData())));
        }
    }

    @FXML
    void handleCancel(ActionEvent event) {
        Stage stage= (Stage) btnCancel.getScene().getWindow();
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adaugare:","Nota nu a fost adaugata.");
        stage.close();
    }

    @FXML
    void handleOk(ActionEvent event) {
        try{
            if(!motivare){
                if(notaService.save(nota)==null)
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adaugare","Nota a fost adaugata.");
                else
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adugare","Nota nu a fost adaugata.");
            }
            else{
                if(notaService.saveMotivated(nota,startData,finalData)==null)
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adaugare","Nota a fost adaugata.");
                else
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Adugare","Nota nu a fost adaugata.");
            }
            notaService.salvareFisier(nota, feedback);
        }catch(ValidationException e){
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        Stage stage= (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}

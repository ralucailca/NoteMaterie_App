package controller;

import domain.NotaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class MediiController {
    private ObservableList<NotaDTO> modelNote= FXCollections.observableArrayList();


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

    public void setList(List<NotaDTO> lista){
        modelNote.setAll(lista);
    }

    @FXML
    private void initialize(){
        tblColumnName.setCellValueFactory(new PropertyValueFactory<String, String>("nume"));
        tblColumnPrenume.setCellValueFactory(new PropertyValueFactory<String, String>("prenume"));
        tblColumnTema.setCellValueFactory(new PropertyValueFactory<String, Integer>("temaID"));
        tblColumnNota.setCellValueFactory(new PropertyValueFactory<String, Double>("nota"));
        tblColumnProf.setCellValueFactory(new PropertyValueFactory<String,String>("profesor"));
        tblColumnGrupa.setCellValueFactory(new PropertyValueFactory<String,String>("grupa"));
        tblStudentId.setCellValueFactory(new PropertyValueFactory<String, Integer>("studentID"));
        tblNoteDTO.setItems(modelNote);
    }
}

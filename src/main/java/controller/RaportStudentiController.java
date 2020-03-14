package controller;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class RaportStudentiController {
    private List<Student> students;
    private ObservableList<Student> model= FXCollections.observableArrayList();

    @FXML
    private Label label;

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

    public void setLista(List<Student> students, String text){
        this.students=students;
        model.setAll(students);
        label.setText(text);
    }

    @FXML
    public void initialize() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student, String>("prenume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<Student, String>("cadruDidacticIndrumatorLab"));
        tableView.setItems(model);
    }

}

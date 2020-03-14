import config.ApplicationContext;
import controller.NoteController;
import controller.StudentController;
import domain.StructuraAnUniversitar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.NotaXMLFileRepository;
import repository.StudentXMLFileRepository;
import repository.TemaXMLFileRepository;
import service.NotaService;
import service.StudentService;
import service.TemaService;
import validator.ValidatorNota;
import validator.ValidatorStudent;
import validator.ValidatorTema;

import java.io.IOException;

public class MainApp extends Application {

    private StudentService studentService;
    private TemaService temaService;
    private NotaService notaService;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ValidatorStudent validatorStudent=new ValidatorStudent();
        StudentXMLFileRepository studentFileRepository=new StudentXMLFileRepository(validatorStudent,
                ApplicationContext.getPROPERTIES().getProperty("data.catalog.studentiXML"));
        studentService=new StudentService(studentFileRepository);

        ValidatorTema validatorTema=new ValidatorTema();
        TemaXMLFileRepository temaFileRepository=new TemaXMLFileRepository(validatorTema,
                ApplicationContext.getPROPERTIES().getProperty("data.temeXML"));
        StructuraAnUniversitar structuraAnUniversitar=new StructuraAnUniversitar(2019);
        temaService=new TemaService(temaFileRepository,structuraAnUniversitar);

        ValidatorNota validatorNota=new ValidatorNota();
        NotaXMLFileRepository notaFileRepository=new NotaXMLFileRepository(validatorNota,
                ApplicationContext.getPROPERTIES().getProperty("data.catalog.noteXML"));
        notaService=new NotaService(studentFileRepository,temaFileRepository,notaFileRepository,structuraAnUniversitar);
        System.out.println(structuraAnUniversitar.getWeek());
        init2(primaryStage);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    private void init2(Stage primaryStage) throws IOException {
        FXMLLoader noteLoader=new FXMLLoader();
        noteLoader.setLocation(getClass().getResource("/noteView.fxml"));
        AnchorPane noteLayout=noteLoader.load();
        primaryStage.setScene(new Scene(noteLayout));

        NoteController noteController=noteLoader.getController();
        noteController.setService(studentService, temaService, notaService);
    }

    private void init1(Stage primaryStage) throws IOException {
        FXMLLoader studentsLoader=new FXMLLoader();
        studentsLoader.setLocation(getClass().getResource("/studentView.fxml"));
        AnchorPane studentsLayout=studentsLoader.load();
        primaryStage.setScene(new Scene(studentsLayout));

        StudentController studentController=studentsLoader.getController();
        studentController.setService(studentService, notaService);
    }
}

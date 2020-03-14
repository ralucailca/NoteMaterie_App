public class Main {
    public static void main(String[] args) {
        /*
        ValidatorTema validatorTema=new ValidatorTema();
        ValidatorStudent validatorStudent=new ValidatorStudent();

        StudentXMLFileRepository studentFileRepository=new StudentXMLFileRepository(validatorStudent,
                ApplicationContext.getPROPERTIES().getProperty("data.catalog.studentiXML"));
        TemaXMLFileRepository temaFileRepository=new TemaXMLFileRepository(validatorTema,
                ApplicationContext.getPROPERTIES().getProperty("data.temeXML"));

        StudentService studentService=new StudentService(studentFileRepository);
        StructuraAnUniversitar structuraAnUniversitar=new StructuraAnUniversitar(2019);
        TemaService temaService=new TemaService(temaFileRepository,structuraAnUniversitar);

        ValidatorNota validatorNota=new ValidatorNota();
        NotaXMLFileRepository notaFileRepository=new NotaXMLFileRepository(validatorNota,
                ApplicationContext.getPROPERTIES().getProperty("data.catalog.noteXML"));
        NotaService notaService=new NotaService(studentFileRepository,temaFileRepository,notaFileRepository,structuraAnUniversitar);

        //Consola consola=new Consola(studentService,temaService, notaService);
        //consola.run();
        */
        MainApp.main(args);
    }
}

package repository;

import domain.Student;
import validator.ValidationException;
import validator.Validator;

import java.sql.*;


public class StudentDatabaseRepository extends InMemoryRepository<Integer, Student> {

    public StudentDatabaseRepository(Validator<Student> validator) {
        super(validator);
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        Statement statement =null;
        Connection connection=null;
        try{
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/Materie","postgres","acular123@");
            statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("select id, nume, prenume, grupa, email, cadruDidacticIndrumatorLab from Studenti");
            while(resultSet.next()){
                Integer id=Integer.parseInt(resultSet.getString("id"));
                String nume=resultSet.getString("nume");
                String prenume=resultSet.getString("prenume");
                String grupa=resultSet.getString("grupa");
                String email=resultSet.getString("email");
                String cadruDidacticIndrumatorLab=resultSet.getString("cadruDidacticIndrumatorLab");
                Student s=new Student(nume, prenume, grupa, email,cadruDidacticIndrumatorLab);
                s.setId(id);
                super.save(s);
            }
            if(statement!=null)
                statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeStudent(Student student){
        PreparedStatement preparedStatement=null;
        Connection connection=null;
        try{
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/Materie","postgres","acular123@");
            preparedStatement=connection.prepareStatement("INSERT INTO Studenti(id, nume, prenume, grupa, email, cadruDidacticIndrumatorLab) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, student.getId().toString());
            preparedStatement.setString(2,student.getNume());
            preparedStatement.setString(3,student.getPrenume());
            preparedStatement.setString(4,student.getGrupa());
            preparedStatement.setString(5,student.getEmail());
            preparedStatement.setString(6,student.getCadruDidacticIndrumatorLab());
            preparedStatement.executeUpdate();
            if(connection!=null)
                connection.close();
            if(preparedStatement!=null)
                preparedStatement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeData(){
            super.findAll().forEach(s-> {
                writeStudent(s);
            });
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        Student e= super.save(entity);
        writeData();
        return e;
    }

    @Override
    public Student delete(Integer id) {
        Student e=super.delete(id);
        writeData();
        return e;
    }

    @Override
    public Student update(Student entity) {
        Student e=super.update(entity);
        writeData();
        return e;
    }
}

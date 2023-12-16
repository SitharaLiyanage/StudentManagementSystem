package lk.ijse.finalproject.model;

import lk.ijse.finalproject.DB.DBConnection;
import lk.ijse.finalproject.DTO.RegistrationDTO;
import lk.ijse.finalproject.DTO.StudentDTO;
import lk.ijse.finalproject.util.IDGenerator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Studentmodel {
    public static boolean deleteStudent(String email){
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement ps=connection.prepareStatement("delete from student where email =?");
            ps.setString(1,email);
            return ps.executeUpdate()>0;
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public static StudentDTO getStudentDetails(String nameorEmail){
        StudentDTO studentDTO = new StudentDTO();
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement ps=connection.prepareStatement("select * from student where name =? or email = ?");
            ps.setString(1,nameorEmail);
            ps.setString(2,nameorEmail);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                studentDTO.setStuid(rs.getString(1));
                studentDTO.setCls(rs.getString(2));
                studentDTO.setName(rs.getString(3));
                studentDTO.setEmail(rs.getString(4));
                studentDTO.setGrade(rs.getString(5));
                studentDTO.setGender(rs.getString(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return studentDTO;
    }
    public static boolean savStudent(StudentDTO student) throws SQLException, ClassNotFoundException {

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setStudentId(student.getStuid());
        registrationDTO.setClassId(student.getCls());
        registrationDTO.setRegFees(800.00);
        registrationDTO.setRemark("");
        registrationDTO.setDate(LocalDate.now().toString());

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean save = save(student);
            if (save) {
                boolean save1 = RegistrationModel.saveRegistration(registrationDTO);
                if (save1) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean save(StudentDTO student){
        Connection connection;
        try {
            connection= DBConnection.getInstance().getConnection();;
            PreparedStatement ps=connection.prepareStatement("insert into student values(?,?,?,?,?,?)");
            ps.setString(1, student.getStuid());
            ps.setString(2,student.getCls());
            ps.setString(3,student.getName());
            ps.setString(4,student.getEmail());
            ps.setString(5,student.getGrade());
            ps.setString(6,student.getGender());
            int aff=ps.executeUpdate();
            return aff>0;
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<StudentDTO> getStudentCount(String cid, String grade){
        ArrayList<StudentDTO> ar=new ArrayList<>();
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement ps=connection.prepareStatement("select * from student where class_id =? and grade = ?");
            ps.setString(1,cid);
            ps.setString(2,grade);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                StudentDTO st=new StudentDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                ar.add(st);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return ar;

    }
    public static ArrayList<StudentDTO> getAllStudents(){
        ArrayList<StudentDTO> ar=new ArrayList<>();
        try {
            Connection connection=DBConnection.getInstance().getConnection();
            PreparedStatement ps=connection.prepareStatement("select * from student");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                StudentDTO st=new StudentDTO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                ar.add(st);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return ar;
    }

    public static String generateID() throws SQLException, ClassNotFoundException {
       return IDGenerator.generateID("student", "ST-", 8);
    }

    public static StudentDTO searchById(String studentId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        StudentDTO student = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM student WHERE StudentID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new StudentDTO();
                student.setStuid(resultSet.getString("StudentID"));
                student.setCls(resultSet.getString("class"));
                student.setName(resultSet.getString("Name"));
                student.setEmail(resultSet.getString("email"));
                student.setGrade(resultSet.getString("Grade"));
                student.setGender(resultSet.getString("gender"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return student;
    }

}

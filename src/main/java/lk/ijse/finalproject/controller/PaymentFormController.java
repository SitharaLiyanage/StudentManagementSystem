package lk.ijse.finalproject.controller;

import com.jfoenix.controls.JFXButton;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.finalproject.DB.DBConnection;
import lk.ijse.finalproject.DTO.ClassDTO;
import lk.ijse.finalproject.DTO.PaymentDTO;
import lk.ijse.finalproject.DTO.StudentDTO;
import lk.ijse.finalproject.model.Classmodel;
import lk.ijse.finalproject.model.PaymentModel;
import lk.ijse.finalproject.model.Studentmodel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class PaymentFormController implements Initializable {

    @FXML
    private TextField amount;

    @FXML
    private Button printDetails;


    @FXML
    private ComboBox<String> classIDCombo;

    @FXML
    private JFXButton saveButton;

    @FXML
    private TextField studentEmail;
    @FXML
    private TextField studentName;
    private String studentID;


    @FXML
    void onSaveClick(ActionEvent event) {
        String classIDComboValue = classIDCombo.getValue();
        String amountText = amount.getText();
        LocalDate now = LocalDate.now();
        String date = now.toString();
        System.out.println(studentID);
        PaymentDTO paymentDTO = new PaymentDTO(1,studentID,Double.parseDouble(amountText),date);
        boolean b = PaymentModel.savePayment(paymentDTO);
        if (b){
            new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            amount.setText("");
            studentName.setText("");
            studentEmail.setText("");

        }

    }
    @FXML
    void onEmailEnter(ActionEvent event) {

    }

    @FXML
    void onEmailENteerClick(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            String text = studentEmail.getText();
            StudentDTO studentDetails = Studentmodel.getStudentDetails(text);
            studentID = studentDetails.getStuid();
            studentName.setText(studentDetails.getName());
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searching();
        ArrayList<ClassDTO> allClasses = Classmodel.getAllClasses();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (int i = 0; i < allClasses.size(); i++) {
            observableList.add(allClasses.get(i).getClassID());
        }
        classIDCombo.setItems(observableList);
    }

    private void searching() {
        ArrayList<StudentDTO> allStudents = Studentmodel.getAllStudents();
        Set<String> studentlistDTO = new HashSet<>();
        allStudents.forEach(StudentDTO -> {
            studentlistDTO.add(StudentDTO.getEmail());
        });

        TextFields.bindAutoCompletion(studentEmail, studentlistDTO);

    }
    @FXML
    void printDetailsOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, JRException {
        InputStream reportsAsStream = getClass().getResourceAsStream("/report/projectTutorPaaymentReportA4.jrxml ");
        JasperDesign load = JRXmlLoader.load(reportsAsStream);
        JasperReport jasperReport1 = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport1,
                null,
                DBConnection.getInstance().getConnection()

        );
        JasperViewer.viewReport(jasperPrint, false);

    }

}

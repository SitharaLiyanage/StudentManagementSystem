package lk.ijse.finalproject.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import lk.ijse.finalproject.DTO.ClassDTO;
import lk.ijse.finalproject.DTO.RegistrationDTO;
import lk.ijse.finalproject.DTO.StudentDTO;
import lk.ijse.finalproject.model.Classmodel;
import lk.ijse.finalproject.model.RegistrationModel;
import lk.ijse.finalproject.model.Studentmodel;

import java.util.ArrayList;
import java.util.Optional;

public class RegisterStudentForController {
    public JFXButton savebutton;
    public ComboBox<ClassDTO> cmbClasses;
    public TextField txtStudentId;
    public TextField txtStudentName;
    public TextField txtRegistrationFees;
    public DatePicker txtDate;
    public TextArea txtRemarks;

    public void initialize() {
        ArrayList<ClassDTO> allClasses = Classmodel.getAllClasses();
        cmbClasses.getItems().addAll(allClasses);
        cmbClasses.setConverter(new StringConverter<ClassDTO>() {
            @Override
            public String toString(ClassDTO classDTO) {
                return classDTO==null?"":classDTO.getName();
            }

            @Override
            public ClassDTO fromString(String s) {
                return null;
            }
        });
    }

    public void onsaveclick(ActionEvent event) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setClassId(cmbClasses.getValue().getClassID());
        registrationDTO.setStudentId(txtStudentId.getText());
        registrationDTO.setRegFees(Double.parseDouble(txtRegistrationFees.getText()));
        registrationDTO.setRemark(txtRemarks.getText());
        registrationDTO.setDate(txtDate.getValue().toString());

        boolean flag = RegistrationModel.saveRegistration(registrationDTO);
        if (flag) {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Registration Done Do You Want To Register More", ButtonType.YES, ButtonType.NO).showAndWait();
            if(buttonType.isPresent()&& buttonType.get().equals(ButtonType.YES)){
                txtStudentId.setText("");
                txtStudentName.setText("");
                txtRegistrationFees.setText("");
                txtRemarks.setText("");
                txtDate.setValue(null);
            }else {
                Stage stage = (Stage) savebutton.getScene().getWindow();
                stage.close();
            }

        }else {
            new Alert(Alert.AlertType.ERROR, "Student Already Registered").show();
        }

    }

    public void btnCloseOnAction(ActionEvent event) {
        Stage window = (Stage) txtStudentName.getScene().getWindow();
        window.close();
    }

    public void txtStudentIdOnAction(ActionEvent event) {
        StudentDTO studentDTO = Studentmodel.searchById(txtStudentId.getText());
        if (studentDTO == null) {
            txtStudentName.setText("");
            new Alert(Alert.AlertType.WARNING, "No Data Found").show();
            return;
        }
        txtStudentName.setText(studentDTO.getName());
    }
}

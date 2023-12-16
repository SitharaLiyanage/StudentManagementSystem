package lk.ijse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.finalproject.DTO.StudentDTO;
import lk.ijse.finalproject.model.Studentmodel;

public class DeleteStudentFormController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField gradeField;

    @FXML
    private Button removeStudent;

    @FXML
    private TextField nameField;

    @FXML
    private TextField getDeleteStudent;

    @FXML
    void deleteStudentOnAction(ActionEvent event) {
        String id = getDeleteStudent.getText();
        StudentDTO studetnDetails = Studentmodel.getStudentDetails(id);
        nameField.setText(studetnDetails.getName());
        gradeField.setText(studetnDetails.getGrade());
        emailField.setText(studetnDetails.getEmail());
        getDeleteStudent.clear();

    }

    @FXML
    void removeStudentOnAction(ActionEvent event) {
        String fieldText = emailField.getText();
        boolean b = Studentmodel.deleteStudent(fieldText);
        if (b) {
            nameField.clear();
            gradeField.clear();
            emailField.clear();
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
            Stage stage = (Stage) removeStudent.getScene().getWindow();
            stage.close();

        }

    }

}
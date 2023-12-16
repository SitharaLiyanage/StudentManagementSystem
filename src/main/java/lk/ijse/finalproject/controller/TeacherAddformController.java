package lk.ijse.finalproject.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lk.ijse.finalproject.DTO.StudentDTO;
import lk.ijse.finalproject.DTO.TeacherDTO;
import lk.ijse.finalproject.model.Studentmodel;
import lk.ijse.finalproject.model.Teachermodel;
import lk.ijse.finalproject.util.Regex;
import lk.ijse.finalproject.util.Subjects;
import lk.ijse.finalproject.util.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherAddformController implements Initializable {

    public ComboBox<Subjects> cmbSub;
    @FXML
    private TextField id;

    @FXML
    private TextField email;

    @FXML
    private RadioButton female;

    @FXML
    private RadioButton male;

    @FXML
    private TextField name;

    @FXML
    private TextField number;

    @FXML
    private JFXButton savebutton;

    @FXML
    private TextField subject;
    @FXML
    private Label lable;

    @FXML
    void onsaveclick(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!validateAll()){
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
            return;
        }
        String a = id.getText();
        String b = name.getText();
        String c = email.getText();
        String d ;
        if (male.isSelected()){
            d = "Male";
        }else{
            d = "Female";
        }
        String e = number.getText();
        String f = cmbSub.getSelectionModel().getSelectedItem().toString();
        TeacherDTO teacherDTO = new TeacherDTO(a,b,c,d,e,f);
        boolean is = Teachermodel.savTeacher(teacherDTO);
        if (is){
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Saved Success Do You Want To Add More", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
                id.setText(Teachermodel.generateTeacherId());
                name.clear();
                email.clear();
                number.clear();
                subject.clear();
            }else {
                Stage stage = (Stage) savebutton.getScene().getWindow();
                stage.close();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            id.setText(Teachermodel.generateTeacherId());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbSub.getItems().addAll(Subjects.values());
    }

    public void txtNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.NAME, name);
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.EMAIL, email);
    }

    public void txtIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.ID, id);
    }

    public void txtMobileOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFields.PHONE, number);
    }

    public boolean validateAll(){
        return Regex.setTextColor(TextFields.NAME, name)
                && Regex.setTextColor(TextFields.EMAIL, email)
                && Regex.setTextColor(TextFields.ID, id)
                && Regex.setTextColor(TextFields.PHONE, number)
                && (male.isSelected() || female.isSelected())
                && cmbSub.getSelectionModel().getSelectedItem() != null;
    }

}

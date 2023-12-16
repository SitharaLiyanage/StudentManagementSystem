package lk.ijse.finalproject.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.finalproject.DTO.ClassDTO;
import lk.ijse.finalproject.DTO.TeacherDTO;
import lk.ijse.finalproject.model.Classmodel;
import lk.ijse.finalproject.model.Teachermodel;
import lk.ijse.finalproject.util.Subjects;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class addClassController implements Initializable {

    public TextField txtName;
    public ComboBox<Subjects> cmbSubjects;
    @FXML
    private TextField C_id;

    @FXML
    private TextField grade;

    @FXML
    private Label lable;

    @FXML
    private JFXButton savebutton;


    @FXML
    private TextField teachername;
    @FXML
    private JFXComboBox combo;

    @FXML
    void onsaveclick(ActionEvent event) {
        String a = C_id.getText();
        String b = txtName.getText();
        String c = cmbSubjects.getSelectionModel().getSelectedItem().toString();
        String d = grade.getText();

        ClassDTO classDTO =new ClassDTO(a,b,c,d);
        boolean is = Classmodel.savClass(classDTO);
        if (is){
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Saved Success Do You Want To Add More", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.isPresent()){
                if (buttonType.get().equals(ButtonType.YES)){
                    C_id.clear();
                    txtName.clear();
                    cmbSubjects.getSelectionModel().clearSelection();
                    grade.clear();
                }else {
                    Stage stage = (Stage) savebutton.getScene().getWindow();
                    stage.close();
                }
            }
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*ArrayList<TeacherDTO> allTeachers = Teachermodel.getAllTeachers();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (int i = 0; i < allTeachers.size(); i++) {
            list.add(allTeachers.get(i).getName());
        }
        combo.setItems(list);
        ArrayList<ClassDTO> ar =  Classmodel.getAllClasses();
        if (ar.size() > 0){
            lable.setText(ar.get(ar.size()-1).getClassID());
        }*/
        try {
            C_id.setText(Classmodel.generateNextId());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        cmbSubjects.setItems(FXCollections.observableArrayList(Subjects.values()));
    }


    public void btnCloseOnAction(ActionEvent event) {
        Stage stage = (Stage) savebutton.getScene().getWindow();
        stage.close();
    }
}

package lk.ijse.finalproject.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.finalproject.DB.DBConnection;
import lk.ijse.finalproject.DTO.StudentDTO;
import lk.ijse.finalproject.Main;
import lk.ijse.finalproject.model.Studentmodel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentformController implements Initializable {

    @FXML
    private Button studentReport;


    @FXML
    private Button deleteStudentButton;

    @FXML
    private Button addstudentbutton;
    @FXML
    private TableColumn<StudentDTO, String> classcoloum;



    @FXML
    private TableColumn<StudentDTO,String> emailcolumn;

    @FXML
    private TableColumn<StudentDTO, String> gendercolumn;

    @FXML
    private TableColumn<StudentDTO, String> gradecolumn;

    @FXML
    private TableColumn<StudentDTO, String> idcolumn;

    @FXML
    private TableColumn<StudentDTO, String> namecolumn;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private TableView<StudentDTO> tableStudent;
    @FXML
    private GridPane gridpane;
    @FXML
    private Button searchbutton;

    @FXML
    private TextField textsearch;
    @FXML
    private AnchorPane pane;
    @FXML
    void pnsearchClick(ActionEvent event) {
        String x = textsearch.getText();
        ArrayList<StudentDTO> ar=Studentmodel.getAllStudents();

    }
    @FXML
    void onDeleteStudentclick(ActionEvent event) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/deleteStudentform.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 808, 599);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

    }


    @FXML
    void onaddstudentclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/addStudentform.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 808, 599);
        Stage stage=new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        ArrayList<StudentDTO> ar= Studentmodel.getAllStudents();
        ObservableList<StudentDTO> observableList = FXCollections.observableArrayList();
        setValues();
        observableList.addAll(ar);
        tableStudent.setItems(observableList);
    }

    private void setValues() {
        idcolumn.setCellValueFactory(new PropertyValueFactory<StudentDTO, String>("stuid"));
        classcoloum.setCellValueFactory(new PropertyValueFactory<StudentDTO, String>("cls"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<StudentDTO, String>("name"));
        emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentDTO, String>("email"));
        gradecolumn.setCellValueFactory(new PropertyValueFactory<StudentDTO, String>("grade"));
        gendercolumn.setCellValueFactory(new PropertyValueFactory<StudentDTO, String>("gender"));
    }

    @FXML
    void studentReportOnAction(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {
        InputStream reportsAsStream = getClass().getResourceAsStream("/report/allStudents_A4.jrxml");
        JasperDesign load = JRXmlLoader.load(reportsAsStream);
        JasperReport jasperReport1 = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport1,
                null,
                DBConnection.getInstance().getConnection()

        );
        JasperViewer.viewReport(jasperPrint, false);

    }

    public void refreshOnaction(ActionEvent event) {
        loadData();
    }
}

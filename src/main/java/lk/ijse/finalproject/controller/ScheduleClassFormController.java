package lk.ijse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import lk.ijse.finalproject.DTO.*;
import lk.ijse.finalproject.model.ClassScheduleModel;
import lk.ijse.finalproject.model.Classmodel;
import lk.ijse.finalproject.model.HallModel;
import lk.ijse.finalproject.model.Teachermodel;
import lk.ijse.finalproject.util.MailSender;

import java.time.LocalDate;
import java.util.List;

public class ScheduleClassFormController {
    public ComboBox<ClassDTO> cmbClass;
    public ComboBox<TeacherDTO> cmbTeacher;
    public ComboBox<HallDTO> cmbHall;
    public DatePicker txtDate;
    public TextField txtTime;
    public TextArea txtEmail;
    public TableView<StudentDTO> tblStudents;
    public TableColumn<StudentDTO, String> colStudentName;
    public TableColumn<StudentDTO, String> colEmail;

    public void initialize() {
        setData();
        setConverters();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void setData() {
        cmbClass.setItems(FXCollections.observableArrayList(Classmodel.getAllClasses()));
        cmbTeacher.setItems(FXCollections.observableArrayList(Teachermodel.getAllTeachers()));
        cmbHall.setItems(FXCollections.observableArrayList(HallModel.getAllHalls()));
    }

    public void setConverters() {
        cmbClass.setConverter(new StringConverter<ClassDTO>() {
            @Override
            public String toString(ClassDTO classDTO) {
                return classDTO == null ? "" : classDTO.getName();
            }

            @Override
            public ClassDTO fromString(String s) {
                return null;
            }
        });

        cmbTeacher.setConverter(new StringConverter<TeacherDTO>() {
            @Override
            public String toString(TeacherDTO teacherDTO) {
                return teacherDTO == null ? "" : teacherDTO.getName();
            }

            @Override
            public TeacherDTO fromString(String s) {
                return null;
            }

        });

        cmbHall.setConverter(new StringConverter<HallDTO>() {
            @Override
            public String toString(HallDTO hallDTO) {
                return hallDTO == null ? "" : hallDTO.getName();
            }

            @Override
            public HallDTO fromString(String s) {
                return null;
            }

        });

    }


    public void cmbClassOnAction(ActionEvent event) {
        try {
            List<StudentDTO> studentsByClassID = ClassScheduleModel.getStudentsByClassID(cmbClass.getSelectionModel().getSelectedItem().getClassID());
            tblStudents.setItems(FXCollections.observableArrayList(studentsByClassID));
            generateClassMessage();
        } catch (Exception e) {
            //   e.printStackTrace();
        }

    }

    public void cmbTeacherOnAction(ActionEvent event) {
        try {
            generateClassMessage();
        } catch (Exception e) {
            //  e.printStackTrace();
        }
    }

    public void cmbHallOnAction(ActionEvent event) {
        try {
            generateClassMessage();
        } catch (Exception e) {
            //  e.printStackTrace();
        }
    }

    public void txtDateOnAction(ActionEvent event) {
        try {
            generateClassMessage();
        } catch (Exception e) {
            //  e.printStackTrace();
        }
    }

    public void txtTimeOnKeyReleased(KeyEvent keyEvent) {
        try {
            generateClassMessage();
        } catch (Exception e) {
            //  e.printStackTrace();
        }
    }

    public void btnSheduleClassOnAction(ActionEvent event) {
        if (!validateData()) {
            new Alert(Alert.AlertType.WARNING, "Please select all values").show();
            return;
        }
        ClassScheduleDTO classScheduleDTO = collectData();
        if (ClassScheduleModel.saveClassSchedule(classScheduleDTO)) {
            new Alert(Alert.AlertType.INFORMATION, "Class scheduled successfully").show();
            sendMails();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public String generateClassMessage() {
        // Get selected values from the UI components
        ClassDTO selectedClass = cmbClass.getValue();
        TeacherDTO selectedTeacher = cmbTeacher.getValue();
        HallDTO selectedHall = cmbHall.getValue();
        LocalDate selectedDate = txtDate.getValue();
        String selectedTime = txtTime.getText();
        String emailMessage = txtEmail.getText();

        // Construct the message using the selected details
        String message = "Dear students,\n\n";
        message += "A new class has been scheduled:\n\n";
        message += "Class: " + selectedClass.getName() + "\n";
        message += "Teacher: " + selectedTeacher.getName() + "\n";
        message += "Hall: " + selectedHall.getName() + "\n";
        message += "Date: " + selectedDate.toString() + "\n";
        message += "Time: " + selectedTime + "\n\n";
        message += "Please make sure to attend this class.\n\n";
        message += "Best regards,\n";
        message += "Sakya";

        // Optionally, you can set the email message if needed
        txtEmail.setText(message);

        return message;
    }

    public boolean validateData() {
        return cmbClass.getSelectionModel().getSelectedItem() != null &&
                cmbTeacher.getSelectionModel().getSelectedItem() != null &&
                cmbHall.getSelectionModel().getSelectedItem() != null &&
                txtDate.getValue() != null &&
                txtTime.getText() != null;
    }


    public ClassScheduleDTO collectData() {
        ClassScheduleDTO classScheduleDTO = new ClassScheduleDTO();
        classScheduleDTO.setClassID(cmbClass.getValue().getClassID());
        classScheduleDTO.setTutorID(cmbTeacher.getValue().getId());
        classScheduleDTO.setHallID(cmbHall.getValue().getHallID());
        classScheduleDTO.setDate(txtDate.getValue().toString());
        classScheduleDTO.setTime(txtTime.getText());
        return classScheduleDTO;
    }

    public void sendMails() {
        for (StudentDTO item : tblStudents.getItems()) {
            new Thread(() -> {
                new MailSender().sendEmail(item.getEmail(), "Class Schedule", txtEmail.getText());
            }).start();
        }
    }

}

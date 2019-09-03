package com.defaultName.defaultPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AddGuestController implements Initializable {

    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private RadioButton radioMale;
    @FXML
    private RadioButton radioFemale;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ComboBox<String> comboDocType;
    @FXML
    private TextField txtDocNo;
    @FXML
    private TextField txtNationality;
    @FXML
    private TextField txtPlaceOfBirth;

    private static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboDocType.setValue("Passport");
        comboDocType.getItems().addAll("Passport","ID card","Drivers Licence");
    }

    public void display(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("add_guest_layout.fxml"));
            stage = new Stage();
            stage.setTitle("Add New Guest");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void btnAddClicked(ActionEvent event) {
        if(verify()) {
            Guest guest = new Guest();
            String fName = txtFName.getText();
            String lName = txtLName.getText();
            String sex = "Male";
            if (radioFemale.isSelected()) sex = "Female";
            String bDate = arrangeDate();
            String dType = comboDocType.getValue();
            String dNo = txtDocNo.getText();
            String nationality = txtNationality.getText();
            String bPlace = txtPlaceOfBirth.getText();

            guest.insertUpdateDelete('i', null, fName, lName, sex, bDate, dType, dNo, nationality, bPlace);
        }
    }
    @FXML
    void btnCancelClicked(ActionEvent event) {
        stage.close();
    }

    private boolean verify(){
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(arrangeDate());
            if(date.after(new Date())){
                alert("Birth date cant be greater than current date!");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(txtFName.getText().isEmpty() || txtLName.getText().isEmpty() ||
                birthDatePicker.getEditor().getText().isEmpty() || comboDocType.getValue().isEmpty() ||
                txtDocNo.getText().isEmpty() || txtNationality.getText().isEmpty() ||
                txtPlaceOfBirth.getText().isEmpty()){
            alert("All fields must be filled!");
            return false;
        }
        else return true;

    }

    private String arrangeDate(){
        return birthDatePicker.getValue().getDayOfMonth()+"/"+birthDatePicker.getValue().getMonthValue()+
                "/"+birthDatePicker.getValue().getYear();
    }

    private void alert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.show();
    }

}
package com.defaultName.defaultPackage;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class Guest {
    public void insertUpdateDelete(char operation, Integer id, String fName, String lName, String sex,
                            String birthDate, String docType, String docNo, String nationality, String birthPlace){

        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        if(operation == 'i'){
            try{
                ps = con.prepareStatement("INSERT INTO guest(firstName, lastName, sex, dateOfBirth, typeOfDoc, docNo, nationality, placeOfBirth) VALUES (?,?,?,?,?,?,?,?)");
                ps.setString(1,fName);
                ps.setString(2,lName);
                ps.setString(3,sex);
                ps.setString(4,birthDate);
                ps.setString(5,docType);
                ps.setString(6,docNo);
                ps.setString(7,nationality);
                ps.setString(8,birthPlace);

                if(ps.executeUpdate() > 0){
                    alert("New guest Added!","info");
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(operation == 'u'){
            if(alert("Save changes?","confirm")) {
                try {
                    ps = con.prepareStatement("UPDATE guest SET firstName = ?, lastName = ?, sex = ?, dateOfBirth = ?, typeOfDoc = ?, docNo = ?, nationality = ?, placeOfBirth = ? WHERE id = ?");
                    ps.setString(1, fName);
                    ps.setString(2, lName);
                    ps.setString(3, sex);
                    ps.setString(4, birthDate);
                    ps.setString(5, docType);
                    ps.setString(6, docNo);
                    ps.setString(7, nationality);
                    ps.setString(8, birthPlace);
                    ps.setInt(9, id);

                    if (ps.executeUpdate() > 0) {
                        alert("Guest updated!", "info");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        if(operation == 'd'){
            if(alert("Delete guest?","confirm")) {
                try {
                    ps = con.prepareStatement("DELETE FROM guest WHERE id = ?");
                    ps.setInt(1, id);

                    if (ps.executeUpdate() > 0) {
                        alert("Guest deleted!", "info");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean alert(String message, String type){

        Alert alert;
        switch (type) {
            case "confirm":
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(message);
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get() == ButtonType.OK){
                    return true;
                }
                else if(option.get() == ButtonType.CANCEL){
                    return false;
                }

            case "info":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(message);
                alert.show();
        }
        return false;
    }
}

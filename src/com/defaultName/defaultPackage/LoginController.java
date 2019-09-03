package com.defaultName.defaultPackage;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField passPassword;

    private static Stage stage;

    public void display(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login_layout.fxml"));
            stage = new Stage();
            stage.setTitle("Login Form");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(e -> System.exit(0));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnCancelClick(){
        System.exit(0);
    }
    @FXML
    private void btnLoginClick(ActionEvent event){

        if(!txtUsername.getText().isEmpty() && !String.valueOf(passPassword.getText()).isEmpty()){
            Connection con = MyConnection.getConnection();
            PreparedStatement ps;
            try {
                ps = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
                ps.setString(1, txtUsername.getText());
                ps.setString(2, String.valueOf(passPassword.getText()));

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    stage.close();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Wrong username or password");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.defaultName.defaultPackage;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController extends Application implements Initializable {

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtLName;
    @FXML
    private RadioButton radioMale;
    @FXML
    private ToggleGroup toggleGroup1;
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
    @FXML
    private TextField txtSearch;
    @FXML
    private TableView<ModelTable> tableView;
    @FXML
    private TableColumn<ModelTable, String> colId;
    @FXML
    private TableColumn<ModelTable, String> colFName;
    @FXML
    private TableColumn<ModelTable, String> colLName;
    @FXML
    private TableColumn<ModelTable, String> colSex;
    @FXML
    private TableColumn<ModelTable, String> colBDate;
    @FXML
    private TableColumn<ModelTable, String> colDType;
    @FXML
    private TableColumn<ModelTable, String> colDNo;
    @FXML
    private TableColumn<ModelTable, String> colNation;
    @FXML
    private TableColumn<ModelTable, String> colBPlace;

    private ObservableList<ModelTable> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTableView("");
        comboDocType.getItems().addAll("Passport","ID card","Drivers Licence");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main_layout.fxml"));
        primaryStage.setTitle("Guest Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        LoginController lc = new LoginController();
        lc.display();
        primaryStage.show();
    }
    @FXML
    private void addNewGuestClicked(){
        AddGuestController controller = new AddGuestController();
        controller.display();
        tableView.getItems().clear();
        fillTableView("");
    }
    @FXML
    private void btnEditClicked(){
        if(verify()) {
            Guest guest = new Guest();
            Integer id = Integer.parseInt(txtID.getText());
            String fName = txtFName.getText();
            String lName = txtLName.getText();
            String sex = "Male";
            if (radioFemale.isSelected()) sex = "Female";
            String bDate = arrangeDate();
            String dType = comboDocType.getValue();
            String dNo = txtDocNo.getText();
            String nationality = txtNationality.getText();
            String bPlace = txtPlaceOfBirth.getText();

            guest.insertUpdateDelete('u', id, fName, lName, sex, bDate, dType, dNo, nationality, bPlace);
            tableView.getItems().clear();
            fillTableView("");
        }
    }
    @FXML
    private void btnDeleteClicked(){
        Guest guest = new Guest();
        Integer id = Integer.parseInt(txtID.getText());
        guest.insertUpdateDelete('d',id,null,null,null,null,null,
                null,null,null);
        tableView.getItems().clear();
        fillTableView("");
    }
    @FXML
    private void tableMouseClicked(){

        if(!tableView.getSelectionModel().isEmpty()){

            ModelTable model = tableView.getSelectionModel().getSelectedItem();

            txtID.setText(model.getId());
            txtFName.setText(model.getFirstName());
            txtLName.setText(model.getLastName());
            if (model.getSex().equals("Male")) radioMale.setSelected(true);
            else radioFemale.setSelected(true);
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(model.getBirthDate());
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                birthDatePicker.setValue(localDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            comboDocType.setValue(model.getDocType());
            txtDocNo.setText(model.getDocNo());
            txtNationality.setText(model.getNation());
            txtPlaceOfBirth.setText(model.getBirthPlace());
        }
    }

    private void fillTableView(String valueToSearch){
        Connection con = MyConnection.getConnection();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM guest WHERE CONCAT(firstName, lastName, nationality)LIKE ?");
            ps.setString(1,"%"+valueToSearch+"%");
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                list.add(new ModelTable(
                        String.valueOf(rs.getInt(1)),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colBDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        colDType.setCellValueFactory(new PropertyValueFactory<>("docType"));
        colDNo.setCellValueFactory(new PropertyValueFactory<>("docNo"));
        colNation.setCellValueFactory(new PropertyValueFactory<>("nation"));
        colBPlace.setCellValueFactory(new PropertyValueFactory<>("birthPlace"));

        tableView.setItems(list);
    }
    @FXML
    void txtSearchTyped() {
        tableView.getItems().clear();
        fillTableView(txtSearch.getText());
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

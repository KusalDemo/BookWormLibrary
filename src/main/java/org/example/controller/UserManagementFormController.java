package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.dto.tm.UserTM;

import java.util.ArrayList;
import java.util.List;

public class UserManagementFormController {

    public TextField txtUserName;
    public TextField txtEmail;
    public ComboBox cmbBranch;
    public TextField txtSearchUser;
    public Button btnUpdate;
    public Button btnDelete;
    public TableView<UserTM> tblUsers;
    public TableColumn<?,?> colUserEmail;
    public TableColumn<?,?> colUserName;
    public TableColumn<?,?> colBranch;
    public Button btnBack;

    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    BranchBO branchBO = (BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void initialize() throws ClassNotFoundException {
        loadAllUsers();
        setCellValueFactory();
        txtEmail.setEditable(false);

        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for(BranchDto branchDto:allBranches){
            cmbBranch.getItems().add(branchDto.getBranchId()+" - "+branchDto.getBranchName());
        }

        tblUsers.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>=0){
                UserTM selectedItem = tblUsers.getItems().get(newValue.intValue());
                txtUserName.setText(selectedItem.getUserName());
                txtEmail.setText(selectedItem.getEmail());
                cmbBranch.setValue(selectedItem.getBranchId());
            }
        });
    }

    private void setCellValueFactory(){
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branchId"));
    }
    public void txtSearchUserOnAction(KeyEvent keyEvent) throws ClassNotFoundException {
        clearFields();
        List<UserDto> allUsers = userBO.getAllUsers();
        for(UserDto userDto:allUsers){
            if(userDto.getUserName().toLowerCase().equals(txtSearchUser.getText().toLowerCase())){
                txtUserName.setText(userDto.getUserName());
                txtEmail.setText(userDto.getEmail());
                cmbBranch.setValue(userDto.getBranchDto().getBranchId()+" - "+userDto.getBranchDto().getBranchName());

            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if(txtEmail.getText().isEmpty()||txtUserName.getText().isEmpty()||cmbBranch.getValue()==null){
            new Alert(Alert.AlertType.ERROR, "Please provide User Name and Branch").show();
        }else{
            String branch = (String) cmbBranch.getValue();
            String[] splittedBranchDetail = branch.split(" - ");
            String branchId = splittedBranchDetail[0];
            System.out.println(branchId);
            BranchDto branchDto = branchBO.searchBranch(branchId);
            try{
                boolean isSaved = userBO.updateUserMinor(new UserDto(txtUserName.getText(), txtEmail.getText(), null, branchDto));
                if(isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "User Updated Successfully").show();
                    clearFields();
                    loadAllUsers();
                }else{
                    new Alert(Alert.AlertType.ERROR, "User Not Updated").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println(e.getMessage());
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

    }
    private void loadAllUsers(){
        ObservableList<UserTM> obList = FXCollections.observableArrayList();

        try{
            List<UserDto> allUsers = userBO.getAllUsers();
            for(UserDto userDto:allUsers){
                String branch=userDto.getBranchDto().getBranchId()+" - "+userDto.getBranchDto().getBranchName();
                obList.add(new UserTM(userDto.getUserName(),userDto.getEmail(),branch));
            }
            tblUsers.setItems(obList);
            tblUsers.refresh();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e.getMessage());
        }
    }
    private void clearFields(){
        txtUserName.clear();
        txtEmail.clear();
        cmbBranch.getSelectionModel().clearSelection();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnBack.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Dashboard");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

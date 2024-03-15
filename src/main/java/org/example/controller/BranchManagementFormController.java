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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BookDto;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.dto.tm.BranchTM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BranchManagementFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtLocation;
    public TextField txtEmail;
    public TableView<BranchTM> tblBranch;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colLocation;
    public TableColumn<?,?> colEmail;
    public Button btnBack;
    public TextField txtSearch;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Label lblNotice;

    BranchBO branchBO=(BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);
    BookBO bookBO=(BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    UserBO userBO=(UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    public void initialize() throws ClassNotFoundException {
        if(LoginFormController.whoIsLogged=="User"){
            btnSave.setVisible(false);
            btnUpdate.setVisible(false);
            btnDelete.setVisible(false);
            lblNotice.setText("Search to find branches and get details of them");
            txtId.setEditable(false);
            txtEmail.setEditable(false);
            txtLocation.setEditable(false);
            txtName.setEditable(false);
        }
        loadAllBranches();
        setCellValueFactory();
        txtId.setEditable(false);

        tblBranch.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>=0){
                BranchTM selectedItem = tblBranch.getItems().get(newValue.intValue());
                txtId.setText(selectedItem.getBranchId());
                txtName.setText(selectedItem.getBranchName());
                txtLocation.setText(selectedItem.getLocation());
                txtEmail.setText(selectedItem.getEmail());
            }
        });
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("branchId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void txtIdOnAction(MouseEvent mouseEvent) {
        txtId.setText(generateBranchId());
    }
    public void txtNameOnAction(MouseEvent mouseEvent) {txtId.setText(generateBranchId());}

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(txtId.getText().equals("")||txtName.getText().equals("")||txtLocation.getText().equals("")||txtEmail.getText().equals("")){
            new Alert(Alert.AlertType.ERROR, "Please provide all the details").show();
        }else{
            try {
                boolean isSaved = branchBO.saveBranch(new BranchDto(txtId.getText(), txtName.getText(), txtLocation.getText(), txtEmail.getText()));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Branch Saved").show();
                    loadAllBranches();
                    clearFields();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Branch Not Saved").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println(e.getMessage());
            }
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        /*List<String> allUsersEmails = userBO.getAllUsers().stream()
                .map(UserDto::getEmail)
                .collect(Collectors.toList());
        List<String> allBookIds = bookBO.getAllBooks().stream()
                .map(BookDto::getId)
                .collect(Collectors.toList());*/
        if(!txtName.getText().equals("")){
            try {
                boolean isUpdated = branchBO.updateBranch(new BranchDto(txtId.getText(), txtName.getText(), txtLocation.getText(), txtEmail.getText()));
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Branch Updated").show();
                    loadAllBranches();
                    clearFields();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Branch Not Updated, Check Again").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println(e.getMessage());
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(!txtId.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want to delete? All related data will also be deleted.");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    try {
                        boolean isDeleted = branchBO.deleteBranch(txtId.getText());
                        if (isDeleted) {
                            new Alert(Alert.AlertType.INFORMATION, "Branch Deleted").show();
                            loadAllBranches();
                            clearFields();
                        }else{
                            new Alert(Alert.AlertType.ERROR, "Branch Not Deleted, Check Again").show();
                        }
                    }catch (Exception e){
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        System.out.println(e.getMessage());
                    }
                } else if (response == buttonTypeNo) {
                    System.out.println("User clicked No");
                }
            });
        }else{
            new Alert(Alert.AlertType.ERROR, "Please provide Branch Id").show();
        }
    }
    private String generateBranchId(){
        String id = "";
        try{
            ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
            int size = allBranches.size();
            id="BR-"+(size+1);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e.getMessage());
        }
        return id;
    }
    private void clearFields(){
        txtId.clear();
        txtName.clear();
        txtLocation.clear();
        txtEmail.clear();
    }
    private void loadAllBranches() throws ClassNotFoundException {
        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        ObservableList<BranchTM> obList = FXCollections.observableArrayList();
        for(BranchDto branch:allBranches){
            obList.add(new BranchTM(branch.getBranchId(),branch.getBranchName(),branch.getLocation(),branch.getEmail()));
        }
        tblBranch.setItems(obList);
        tblBranch.refresh();
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

    public void txtSearchOnAction(KeyEvent keyEvent) throws ClassNotFoundException {
        clearFields();
        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for(BranchDto branch:allBranches){
            if(branch.getBranchId().toLowerCase().equals(txtSearch.getText().toLowerCase())||branch.getBranchName().toLowerCase().equals(txtSearch.getText().toLowerCase())){
                txtId.setText(branch.getBranchId());
                txtName.setText(branch.getBranchName());
                txtLocation.setText(branch.getLocation());
                txtEmail.setText(branch.getEmail());
            }
        }
    }


}

package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class DashBoardFormController {

    public AnchorPane bookManagePane;
    public AnchorPane userManagePane;
    public AnchorPane borrowBookManagePane;
    public Button btnLogOut;
    public Label lblLoggedPersonName;
    public AnchorPane branchManagePane;
    public AnchorPane viewBooksPane;
    public Label lblManageUser;
    public Label lblViewBooksStatus;
    public Label lblBorrowBookManage;

    public void initialize() {
        if(LoginFormController.whoIsLogged=="User"){
            userManagePane.setVisible(false);
            viewBooksPane.setVisible(false);
            lblManageUser.setVisible(false);
            lblViewBooksStatus.setVisible(false);
        }else{
            borrowBookManagePane.setVisible(false);
            lblBorrowBookManage.setVisible(false);
        }
        String loggedPerson = LoginFormController.loggedPerson;
        lblLoggedPersonName.setText(loggedPerson);
    }

    public void userManagePaneOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/userManagement_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) userManagePane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("User Management");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }


    public void bookManagePaneOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/BookManagement_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) bookManagePane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Book Management");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void branchManagePaneOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/BranchManagement_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) branchManagePane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Branch Management");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void borrowBookManagePaneOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/BorrowBooksManagement_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) borrowBookManagePane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Let's Borrow and Read Books");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void viewBorrowedBooksPaneOnAction(MouseEvent mouseEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/viewBorrowedBooks_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) viewBooksPane.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Find Not Returned Books");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you want to log out?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnLogOut.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("Let's Login");
                    stage1.centerOnScreen();

                    new FadeIn(root).play();
                }catch (Exception e){
                    System.out.println(e);
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User said Don't want to log out");
            }
        });
    }


}

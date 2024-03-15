package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.AdminBO;
import org.example.bo.custom.UserBO;
import org.example.dto.AdminDto;
import org.example.dto.UserDto;

import java.io.IOException;
import java.util.List;


public class LoginFormController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public Button btnLogin;
    public Button btnSignUp;
    public ComboBox cmbRole;

    public static String loggedPerson;
    public static UserDto currentUserDto;

    public static AdminDto currentAdminDto;
    public static String whoIsLogged;
    AdminBO adminBO=(AdminBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.ADMIN);
    UserBO userBO=(UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);

    public void initialize(){
        cmbRole.getItems().addAll("User", "Admin");
        cmbRole.getSelectionModel().select(0);
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
       /* List<UserDto> allUsers = userBO.getAllUsers();
        for (UserDto userDto : allUsers) {
            System.out.println(userDto.getEmail());
            if (userDto.getEmail().equals(txtEmail.getText()) && userDto.getPassword().equals(txtPassword.getText())) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                Scene scene1 = new Scene(root);
                Stage stage1 = (Stage) btnLogin.getScene().getWindow();
                stage1.setScene(scene1);
                stage1.setTitle("DASHBOARD");
                stage1.centerOnScreen();
            }else{
                new Alert(Alert.AlertType.ERROR, "Invalid Email or Password").show();
            }*/
        Boolean isFoundInDB = false;
        if (cmbRole.getValue().equals("User")) {
            try {
                List<UserDto> allUsers = userBO.getAllUsers();
                for (UserDto userDto : allUsers) {
                    System.out.println(userDto.getEmail());
                    if (userDto.getEmail().equals(txtEmail.getText()) && userDto.getPassword().equals(txtPassword.getText())) {
                        isFoundInDB = true;
                        loggedPerson=userDto.getUserName();
                        currentUserDto=userDto;
                        whoIsLogged="User";
                    }
                }
                if(isFoundInDB == true){
                    Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnLogin.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("DASHBOARD");
                    stage1.centerOnScreen();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid Email or Password").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println("User Error bng");
                System.out.println(e.getMessage());
            }
        } else {
            try {
               /* List<AdminDto> allAdmins = adminBO.getAllAdmins();
                for(AdminDto searchedAdmin : allAdmins){
                    if(searchedAdmin.getEmail().equals(txtEmail.getText()) && searchedAdmin.getPassword().equals(txtPassword.getText())){
                        isFoundInDB = true;
                        loggedPerson=searchedAdmin.getUsername();
                    }
                }*/
                AdminDto admin = adminBO.getAdmin(txtEmail.getText());
                if (admin.getEmail().equals(txtPassword.getText())) {
                    isFoundInDB = true;
                    loggedPerson = admin.getUsername();
                    currentAdminDto = admin;
                    whoIsLogged = "Admin";
                }
                if (isFoundInDB == true) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnLogin.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("DASHBOARD");
                    stage1.centerOnScreen();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Email or Password").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println("Admin Error bng");
                System.out.println(e.getMessage());
            }
        }
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/signup_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnSignUp.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("SIGN UP FORM");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}

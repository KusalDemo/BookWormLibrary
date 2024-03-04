package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.Util.Regex;
import org.example.bo.BoFactory;
import org.example.bo.custom.AdminBO;
import org.example.bo.custom.UserBO;
import org.example.dto.AdminDto;
import org.example.dto.UserDto;

public class SignUpFormController {

    public TextField txtUserName;
    public PasswordField txtRePassword;
    public PasswordField txtPassword;
    public Button btnAlreadyHaveAnAccount;
    public Button btnSignUp;
    public TextField txtEmail;

    AdminBO adminBO=(AdminBO)BoFactory.getBoFactory().getBO(BoFactory.BOType.ADMIN);
    UserBO userBO=(UserBO)BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        String userName=txtUserName.getText();
        String email=txtEmail.getText();
        String password=txtPassword.getText();
        String rePassword=txtRePassword.getText();

        if(Regex.isEmailValid(email)&&Regex.isPasswordValid(password)){
            if(password.equals(rePassword)){
                UserDto userDto = new UserDto(userName, email, password);
                try {
                    if(userBO.saveUser(userDto)){
                        new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful").show();
                        clearFields();
                        btnAlreadyHaveAnAccountOnAction(actionEvent);
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
                    }
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    System.out.println(e.getMessage());
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Email or Password").show();
        }
    }

    public void btnAlreadyHaveAnAccountOnAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) btnAlreadyHaveAnAccount.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("LOGIN FORM");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    private void clearFields(){
        txtUserName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtRePassword.clear();
    }
}

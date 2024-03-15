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

public class PasswordChangeFormController {

    public TextField txtOTP;
    public PasswordField txtPassword;
    public PasswordField txtRePassword;
    public Button btnChangePassword;
    public Button btnBack;

    AdminBO adminBO = (AdminBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.ADMIN);

    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);

    public void btnChangePasswordOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if (txtOTP.getText().equals(LoginFormController.otp)) {
            if (LoginFormController.passwordChangeRequestedAdminDto != null) {
                AdminDto adminDto = LoginFormController.passwordChangeRequestedAdminDto;
                boolean isPasswordChanged1 = adminBO.updatePassword(adminDto.getAdminId(), txtPassword.getText());
                if (isPasswordChanged1) {
                    new Alert(Alert.AlertType.INFORMATION, "Password Changed Successfully").show();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
                        Scene scene1 = new Scene(root);
                        Stage stage1 = (Stage) btnChangePassword.getScene().getWindow();
                        stage1.setScene(scene1);
                        stage1.setTitle("LOGIN");
                        stage1.centerOnScreen();
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } else if (LoginFormController.passwordChangeRequestedUserDto != null) {
                    if (LoginFormController.passwordChangeRequestedUserDto != null) {
                        UserDto userDto = LoginFormController.passwordChangeRequestedUserDto;
                        boolean isPasswordChanged = userBO.updatePassword(userDto.getEmail(), txtPassword.getText());
                        if (isPasswordChanged) {
                            new Alert(Alert.AlertType.INFORMATION, "Password Changed Successfully").show();
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
                                Scene scene1 = new Scene(root);
                                Stage stage1 = (Stage) btnChangePassword.getScene().getWindow();
                                stage1.setScene(scene1);
                                stage1.setTitle("LOGIN");
                                stage1.centerOnScreen();

                                new FadeIn(root).play();
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    }
                }
            }
        }
    }
    public void btnBackOnAction(ActionEvent actionEvent){
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
                    Stage stage1 = (Stage) btnBack.getScene().getWindow();
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

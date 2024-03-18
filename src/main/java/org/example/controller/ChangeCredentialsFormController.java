package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Util.Regex;
import org.example.bo.BoFactory;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;

import java.io.IOException;

public class ChangeCredentialsFormController {

    public TextField txtUserName;
    public PasswordField txtPassword;
    public PasswordField txtRePassword;
    public Button btnChangePassword;
    public Button btnBack;

    public UserDto userDto=LoginFormController.currentUserDto;
    public Button btnChangeName;
    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    BranchBO branchBO = (BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void btnChangePasswordOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if(Regex.isPasswordValid(txtPassword.getText())){
            if(txtPassword.getText().equals(txtRePassword.getText())){
                boolean isPasswordChanged = userBO.updatePassword(userDto.getUserName(), txtPassword.getText());
                if(isPasswordChanged){
                    new Alert(Alert.AlertType.INFORMATION, "Password Changed Successfully").show();
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                        Scene scene1 = new Scene(root);
                        Stage stage1 = (Stage) btnChangePassword.getScene().getWindow();
                        stage1.setScene(scene1);
                        stage1.setTitle("Dashboard");
                        stage1.centerOnScreen();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Password does not match").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Password, Password must be at least 8 characters and must contain at least one uppercase letter, one lowercase letter, one digit, and one special character").show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
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

    public void btnChangeNameOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        if(Regex.isNameValid(txtUserName.getText())){
            BranchDto branchDto = branchBO.searchBranch(userDto.getBranchDto().getBranchId());
            boolean isUserNameUpdated = userBO.updateUser(new UserDto(txtUserName.getText(),userDto.getUserName(), userDto.getPassword(), branchDto));
            if(isUserNameUpdated){
                new Alert(Alert.AlertType.INFORMATION, "Name Changed Successfully").show();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                    Scene scene1 = new Scene(root);
                    Stage stage1 = (Stage) btnChangeName.getScene().getWindow();
                    stage1.setScene(scene1);
                    stage1.setTitle("Dashboard");
                    stage1.centerOnScreen();
        }       catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
            new Alert(Alert.AlertType.ERROR, "Invalid Name, Name must be at least 3 characters").show();
        }
        }
    }
}

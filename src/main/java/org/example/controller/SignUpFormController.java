package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Util.Regex;
import org.example.bo.BoFactory;
import org.example.bo.custom.AdminBO;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.AdminDto;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SignUpFormController {

    public TextField txtUserName;
    public PasswordField txtRePassword;
    public PasswordField txtPassword;
    public Button btnAlreadyHaveAnAccount;
    public Button btnSignUp;
    public TextField txtEmail;
    public ComboBox cmbRole;
    public ComboBox cmbBranch;
    public Text lblBranch;
    public ImageView imgBranch;

    AdminBO adminBO = (AdminBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.ADMIN);
    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    BranchBO branchBO = (BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void initialize() throws ClassNotFoundException {
        cmbRole.getItems().addAll("User", "Admin");
        cmbRole.getSelectionModel().select(0);

        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for (BranchDto branchDto : allBranches) {
            cmbBranch.getItems().add(branchDto.getBranchId() + " - " + branchDto.getBranchName());
        }
        cmbBranch.getSelectionModel().select(0);


        if(cmbRole.getValue().equals("User")){
            cmbBranch.setVisible(true);
            lblBranch.setVisible(true);
            imgBranch.setVisible(true);
        }
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();
        String role = cmbRole.getValue().toString();

        String branch = cmbBranch.getValue().toString();
        String[] parts = branch.split(" - ");
        String branchId = parts[0];

        if (role.equals("Admin")) {
            if (Regex.isEmailValid(email) && Regex.isPasswordValid(password)) {
                if (password.equals(rePassword)) {
                    String adminId = AdminIdGenerator();
                    AdminDto adminDto = new AdminDto(adminId, userName, email, password);
                    try {
                        if (adminBO.saveAdmin(adminDto)) {
                            new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful").show();
                            clearFields();
                            btnAlreadyHaveAnAccountOnAction(actionEvent);
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        System.out.println(e.getMessage());
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                }
            }
        } else if (role.equals("User")) {
            if (Regex.isEmailValid(email) && Regex.isPasswordValid(password)) {
                if (password.equals(rePassword)) {
                    UserDto userDto = new UserDto(userName, email, password, branchId, null);
                    try {
                        if (userBO.saveUser(userDto)) {
                            new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful").show();
                            clearFields();
                            btnAlreadyHaveAnAccountOnAction(actionEvent);
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        System.out.println(e.getMessage());
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Email or Password").show();
            }
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void clearFields() {
        txtUserName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtRePassword.clear();
    }

    private String AdminIdGenerator() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatStyle = DateTimeFormatter.ofPattern("MMddHHmmss");
        String formattedDate = now.format(formatStyle);
        return formattedDate;
    }
}

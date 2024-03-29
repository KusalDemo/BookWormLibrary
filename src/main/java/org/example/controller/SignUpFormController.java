package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Util.Mail;
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
    public TextField txtPasswordView;
    public TextField txtRePasswordView;
    public ImageView imgLockedPassword;
    public ImageView imgLockedPassword2;
    public ImageView imgViewPassword;
    public ImageView imgViewPassword2;

    AdminBO adminBO = (AdminBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.ADMIN);
    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    BranchBO branchBO = (BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void initialize() throws ClassNotFoundException {
        cmbRole.getItems().addAll("User", "Admin");
        cmbRole.getSelectionModel().select(1);

        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for (BranchDto branchDto : allBranches) {
            cmbBranch.getItems().add(branchDto.getBranchId() + " - " + branchDto.getBranchName());
        }
        cmbBranch.getSelectionModel().select(0);


        cmbRole.setOnAction(event -> {
            String selectedOption = (String) cmbRole.getValue();
            if(cmbRole.getValue().equals("User")){
                cmbBranch.setVisible(true);
                lblBranch.setVisible(true);
                imgBranch.setVisible(true);
            } else if (cmbRole.getValue().equals("Admin")) {
                cmbBranch.setVisible(false);
                lblBranch.setVisible(false);
                imgBranch.setVisible(false);
            }
        });
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String rePassword = txtRePassword.getText();
        String role = cmbRole.getValue().toString();

        if(Regex.isNameValid(userName)){
            if(Regex.isEmailValid(email)){
                if(Regex.isPasswordValid(password)){
                    if(password.equals(rePassword)){
                        if (role.equals("Admin")) {
                            String adminId = AdminIdGenerator();
                            AdminDto adminDto = new AdminDto(adminId, userName, email, password);
                            try {
                                if (adminBO.saveAdmin(adminDto)) {
                                    boolean isMailSent = sendMail(email, adminId);
                                    new Alert(Alert.AlertType.INFORMATION, "Sign Up Successful , Your Admin ID is sent to your email..").show();
                                    clearFields();
                                    btnAlreadyHaveAnAccountOnAction(actionEvent);
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
                                }
                            } catch (Exception e) {
                                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                                System.out.println(e.getMessage());
                            }

                        } else if (role.equals("User")) {
                            String branch = cmbBranch.getValue().toString();
                            String[] parts = branch.split(" - ");
                            String branchId = parts[0];
                            BranchDto branchDto = branchBO.searchBranch(branchId);
                            UserDto userDto = new UserDto(userName, email, password, branchDto);
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
                        }
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Password does not match").show();
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR, "Invalid Password , Please enter a valid password").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Invalid Email , Please enter a valid email").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid User Name , Please enter a username containing only letters, numbers, and underscores. The username must be between 3 and 20 characters long.").show();
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
        return "AD"+formattedDate;
    }
    private boolean sendMail(String email,String id){
        try {
            Mail mail = new Mail();
            mail.setMsg("Hi there,"+ "\n\n\tADMIN : "+email+"\n\n\tYour Admin ID : "+id +"\n\n\tDate & Time : "+ LocalDateTime.now() + "\n\n\t Don't Share Your Admin ID with Anyone \n\nThank You,\n" +
                    "BookWorm Support Team");
            mail.setTo(email);
            mail.setSubject("ADMIN Verification [private and confidential]");

            Thread thread = new Thread(mail);
            thread.start();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void imgViewPassword(MouseEvent mouseEvent) {
        txtPasswordView.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        imgViewPassword.setVisible(false);
        txtPasswordView.setVisible(true);
    }

    public void imgViewPassword2OnAction(MouseEvent mouseEvent) {
        txtRePasswordView.setText(txtRePassword.getText());
        txtRePassword.setVisible(false);
        imgViewPassword2.setVisible(false);
        txtRePasswordView.setVisible(true);
    }

    public void imgLockedPasswordOnAction(MouseEvent mouseEvent) {
        txtPassword.setText(txtPasswordView.getText());
        txtPasswordView.setVisible(false);
        imgViewPassword.setVisible(true);
        txtPassword.setVisible(true);
    }

    public void imgLockedPassword2OnAction(MouseEvent mouseEvent) {
        txtRePassword.setText(txtRePasswordView.getText());
        txtRePasswordView.setVisible(false);
        imgViewPassword2.setVisible(true);
        txtRePassword.setVisible(true);
    }
}

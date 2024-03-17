package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Util.Mail;
import org.example.bo.BoFactory;
import org.example.bo.custom.AdminBO;
import org.example.bo.custom.UserBO;
import org.example.dto.AdminDto;
import org.example.dto.UserDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


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
    public Text lblCredential;
    public Label lblForgetPassword;

    public static UserDto passwordChangeRequestedUserDto=null;
    public static AdminDto passwordChangeRequestedAdminDto=null;



    public static String otp;
    public TextField txtPasswordView;
    public ImageView imgViewPassword;
    public ImageView imgLockedPassword;
    AdminBO adminBO=(AdminBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.ADMIN);
    UserBO userBO=(UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);

    public void initialize(){
        cmbRole.getItems().addAll("User", "Admin");
        cmbRole.getSelectionModel().select(0);
        lblCredential.setText("User Name");

        cmbRole.setOnAction(event -> {
            if(cmbRole.getValue().equals("User")){
               lblCredential.setText("User Name");
            } else if (cmbRole.getValue().equals("Admin")) {
                lblCredential.setText("Admin ID");
            }
        });
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
        Boolean isFoundInDB = false;
        if (cmbRole.getValue().equals("User")) {
            try {
                List<UserDto> allUsers = userBO.getAllUsers();
                for (UserDto userDto : allUsers) {
                    System.out.println(userDto.getEmail());
                    if (userDto.getEmail().equals(txtEmail.getText()) && userDto.getPassword().equals(txtPassword.getText())) {
                        isFoundInDB = true;
                        loggedPerson=userDto.getEmail();
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
                AdminDto admin = adminBO.getAdmin(txtEmail.getText());
                if (admin.getPassword().equals(txtPassword.getText())) {
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

    public void lblForgetPassword(MouseEvent mouseEvent) throws ClassNotFoundException {
        if(cmbRole.getValue().equals("User")&& !txtEmail.getText().isEmpty()){
            try{
                UserDto userDto = userBO.searchUserFromUserName(txtEmail.getText());
                boolean isSend = sendMail(userDto.getEmail());
                if(isSend){
                    new Alert(Alert.AlertType.INFORMATION, "OTP Sent Successfully").showAndWait();
                    passwordChangeRequestedUserDto=userDto;
                    System.out.println("This User is tying to change his/her password : "+passwordChangeRequestedUserDto.getEmail());
                    loadPasswordForm();
                }else{
                    new Alert(Alert.AlertType.ERROR, "OTP Sending Failed").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else if(cmbRole.getValue().equals("Admin")&& !txtEmail.getText().isEmpty()){
            try{
                AdminDto adminDto = adminBO.getAdmin(txtEmail.getText());
                boolean isSend = sendMail(adminDto.getEmail());
                if(isSend){
                    new Alert(Alert.AlertType.INFORMATION, "OTP Sent Successfully").showAndWait();
                    loadPasswordForm();
                    System.out.println("This User is tying to change his/her password : "+passwordChangeRequestedAdminDto.getEmail());
                    passwordChangeRequestedAdminDto=adminDto;
                }else{
                    new Alert(Alert.AlertType.ERROR, "OTP Sending Failed").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    private boolean sendMail(String email){
        try {
            otp=String.valueOf(generateOTP());
            Mail mail = new Mail();
            mail.setMsg("Hi there,"+ "\n\n\tUser :  \n\n\tYour OTP : "+otp+ "\n\n\tDate & Time : "+ LocalDateTime.now() + " \n\nThank You,\n" +
                    "BookWorm Support Team");
            mail.setTo(email);
            mail.setSubject("OTP Verification");


            Thread thread = new Thread(mail);
            thread.start();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static int generateOTP(){
        Random random = new Random();
        int password = random.nextInt(9000000) + 1000000;
        System.out.println(password);
        return password;
    }
    private void loadPasswordForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/passwordChange_form.fxml"));
            Scene scene1 = new Scene(root);
            Stage stage1 = (Stage) lblForgetPassword.getScene().getWindow();
            stage1.setScene(scene1);
            stage1.setTitle("Password Reset");
            stage1.centerOnScreen();

            new FadeIn(root).play();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void imgViewPasswordOnAction(MouseEvent mouseEvent) {
        txtPasswordView.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        imgViewPassword.setVisible(false);
        txtPasswordView.setVisible(true);
    }

    public void imgLockedPasswordOnAction(MouseEvent mouseEvent) {
        txtPassword.setText(txtPasswordView.getText());
        txtPasswordView.setVisible(false);
        imgViewPassword.setVisible(true);
        txtPassword.setVisible(true);
    }
}

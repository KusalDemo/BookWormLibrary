package org.example.controller;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BorrowBooksBO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;
import org.example.dto.UserDto;
import org.example.dto.tm.BorrowBookTM;

import java.util.List;

public class UserBorrowedBooksFormController {

    public TableView<BorrowBookTM> tblBorrowBooks;
    public TableColumn<?,?> colBorrowID;
    public TableColumn<?,?> colBookID;
    public TableColumn<?,?> colBorrowDate;
    public TableColumn<?,?> colReturnDate;

    public Button btnBack;
    public Label lblBookID;
    public Label lblBookTitle;
    public Label lblBranch;
    public Label lblBranchEmail;
    public Button btnClear;
    public Label lblBorrow;
    public Label lblUserName;
    UserDto userDto;

    BookBO bookBO=(BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    BorrowBooksBO borrowBooksBO=(BorrowBooksBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BORROWBOOKS);

    public void initialize() throws ClassNotFoundException {
        userDto=LoginFormController.currentUserDto;
        if(userDto==null){
            System.out.println("No user");
        }else{
            loadAllBorrowedBooksByUser(userDto.getUserName());
            setCellValueFactory();
            lblUserName.setText(userDto.getEmail());
        }

        tblBorrowBooks.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() >= 0){
                BorrowBookTM selectedBorrowBook = tblBorrowBooks.getItems().get(newValue.intValue());
                lblBorrow.setText(selectedBorrowBook.getId());
                /*try {
                    BookDto bookDto = bookBO.searchBook(selectedBorrowBook.getBookId());
                    lblBookID.setText(bookDto.getId());
                    lblBookTitle.setText(bookDto.getTitle());
                    lblBranch.setText(bookDto.getBranch().getBranchName());
                    lblBranchEmail.setText(bookDto.getBranch().getEmail());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }*/
            }
        });
    }
    public void setCellValueFactory(){
        colBorrowID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
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

    public void btnClearOnAction(ActionEvent actionEvent) {
        lblBookID.setText("");
        lblBookTitle.setText("");
        lblBranchEmail.setText("");
        lblBranch.setText("");
    }

    private void loadAllBorrowedBooksByUser(String userID) throws ClassNotFoundException {
        List<BorrowBooksDto> allBorrowBooksFromUserId = borrowBooksBO.getAllBorrowBooksFromUserId(userID);
        ObservableList<BorrowBookTM> obList = FXCollections.observableArrayList();
       for(BorrowBooksDto b:allBorrowBooksFromUserId){
           System.out.println(b.getBook().getId());
           obList.add(new BorrowBookTM(b.getId(),b.getBook().getTitle(),b.getBook().getId(),String.valueOf(b.getBorrowDate()),String.valueOf(b.getReturnDate()),b.getStatus()));
       }
       tblBorrowBooks.setItems(obList);
    }
}

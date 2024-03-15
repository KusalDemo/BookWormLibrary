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
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BorrowBooksBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;
import org.example.dto.UserDto;
import org.example.dto.tm.BorrowBookTM;

import java.time.LocalDate;
import java.util.List;

public class ViewBorrowedBooksFormController {

    public TableView<BorrowBookTM> tblBorrowBooks;
    public TableColumn<?,?> colBorrowID;
    public TableColumn<?,?> colBookID;
    public TableColumn<?,?> colUserID;
    public TableColumn<?,?> colBorrowDate;
    public TableColumn<?,?> colReturnDate;
    public ComboBox cmbLoadBooks;
    public Button btnBack;
    public Label lblUserID;
    public Label lblUserName;
    public Label lblUserEmail;
    public Label lblBookID;
    public Label lblBookTitle;
    public Label lblBranch;
    public Label lblBranchEmail;

    BorrowBooksBO borrowBooksBO = (BorrowBooksBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BORROWBOOKS);
    BookBO bookBO = (BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    UserBO userBO = (UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);

    public void initialize(){
        cmbLoadBooks.getItems().addAll("All Borrowed Books","Return Date Exceeded Books");
        cmbLoadBooks.setValue("All Borrowed Books");
        if(cmbLoadBooks.getValue().equals("All Borrowed Books")){
            try {
                loadAllBorrowedBooks();
                setCellValueFactory();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if(cmbLoadBooks.getValue().equals("Return Date Exceeded Books")){
            try {
                loadAllReturnDateExceededBooks();
                setCellValueFactory();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        tblBorrowBooks.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() >= 0){
                BorrowBookTM selectedBorrowBook = tblBorrowBooks.getItems().get(newValue.intValue());
                try {
                    BookDto bookDto = bookBO.searchBook(String.valueOf(selectedBorrowBook.getBookId()));
                    lblBookID.setText(bookDto.getId());
                    lblBookTitle.setText(bookDto.getTitle());
                    lblBranch.setText(bookDto.getBranch().getBranchName());
                    lblBranchEmail.setText(bookDto.getBranch().getEmail());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                try{
                    UserDto userDto = userBO.searchUser(String.valueOf(selectedBorrowBook.getUserId()));
                    lblUserID.setText(userDto.getEmail());
                    lblUserName.setText(userDto.getUserName());
                    lblUserEmail.setText(userDto.getBranchDto().getBranchName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void setCellValueFactory(){
        colBorrowID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    private void loadAllBorrowedBooks() throws ClassNotFoundException {
        List<BorrowBooksDto> allBorrowBooks = borrowBooksBO.getAllBorrowBooks();
        ObservableList<BorrowBookTM> obList = FXCollections.observableArrayList();
        for(BorrowBooksDto borrowBooksDto:allBorrowBooks){
            obList.add(new BorrowBookTM(
                    borrowBooksDto.getId(),
                    borrowBooksDto.getUser().getUserName(),
                    borrowBooksDto.getBook().getAuthor(),
                    borrowBooksDto.getBorrowDate().toString(),
                    borrowBooksDto.getReturnDate().toString(),
                    borrowBooksDto.getStatus())
            );
        }
        tblBorrowBooks.setItems(obList);
        tblBorrowBooks.refresh();
    }
    private void loadAllReturnDateExceededBooks() throws ClassNotFoundException {
        List<BorrowBooksDto> returnDateExceededBooks = borrowBooksBO.getReturnDateExceededBooks();
        ObservableList<BorrowBookTM> obList = FXCollections.observableArrayList();
        for(BorrowBooksDto borrowBooksDto:returnDateExceededBooks){
            obList.add(new BorrowBookTM(
                    borrowBooksDto.getId(),
                    borrowBooksDto.getUser().getEmail(),
                    borrowBooksDto.getBook().getTitle(),
                    borrowBooksDto.getBorrowDate().toString(),
                    borrowBooksDto.getReturnDate().toString(),
                    borrowBooksDto.getStatus())
            );
        }
        tblBorrowBooks.setItems(obList);
        tblBorrowBooks.refresh();
    }
    private void loadAllReturnDateExceededBooks(String id) throws ClassNotFoundException {
        List<BorrowBooksDto> returnDateExceededBooks = borrowBooksBO.getReturnDateExceededBooks(id);
        ObservableList<BorrowBookTM> obList = FXCollections.observableArrayList();
        for(BorrowBooksDto borrowBooksDto:returnDateExceededBooks){
            obList.add(new BorrowBookTM(
                    borrowBooksDto.getId(),
                    borrowBooksDto.getBook().getId(),
                    borrowBooksDto.getUser().getEmail(),
                    borrowBooksDto.getBorrowDate().toString(),
                    borrowBooksDto.getReturnDate().toString(),
                    borrowBooksDto.getStatus())
            );
        }
        tblBorrowBooks.setItems(obList);
        tblBorrowBooks.refresh();
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
}

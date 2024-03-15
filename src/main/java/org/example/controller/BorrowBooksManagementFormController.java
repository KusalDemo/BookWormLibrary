package org.example.controller;

import animatefx.animation.FadeIn;
import jakarta.persistence.PersistenceException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BorrowBooksBO;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;
import org.example.dto.BranchDto;
import org.example.dto.UserDto;
import org.example.dto.tm.BookTM;
import org.example.dto.tm.BorrowBookTM;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BorrowBooksManagementFormController {

    public TextField txtSearch;
    public ComboBox cmbBranch;
    public Label lblNotice;
    public TextField txtBorrowId;
    public TextField txtUserId;
    public TextField txtBookId;
    public TextField txtBookName;
    public TextField txtLocation;
    public TextField txtToday;
    public TextField txtReturnDate;
    public Button btnBorrow;
    public TableView<BookTM> tblBorrowBooks;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colTitle;
    public TableColumn<?,?> colAuthor;
    public TableColumn<?,?> colGenre;

    /*public UserDto loggedUser=LoginFormController.currentUserDto;*/
    public Button btnBack;


    BorrowBooksBO borrowBooksBO=(BorrowBooksBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BORROWBOOKS);
    BookBO bookBO=(BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    BranchBO branchBO=(BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);
    UserBO userBO=(UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);

    public void initialize() throws ClassNotFoundException {
        txtUserId.setText("boosri@gmail.com");
        /*txtUserId.setText(loggedUser.getEmail());*/
        checkEligible();
        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for(BranchDto branchDto : allBranches){
            cmbBranch.getItems().add(branchDto.getBranchId()+" - "+branchDto.getBranchName());
        }
        cmbBranch.setOnAction(event -> {
            clearFields();
            loadTableDetailsFromBranch();
        });
        setCellValueFactory();

        tblBorrowBooks.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>=0){
                txtBorrowId.setText(borrowIdGenerator());
                BookTM selectedItem = tblBorrowBooks.getItems().get(newValue.intValue());
                txtBookId.setText(selectedItem.getId());
                txtBookName.setText(selectedItem.getTitle());
                txtLocation.setText(selectedItem.getBranchId());
                //Time Today
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateString = currentDate.format(formatter);
                txtToday.setText(dateString);
                //Return Date
                LocalDate currentDate1 = LocalDate.now();
                LocalDate twoWeeksAhead = currentDate1.plusWeeks(2);
                String dateString1 = twoWeeksAhead.format(formatter);
                txtReturnDate.setText(dateString1);
            }
        });

    }

    public void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
    }
    public void btnBorrowOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("You have chosen : " + txtBookName.getText());
        alert.setContentText("Do you want to Borrow ?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                try {
                    UserDto selectedUserDto = userBO.searchUser(txtUserId.getText());

                    BookDto selectedBookDto = new BookDto();
                    String selectedBranch = (String)cmbBranch.getValue();
                    String[] parts = selectedBranch.split(" - ");
                    String branchId = parts[0];
                    ArrayList<BookDto> allAvailableBooksFromBranchId = bookBO.getAllAvailableBooksFromBranchId(branchId);
                    for(BookDto bookDto : allAvailableBooksFromBranchId){
                        if(bookDto.getId().equals(txtBookId.getText())){
                            selectedBookDto = bookDto;
                        }
                    }
                    System.out.println(selectedUserDto.getEmail()+" "+selectedUserDto.getUserName()+" "+selectedUserDto.getBranchDto().getBranchId()+" "+selectedUserDto.getBranchDto().getBranchName()+" "+selectedUserDto.getBranchDto().getEmail()+" "+selectedUserDto.getPassword());

                    LocalDate today = LocalDate.parse(txtToday.getText());
                    LocalDate returnDate = LocalDate.parse(txtReturnDate.getText());
                    boolean isSaved = borrowBooksBO.saveBorrowBook(new BorrowBooksDto(txtBorrowId.getText(), selectedUserDto, selectedBookDto, today, returnDate, "Pending"));
                    if(isSaved){
                        tblBorrowBooks.refresh();
                        new Alert(Alert.AlertType.INFORMATION, "Your Book borrowing request has been accepted.. Return your book within 2 weeks. Thank you").show();
                    }
                    else{
                        new Alert(Alert.AlertType.ERROR, "Something went wrong. Please try again").show();
                    }
                }catch (PersistenceException e){
                    throw e;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (response == buttonTypeNo) {
                System.out.println("User cancelled the operation.");
            }
        });

    }

    private void loadAllAvailableBooks(String branch){
        try{
            ArrayList<BookDto> allAvailableBooksFromBranchId = bookBO.getAllAvailableBooksFromBranchId(branch);
            ObservableList<BookTM> observableList = FXCollections.observableArrayList();
            for (BookDto bookDto : allAvailableBooksFromBranchId) {
                observableList.add(new BookTM(bookDto.getId(),bookDto.getTitle(),bookDto.getAuthor(),bookDto.getGenre(),String.valueOf(bookDto.isAvailability()),bookDto.getBranch().getBranchName()));
            }
            tblBorrowBooks.setItems(observableList);
            tblBorrowBooks.refresh();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadTableDetailsFromBranch(){
        String selectedBranch =(String)cmbBranch.getValue();
        String[] parts = selectedBranch.split(" - ");
        String branchId = parts[0];
        loadAllAvailableBooks(branchId);
    }
    private String borrowIdGenerator(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatStyle = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        String formattedDate = now.format(formatStyle);
        return "Bor-"+formattedDate;
    }

    private void checkEligible(){
        try {
            List<BorrowBooksDto> returnDateExceededBooks = borrowBooksBO.getReturnDateExceededBooks(txtUserId.getText());
            if (returnDateExceededBooks.size() > 0) {
                lblNotice.setText("Sorry.. we found some books which are not returned yet.. Please return them for your next borrow");
                btnBorrow.setVisible(false);
            }else{
                lblNotice.setText("Select Book and Click Borrow Button..");
                btnBorrow.setVisible(true);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public void txtSearchOnAction(KeyEvent keyEvent) throws ClassNotFoundException {
        clearFields();
        String selectedBranch =(String)cmbBranch.getValue();
        String[] parts = selectedBranch.split(" - ");
        String branchId = parts[0];
        String branchName = parts[1];
        ArrayList<BookDto> allBooks = bookBO.getAllAvailableBooksFromBranchId(branchId);
        for (BookDto book:allBooks){
            if(book.getTitle().toLowerCase().equals(txtSearch.getText().toLowerCase())){
                txtBorrowId.setText(borrowIdGenerator());
                txtBookId.setText(book.getId());
                txtBookName.setText(book.getTitle());
                txtLocation.setText(branchName);
                //Time Today
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String dateString = currentDate.format(formatter);
                txtToday.setText(dateString);
                //Return Date
                LocalDate currentDate1 = LocalDate.now();
                LocalDate twoWeeksAhead = currentDate1.plusWeeks(2);
                String dateString1 = twoWeeksAhead.format(formatter);
                txtReturnDate.setText(dateString1);
            }
        }
    }
    public void clearFields(){
        txtBookId.clear();
        txtBookName.clear();
        txtBorrowId.clear();
        txtReturnDate.clear();
        txtToday.clear();
    }
}

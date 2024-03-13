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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BookDto;
import org.example.dto.BranchDto;
import org.example.dto.tm.BookTM;
import org.example.entity.Branch;

import java.util.ArrayList;

public class BookManagementFormController {

    public TextField txtBookName;
    public TextField txtAuthor;
    public TextField txtGenre;
    public ComboBox cmbBranches;
    public TextField txtBookId;
    public TextField txtSearchBook;
    public TextField txtAvailability;
    public TableView<BookTM> tblBook;
    public TableColumn<?,?> colId;
    public TableColumn<?,?> colTitle;
    public TableColumn<?,?> colAuthor;
    public TableColumn<?,?> colGenre;
    public TableColumn<?,?> colAvailability;
    public TableColumn<?,?> colBranch;
    public Button btnBack;

    private Branch branch;

    BookBO bookBO=(BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    UserBO userBO=(UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    BranchBO branchBO=(BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void initialize() throws ClassNotFoundException {
        try {
            ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
            for(BranchDto branchDto:allBranches){
                cmbBranches.getItems().add(branchDto.getBranchName());
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e.getMessage());
        }
        loadAllBooks();
        setCellValueFactory();

        tblBook.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue()>=0){
                BookTM selectedItem = tblBook.getItems().get(newValue.intValue());
                txtBookId.setText(selectedItem.getId());
                txtBookName.setText(selectedItem.getTitle());
                txtAuthor.setText(selectedItem.getAuthor());
                txtGenre.setText(selectedItem.getGenre());
                txtAvailability.setText(selectedItem.getAvailability());
                cmbBranches.setValue(selectedItem.getBranchId());
            }
        });

    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colBranch.setCellValueFactory(new PropertyValueFactory<>("branchId"));
    }
    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String bookId = generateBookId();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String bookName = txtBookName.getText();
        String branchName = (String) cmbBranches.getValue();

        BranchDto branch = null ;
        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for(BranchDto branchDto:allBranches){
            if(branchDto.getBranchName().equalsIgnoreCase(branchName)){
                branch = branchDto;
                break;
            }
        }

        if(bookId.isEmpty() || bookName.isEmpty() || author.isEmpty() || genre.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please provide all the details").show();
        } else {
            try {
                // Assuming you have a way to get the selected branch's ID
                String selectedBranchId = (String) cmbBranches.getValue(); // Implement this method based on your UI setup
                System.out.println("Selected Branch ID: " + selectedBranchId);

                ArrayList<BookDto> allBooks = bookBO.getAllBooks();
                boolean isBookAlreadyExist = false;
                for (BookDto bookDto : allBooks) {
                    if (bookDto.getTitle().equalsIgnoreCase(bookName)) {
                        new Alert(Alert.AlertType.ERROR, "Book Already Exists").show();
                        isBookAlreadyExist = true;
                        break; // Exit the loop if the book already exists
                    }
                }
                if(!isBookAlreadyExist){
                    boolean isSaved = bookBO.saveBook(new BookDto(bookId, bookName, author, genre, true, branch));
                    loadAllBooks();
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Book Saved").show();
                        clearFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Book Not Saved").show();
                    }
                }
            } catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println(e.getMessage());
            }
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        BranchDto branchDto = branchBO.searchBranch((String) cmbBranches.getValue().toString());
        if(!txtBookName.getText().equals("")){
            try {
                boolean isUpdated = bookBO.updateBook(new BookDto(txtBookId.getText(), txtBookName.getText(), txtAuthor.getText(), txtGenre.getText(), true, branchDto));
                loadAllBooks();
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Book Updated").show();
                    clearFields();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Book Not Updated").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                System.out.println(e.getMessage());
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Please provide Book Name").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if (!txtBookName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you want to delete? All related data will also be deleted.");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeYes = new ButtonType("Yes");
            ButtonType buttonTypeNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            alert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    try {boolean isDeleted = bookBO.deleteBook(txtBookName.getText());
                        if (isDeleted) {
                            loadAllBooks();
                            new Alert(Alert.AlertType.INFORMATION, "Book Deleted").show();
                            clearFields();
                        }else{
                            new Alert(Alert.AlertType.ERROR, "Book Not Deleted").show();
                        }
                    }catch (Exception e){
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                        System.out.println(e.getMessage());
                    }
                } else if (response == buttonTypeNo) {
                    System.out.println("User clicked No");
                }
            });
        }else{
            new Alert(Alert.AlertType.ERROR, "Please provide Book Name").show();
        }
    }
    private void clearFields() {
        txtBookId.clear();
        txtBookName.clear();
        txtAuthor.clear();
        txtGenre.clear();
        txtAvailability.clear();
        cmbBranches.getSelectionModel().clearSelection();
    }

    private String generateBookId() throws ClassNotFoundException {
        String bookId;
        ArrayList<BookDto> allBooks = bookBO.getAllBooks();
        int size = allBooks.size();
        bookId="B-"+(size+1);
        return bookId;
    }

    public void txtBookIdOnAction(MouseEvent mouseEvent) throws ClassNotFoundException {
        txtBookId.setText(generateBookId());
    }

    public void txtSearchBookOnAction(KeyEvent keyEvent) throws ClassNotFoundException {
        clearFields();
        ArrayList<BookDto> allBooks = bookBO.getAllBooks();
        for (BookDto book:allBooks){
            if(book.getTitle().toLowerCase().equals(txtSearchBook.getText().toLowerCase())){
                txtBookId.setText(book.getId());
                txtBookName.setText(book.getTitle());
                txtAuthor.setText(book.getAuthor());
                txtGenre.setText(book.getGenre());
                txtAvailability.setText(book.isAvailability()?"Available":"Not Available");
                cmbBranches.setValue(book.getBranch().getBranchName());
            }
        }
    }
    private void loadAllBooks() throws ClassNotFoundException {
        ArrayList<BookDto> allBooks = bookBO.getAllBooks();
        ObservableList<BookTM> obList = FXCollections.observableArrayList();
        for (BookDto book:allBooks){
            BranchDto branch = book.getBranch();
            obList.add(new BookTM(book.getId(),book.getTitle(),book.getAuthor(),book.getGenre(),String.valueOf(book.isAvailability()),branch.getBranchId()));
        }
        tblBook.setItems(obList);
        tblBook.refresh();
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

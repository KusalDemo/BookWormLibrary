package org.example.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BranchBO;
import org.example.bo.custom.UserBO;
import org.example.dto.BookDto;
import org.example.dto.BranchDto;
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

    private Branch branch;

    BookBO bookBO=(BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    UserBO userBO=(UserBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.USER);
    BranchBO branchBO=(BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void initialize(){
        try {
            ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
            for(BranchDto branchDto:allBranches){
                cmbBranches.getItems().add(branchDto.getBranchName());
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            System.out.println(e.getMessage());
        }
    }
    public void btnSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        String bookId = generateBookId();
        String author = txtAuthor.getText();
        String genre = txtGenre.getText();
        String bookName = txtBookName.getText();
        String branchName = (String) cmbBranches.getValue();

        String branchId = "";
        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for(BranchDto branchDto:allBranches){
            if(branchDto.getBranchName().equalsIgnoreCase(branchName)){
                branchId = branchDto.getBranchId();
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
                    boolean isSaved = bookBO.saveBook(new BookDto(bookId, bookName, author, genre, true, branchId));
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


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if(!txtBookName.getText().equals("")){
            try {
                boolean isUpdated = bookBO.updateBook(new BookDto(txtBookId.getText(), txtBookName.getText(), txtAuthor.getText(), txtGenre.getText(), true, (String)cmbBranches.getValue()));
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
                cmbBranches.setValue(book.getBranchId());
            }
        }
    }
}

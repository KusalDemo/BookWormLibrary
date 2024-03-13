package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.bo.BoFactory;
import org.example.bo.custom.BookBO;
import org.example.bo.custom.BorrowBooksBO;
import org.example.bo.custom.BranchBO;
import org.example.dto.BookDto;
import org.example.dto.BorrowBooksDto;
import org.example.dto.BranchDto;
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


    BorrowBooksBO borrowBooksBO=(BorrowBooksBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BORROWBOOKS);
    BookBO bookBO=(BookBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BOOK);
    BranchBO branchBO=(BranchBO) BoFactory.getBoFactory().getBO(BoFactory.BOType.BRANCH);

    public void initialize() throws ClassNotFoundException {
        //txtUserId.setText(LoginFormController.currentUserDto.getEmail());
        ArrayList<BranchDto> allBranches = branchBO.getAllBranches();
        for(BranchDto branchDto : allBranches){
            cmbBranch.getItems().add(branchDto.getBranchId()+" - "+branchDto.getBranchName());
        }

        cmbBranch.setOnAction(event -> {
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
    public void btnBorrowOnAction(ActionEvent actionEvent) {

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
}

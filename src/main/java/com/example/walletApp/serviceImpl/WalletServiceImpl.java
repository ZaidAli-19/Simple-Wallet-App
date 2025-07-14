package com.example.walletApp.serviceImpl;

import com.example.walletApp.exceptionHandling.CannotDeleteWalletException;
import com.example.walletApp.exceptionHandling.UserNotFoundException;
import com.example.walletApp.exceptionHandling.WalletNotFoundException;
import com.example.walletApp.model.Transaction;
import com.example.walletApp.model.Wallet;
import com.example.walletApp.repository.TransactionRepository;
import com.example.walletApp.repository.UserRepository;
import com.example.walletApp.repository.WalletRepository;
import com.example.walletApp.request.PaginationRequest;
import com.example.walletApp.request.WalletRequest;
import com.example.walletApp.response.TransactionResponse;
import com.example.walletApp.response.WalletResponse;
import com.example.walletApp.response.WalletsResponse;
import com.example.walletApp.service.WalletService;
import com.example.walletApp.util.ResponseMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(UserRepository userRepository, WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }


    public String createWallet(WalletRequest walletRequest) throws RuntimeException {
            Wallet wallet = new Wallet();
            wallet.setBalance(0d);
            wallet.setUser(userRepository.findById(walletRequest.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("Cannot find user with id:"+walletRequest.getUserId()+" ! Please provide a valid user Id.")));
            walletRepository.save(wallet);
            return "Wallet created successfully.\n"+"WalletId : "+wallet.getWalletId();
    }

//This method will return a wallet and it's transactions.
    public WalletResponse getInfoByWalletId(String id) {
         return ResponseMapper.toWalletResponse(walletRepository.findById(id).orElseThrow
                 (()->new WalletNotFoundException("Invalid wallet id! please provide a valid wallet id.")));
    }

//This method will return all the wallets of a user since a user can have multiple wallets.
    public List<WalletsResponse> getAllWalletsByUuid(String uuid){
        List<Wallet> wallet = walletRepository.findByUser_Uuid(uuid);
        if(wallet.isEmpty()){
            throw new UserNotFoundException("Invalid user id! Please provide a valid user Id");
        }
      return  wallet.stream().map(ResponseMapper::toWalletsResponse).toList();
    }


//This will simply return the current balance of a wallet.
    public String showBalance(String walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Wallet Id is not valid!"));
return "current balance in wallet "+wallet.getWalletId()+" is "+wallet.getBalance()+".";
    }



    //method for downloading transactions in Excel file format
    public void downloadTransactions(String walletId, HttpServletResponse response) throws IOException {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(()->new RuntimeException("invalid id"));

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.xlsx");

        XSSFWorkbook workbook=new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Transactions");

        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));

        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("S.No");
        row.createCell(1).setCellValue("Type");
        row.createCell(2).setCellValue("Amount");
        row.createCell(3).setCellValue("Description");
        row.createCell(4).setCellValue("Date&Time");

        int rowNum=1;
        int count=1;
        for(Transaction transaction: wallet.getTransactionHistory()){
            XSSFRow row1 = sheet.createRow(rowNum++);
            row1.createCell(0).setCellValue(count++);
            row1.createCell(1).setCellValue(transaction.getTransactionType().toString());
            row1.createCell(2).setCellValue(transaction.getAmount());
            row1.createCell(3).setCellValue(transaction.getDescription());
            Cell dateCell = row1.createCell(4);
            dateCell.setCellValue(java.sql.Timestamp.valueOf(transaction.getDateTime()));
            dateCell.setCellStyle(dateStyle);
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }


    public String deleteWallet(String walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("invalid id"));
        if(wallet.getBalance()==0){
            walletRepository.delete(wallet);
            return "wallet deleted successfully!";
        }
            throw new CannotDeleteWalletException("Wallet deletion failed because wallet has balance in it.");
    }

//This method will return the recent transactions , Pagination is implemented here.
    public List<TransactionResponse> getRecentTransactionsByWalletId(String walletId, PaginationRequest request) {
        if (request.getPageSize() != null && request.getPageNumber() != null) {
            Pageable pageable = PageRequest.of(request.getPageNumber() - 1,
                    request.getPageSize(),
                    Sort.by(Sort.Direction.DESC,
                            request.getSortBy()));
            List<Transaction> byWalletWalletId = transactionRepository.findByWallet_walletId(walletId, pageable);
            return byWalletWalletId.stream().map(ResponseMapper::toTransactionResponse).toList();
        } else {
            Sort sort = Sort.by(request.getSortBy()).descending();
            List<Transaction> byWalletWalletId = transactionRepository.findByWallet_walletId(walletId, sort);
            return byWalletWalletId.stream().map(ResponseMapper::toTransactionResponse).toList();
        }
    }


//    public void downloadTransactions(String walletId, HttpServletResponse response) throws IOException {
//        Wallet wallet = walletRepository.findById(walletId)
//                .orElseThrow(() -> new RuntimeException("Wallet not found"));
//
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");
//
//        Document document = new Document();
//        PdfWriter.getInstance(document, response.getOutputStream());
//        document.open();
//
//        int count = 1;
//        for (Transaction txn : wallet.getTransactionHistory()) {
//            String line = count++ + ". " +
//                    txn.getTransactionType() + " - ₹" +
//                    txn.getAmount() + " - " +
//                    txn.getDescription() + " - " +
//                    txn.getDateTime()+"\n"+"\n";
//
//            document.add(new Paragraph(line));
//        }
//
//        document.close();
//    }
}

package com.example.filterPoc.service;

import com.example.filterPoc.model.Transaction;
import com.example.filterPoc.model.Wallet;
import com.example.filterPoc.repository.UserRepository;
import com.example.filterPoc.repository.WalletRepository;
import com.example.filterPoc.request.WalletRequest;
import com.example.filterPoc.response.WalletResponse;
import com.example.filterPoc.response.WalletsResponse;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Row;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WalletService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public WalletService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    public String createWallet(WalletRequest walletRequest) throws RuntimeException {
            Wallet wallet = new Wallet();
            wallet.setBalance(0d);
            wallet.setUser(userRepository.findById(walletRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("invalid user id")));
            walletRepository.save(wallet);
            return "Wallet created successfully.\n"+"WalletId : "+wallet.getWalletId();
    }

    public WalletResponse getInfo(String id) {
         return new WalletResponse(walletRepository.findById(id).orElseThrow
                 (()->new RuntimeException("invalid id")));
    }

    public List<WalletsResponse> getAllInfo(String uuid) {
        List<Wallet> wallet = walletRepository.findByUser_Uuid(uuid);
      return  wallet.stream().map(WalletsResponse::new).toList();
    }

    public String showBalance(String walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("invalid Id"));
return "current balance in wallet "+wallet.getWalletId()+" is "+wallet.getBalance()+".";
    }

    //method for downloading transactions in Excel file format.
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

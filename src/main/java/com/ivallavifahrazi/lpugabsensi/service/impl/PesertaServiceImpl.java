package com.ivallavifahrazi.lpugabsensi.service.impl;

import com.ivallavifahrazi.lpugabsensi.controller.DateBatchController;
import com.ivallavifahrazi.lpugabsensi.models.*;
import com.ivallavifahrazi.lpugabsensi.repository.*;
import com.ivallavifahrazi.lpugabsensi.service.PesertService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PesertaServiceImpl implements PesertService {
    @Autowired
    PesertaJadwalRepository pesertaJadwalRepository;

    @Autowired
    JadwalRepository jadwalRepository;


    @Autowired
    DateBatchRepository dateBatchRepository;

    @Autowired
    PesertaRepository pesertaRepository;
    Logger logger = LoggerFactory.getLogger(DateBatchController.class);

    @Override
    public void importToDb(List<MultipartFile> multipartFileList, Long batchId) {
//        if(!multipartFileList.isEmpty()){
//            List<PesertaJadwal> pesertaJadwal = new ArrayList<>();
//            Set<Long> batchSet = new HashSet<Long>();
//
//
//            List<BatchNumber> batchNumberList = new ArrayList<>();
//            multipartFileList.forEach(multipartFile -> {
//                try{
//                    XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
//                    XSSFSheet sheet = workbook.getSheetAt(0);
//
//                    //looping each row
//                    for (int rowIndex = 0; rowIndex < getNumberOfNonEmptyCells(sheet,1); rowIndex++) {
//                        XSSFRow row = sheet.getRow(rowIndex);
//                        if(rowIndex == 0){
//                            continue;
//                        }
//                        String nama = String.valueOf(row.getCell(0));
//                        String npm = String.valueOf(Long.parseLong(getValue(row.getCell(1)).toString()));
//                        String jurusan = String.valueOf(row.getCell(2));
//                        String kelas = String.valueOf(row.getCell(3));
//                        String email = String.valueOf(row.getCell(4));
//                        String kursus = String.valueOf(row.getCell(5));
//                        DataFormatter df = new DataFormatter();
//                        String tanggal = df.formatCellValue(row.getCell(6));
//                        LocalDate tanggalKursus = LocalDate.parse(tanggal);
//
//                        Integer batch = Integer.parseInt(getValue(row.getCell(7)).toString());
//                        batchSet.add(Long.valueOf(batch));
//
//                        PesertaJadwal newPesertaJadwal = new PesertaJadwal();
//                        newPesertaJadwal.setNama(nama);
//                        newPesertaJadwal.setNpm(npm);
//                        newPesertaJadwal.setJurusan(jurusan);
//                        newPesertaJadwal.setKelas(kelas);
//                        newPesertaJadwal.setEmail(email);
//                        newPesertaJadwal.setKursus(kursus);
//                        newPesertaJadwal.setTanggal(tanggalKursus);
//                        newPesertaJadwal.setCreatedAt(LocalDateTime.now());
//                        newPesertaJadwal.setBatch(batch);
//
//                        Jadwal jadwal = jadwalRepository.findByTanggal(tanggalKursus);
//
//                        if(pesertaRepository.findByNpm(npm).isEmpty()){
//                            Peserta newPeserta = new Peserta();
//                            newPeserta.setNpm(npm);
//                            newPeserta.setNama(nama);
//                            pesertaRepository.save(newPeserta);
//                        }
//
//                        newPesertaJadwal.setJadwal(jadwal);
//
//                        newPesertaJadwal.setBatchId(batchId);
//                        pesertaJadwal.add(newPesertaJadwal);
//                    }
//                    for (Long batch :batchSet) {
//                        BatchNumber batchNumber = new BatchNumber();
//                        batchNumber.setBatchNum(batch);
//                        batchNumberList.add(batchNumber);
//                    }
//                    batchNumberRepository.saveAll(batchNumberList);
//                    //SET BATCH NUM TO DATE_BATCH
//                    Long[] batchSetArr = batchSet.toArray(new Long[batchSet.size()]);
//
//                    try{
//                        workbook.close();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            if(!pesertaJadwal.isEmpty()){
//                pesertaJadwalRepository.saveAll(pesertaJadwal);
//            }
//        }
    }

    @Override
    public Integer excelReader(MultipartFile multipartFile) {
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheet("Pelatihan");
            Iterator<Row> rows = sheet.iterator();
            Integer batchNum = 0;
            DateBatch dateBatch = new DateBatch();

            List<Peserta> pesertaList = new ArrayList<>();
            while (rows.hasNext()) {
                Row currRow = rows.next();
                if(currRow.getRowNum() == 0 || currRow.getRowNum() == 2) {
                    continue;
                }

                Iterator<Cell> cellsInRow = currRow.iterator();
                if(!cellsInRow.hasNext()) break;
                if(currRow.getRowNum() == 1){
                    while (cellsInRow.hasNext()){
                        Cell currentCell = cellsInRow.next();
                        if(currentCell.getColumnIndex() == 1){
                            batchNum = (int) currentCell.getNumericCellValue();
                            break;
                        }
                    }
                    continue;
                }
//                Inisasi PESERTA
                Peserta peserta = new Peserta();

                while (cellsInRow.hasNext()){
                    Cell currentCell = cellsInRow.next();
                    switch (currentCell.getColumnIndex()){
                        case 1 -> {
                            String nama = currentCell.toString();
                            peserta.setNama(nama);
                        }
                        case 2 -> {
                            String npm = currentCell.toString();
                            peserta.setNpm(npm);
                        }
                        case 3 -> {
                            String phoneNum = currentCell.toString();
                            String phoneNumParsed = phoneNum.split("E")[0];
                            peserta.setPhoneNum(phoneNumParsed);
                        }
                        case 4 -> {
                            String email = currentCell.toString();
                            peserta.setEmail(email);
                        }
                        case 5 -> {
                            String kursus = currentCell.toString();
                            peserta.setKursus(kursus);
                        }
                    }
                }
                peserta.setBatch(batchNum);
                pesertaList.add(peserta);
            }
            pesertaRepository.saveAll(pesertaList);
            workbook.close();
            return batchNum;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case ERROR:
                return cell.getErrorCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            case _NONE:
                return null;
            default:
                break;
        }
        return null;
    }
    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }
        return numOfNonEmptyCells;
    }
}

package com.ivallavifahrazi.lpugabsensi.controller;

import com.ivallavifahrazi.lpugabsensi.email.service.EmailService;
import com.ivallavifahrazi.lpugabsensi.models.*;
import com.ivallavifahrazi.lpugabsensi.repository.*;
import com.ivallavifahrazi.lpugabsensi.scheduler.ScheduledTask;
import com.ivallavifahrazi.lpugabsensi.service.StaffService;
import com.ivallavifahrazi.lpugabsensi.service.impl.PesertaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("admin")
public class DateBatchController {
    private static final Logger log = LoggerFactory.getLogger(DateBatchController.class);

    @Autowired
    JadwalRepository jadwalRepo;

    @Autowired
    DateBatchRepository dateBatchRepository;

    @Autowired
    JadwalRepository jadwalRepository;

    @Autowired
    PesertaServiceImpl pesertaService;
    @Autowired
    PesertaJadwalRepository pesertaJadwalRepository;


    @Autowired
    PesertaRepository pesertaRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ScheduledTask scheduledTask;

    @Autowired
    AbsensiJadwalRepository absensiJadwalRepository;

    @Autowired
    RekapanJadwalRepository rekapanJadwalRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    StaffJadwalRepository staffJadwalRepository;

    @Autowired
    FinalReportRepository finalReportRepository;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\uploads";


    Logger logger = LoggerFactory.getLogger(DateBatchController.class);
    //DASHBOARD ADMIN
    @GetMapping()
    public String dashboard(){
        return "admin_dashboard";
    }


    //GET INPUT BATCH
    @GetMapping("/input_batch")
    public String showInputBatch(Model model){
        DateBatch dateBatch = new DateBatch();
        model.addAttribute("date_batch",dateBatch);
//        scheduledTask.checkMail();
        return "input_batch";
    }

    @PostMapping("/input_batch")
    public String postInputBatch(@ModelAttribute("date_batch") DateBatch dateBatch,
                                 @RequestParam("file") MultipartFile file){

        Integer batchNum = pesertaService.excelReader(file);

        LocalDate tanggalMulai = dateBatch.getTanggalMulai();
        LocalDate tanggalAkhir = dateBatch.getTanggalAkhir().plusDays(1);
        dateBatch.setBatchKe(batchNum);
        DateBatch newDateBatch = dateBatchRepository.save(dateBatch);
        Long batchId = newDateBatch.getDateBatchId();

        //Jadwal Part
        List<Jadwal> arrJadwal = new ArrayList<>();
        for(LocalDate date = tanggalMulai; date.isBefore(tanggalAkhir); date = date.plusDays(1)){
            if(date.get(ChronoField.DAY_OF_WEEK) == 5||date.get(ChronoField.DAY_OF_WEEK) == 6 || date.get(ChronoField.DAY_OF_WEEK) == 7){
                continue;
            }
            Jadwal newJadwal = new Jadwal();
            newJadwal.setTanggal(date);
            newJadwal.setBatchId(batchId);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            String message = date.format(dtf);
            newJadwal.setMessage(String.format("Template Email Kursus\nTanggal: %s Mohon Dihadiri!", message));
            arrJadwal.add(newJadwal);
        }

        jadwalRepo.saveAll(arrJadwal);

        List<PesertaJadwal> pesertaJadwalList = new ArrayList<>();
        List<Jadwal> jadwalList = jadwalRepository.findByDateBatch_DateBatchId(batchId)
                .orElseThrow();

        List<Peserta> pesertaList = pesertaRepository.findByBatch(batchNum)
                .orElseThrow();

        List<Peserta> updatePeserta = new ArrayList<Peserta>();

        pesertaList.stream().forEach(peserta -> {
            updatePeserta.add(peserta);
            jadwalList.stream().forEach(jadwal -> {
                PesertaJadwal pesertaJadwal = new PesertaJadwal();
                pesertaJadwal.setBatch(batchNum);
                pesertaJadwal.setPeserta(peserta);
                pesertaJadwal.setJadwal(jadwal);
                pesertaJadwal.setKursus(peserta.getKursus());
                pesertaJadwal.setDateBatch(newDateBatch);
                pesertaJadwalList.add(pesertaJadwal);
            });
        });
        pesertaJadwalRepository.saveAll(pesertaJadwalList);
        updatePeserta.forEach(peserta -> {
            peserta.setDateBatch(newDateBatch);
        });

        pesertaRepository.saveAll(updatePeserta);

        return "redirect:/admin/list_batch/" + batchId;

    }


    @PostMapping("/absensi")
    public String postAbsensiBatch(@ModelAttribute("jadwal") Jadwal jadwal,
                                   @RequestParam("kursus") String kursus){
        Long jadwalId = jadwal.getJadwalId();
        Jadwal jadwalFound = jadwalRepository.findById(jadwalId)
                .orElseThrow(() -> new IllegalArgumentException("no jadwal found"));
        List<AbsensiJadwal> absensiJadwalList = new ArrayList<AbsensiJadwal>();
        List<PesertaJadwal> pesertaJadwalList = pesertaJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, kursus)
                .orElseThrow();
        pesertaJadwalList.stream().forEach(pesertaJadwal -> {
            AbsensiJadwal absensiJadwal = new AbsensiJadwal();
            absensiJadwal.setJadwal(jadwalFound);
            absensiJadwal.setPeserta(pesertaJadwal);
            absensiJadwal.setKursus(kursus);
            absensiJadwal.setNpm(pesertaJadwal.getPeserta().getNpm());
            absensiJadwal.setBatchKe(jadwalFound.getDateBatch().getBatchKe());
            absensiJadwalList.add(absensiJadwal);
        });
        absensiJadwalRepository.saveAll(absensiJadwalList);
        return "redirect:/admin/absensi/" + jadwalId + "/" + kursus;
    }
    @GetMapping("/absensi/{jadwalId}/{kursus}")
    public String getPesertaAbsensi(@PathVariable Long jadwalId,
                                    @PathVariable String kursus,
                                    Model model){
        List<AbsensiJadwal> absensiJadwalList =  absensiJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId,
                        kursus)
                .orElseThrow(() -> new IllegalArgumentException("no peserta found"));
        ListAbsensiJadwal listAbsensiJadwal = new ListAbsensiJadwal(absensiJadwalList);
        Jadwal jadwal = jadwalRepository.findById(jadwalId)
                .orElseThrow(() -> new IllegalArgumentException("no jadwal found"));
        Long batchId = jadwal.getDateBatchId();
        model.addAttribute("listPeserta", listAbsensiJadwal);
        model.addAttribute("jadwalId", jadwalId);
        model.addAttribute("batchId", batchId);
        model.addAttribute("kursus", kursus);
        return "list_peserta_absensi";
    }

    @PostMapping("/absensi/{jadwalId}/{kursus}")
    public String updateKehadiran(@PathVariable Long jadwalId,
                                  @PathVariable String kursus,
                                  @ModelAttribute("listPeserta") ListAbsensiJadwal listAbsensiJadwal){
        List<AbsensiJadwal> updatedAbsensi = new ArrayList<AbsensiJadwal>();
        listAbsensiJadwal.getAbsensiJadwalList().stream().forEach(peserta -> {
            AbsensiJadwal absensiJadwal = absensiJadwalRepository.findByPeserta_Peserta_PesertaIdAndJadwal_JadwalId(peserta.getPeserta().getPeserta().getPesertaId(),
                            jadwalId)
                    .orElseThrow(() -> new IllegalArgumentException("user not found"));
            absensiJadwal.setStatusHadir(peserta.getStatusHadir());
            //DELETE AFTER THIS
            Jadwal jadwal = jadwalRepository.findById(jadwalId)
                    .orElseThrow();
            absensiJadwal.setBatchKe(jadwal.getDateBatch().getBatchKe());
            updatedAbsensi.add(absensiJadwal);
        });
        absensiJadwalRepository.saveAll(updatedAbsensi);
        return  "redirect:/admin/rekapan/" + jadwalId + "/" + kursus; //fix pls
    }

    @GetMapping("/rekapan/{jadwalId}/{kursus}")
    public String rekapanJadwal(@PathVariable Long jadwalId,
                                @PathVariable String kursus,
                                Model model){
        RekapanJadwal rekapanJadwal = new RekapanJadwal();
        List<RekapanJadwal> rekapFound = rekapanJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, kursus)
                .orElseThrow();
        Boolean isRekapFound = false;
        if(rekapFound.size() > 0){
            isRekapFound = true ;
            model.addAttribute("rekapFound", rekapFound.get(0));
        }
        List<AbsensiJadwal> absensiJadwalList = absensiJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId,kursus)
                .orElseThrow(() -> new IllegalArgumentException("jadwal not found"));

        List<Staff> aslabList = staffRepository.findByRole("ASISTEN")
                .orElseThrow();
        if(aslabList.size() > 0) model.addAttribute("aslabList", aslabList);

        List<Staff> asistenInstruktur = staffRepository.findByRole("ASISTEN_INSTRUKTUR")
                .orElseThrow();
        if(asistenInstruktur.size() > 0) model.addAttribute("asistenInstruktur", asistenInstruktur);

        List<Staff> instrukturList = staffRepository.findByRole("INSTRUKTUR")
                .orElseThrow();
        if(instrukturList.size() > 0 ) model.addAttribute("instruktur", instrukturList);

        Staff staff = new Staff();
        Long batchId = jadwalRepository.findById(jadwalId).orElseThrow().getDateBatch().getDateBatchId();

        List<StaffJadwal> staffJadwalList = staffJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, kursus)
                .orElseThrow();

        if(staffJadwalList.size() > 0) model.addAttribute("staffList", staffJadwalList);
        model.addAttribute("rekapan", rekapanJadwal);
        model.addAttribute("jadwalId", jadwalId);
        model.addAttribute("pesertaList", absensiJadwalList);
        model.addAttribute("batchId", batchId);
        model.addAttribute("kursus", kursus);
        model.addAttribute("isRekapFound", isRekapFound);
        model.addAttribute("staff", staff);

        return "rekapan_jadwal";
    }

    @PostMapping("/staff/{jadwalId}/{kursus}")
    public String tambahRekapan(@PathVariable Long jadwalId,
                                @PathVariable String kursus,
                                @ModelAttribute("staff") Staff staff){
        StaffJadwal staffJadwal = new StaffJadwal();
        Staff staffFound = staffRepository.findById(staff.getIdStaff())
                .orElseThrow();
        Jadwal jadwal = jadwalRepository.findById(jadwalId)
                .orElseThrow();
        DateBatch dateBatch = jadwal.getDateBatch();
        staffJadwal.setJadwal(jadwal);
        staffJadwal.setDateBatch(dateBatch);
        staffJadwal.setBatch(dateBatch.getBatchKe());
        staffJadwal.setKursus(kursus);
        staffJadwal.setStaff(staffFound);
        staffJadwalRepository.save(staffJadwal);
        return  "redirect:/admin/rekapan/" + jadwalId + "/" + kursus;
    }

    @PostMapping("/rekapan/{jadwalId}/{kursus}")
    public String tambahRekapan(@PathVariable Long jadwalId,
                                @PathVariable String kursus,
                                @ModelAttribute("rekapan") RekapanJadwal rekapanJadwal,
                                @RequestParam("imageOne") MultipartFile imageOne,
                                @RequestParam("imageOne") MultipartFile imageTwo
    ) throws IOException {
        LocalDate date = LocalDate.now();
        //IMAGE ONE START ---------------------
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        String imageOneFileName = date + "_" + uuid1 + "_" + imageOne.getOriginalFilename();
        Path imageOneDir = Paths.get(UPLOAD_DIRECTORY, imageOneFileName);
        Files.write(imageOneDir, imageOne.getBytes());
        //IMAGE ONE END-------------------------

        String imageTwoFileName = date + "_" + uuid2 + "_" + imageOne.getOriginalFilename();
        Path imageTwoDir = Paths.get(UPLOAD_DIRECTORY, imageTwoFileName);
        Files.write(imageTwoDir, imageTwo.getBytes());
//        -----------------------------------------------------------
        RekapanJadwal newRekapan = new RekapanJadwal();
        DateBatch dateBatch = dateBatchRepository.findById(rekapanJadwal.getDateBatch().getDateBatchId())
                .orElseThrow();

        Jadwal jadwal = jadwalRepository.findById(rekapanJadwal.getJadwal().getJadwalId())
                .orElseThrow();
        newRekapan.setDateBatch(dateBatch);
        newRekapan.setJadwal(jadwal);
        newRekapan.setKursus(kursus);
        newRekapan.setBuktiAbsenSatu(imageOneFileName);
        newRekapan.setBuktiAbsenDua(imageTwoFileName);
        newRekapan.setLinkGoogleDrive(rekapanJadwal.getLinkGoogleDrive());
        rekapanJadwalRepository.save(newRekapan);

        return "redirect:/admin/rekapan/" + jadwalId + "/" + kursus;
    }
    //GET PESERTA BY JADWAL ID
    @GetMapping("list_batch/{batchId}/peserta/jadwal/{jadwalId}")
    public String showPesertaByJadwalId(@PathVariable Long batchId,
                                        @PathVariable Long jadwalId,
                                        Model model){
        List<PesertaJadwal> pesertaJadwalList = pesertaJadwalRepository.findByJadwalId(jadwalId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + jadwalId)));
        Jadwal jadwal = jadwalRepository.findById(jadwalId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + jadwalId)));
        Boolean isGenerated = false; //tambahin isCisco sama isSAP
        Boolean isCiscoExist = true;
        Boolean isSAPExist = true;

        Boolean isCiscoGenerated= false;
        Boolean isSAPGenerated= false;

        List<PesertaJadwal> pesertaCisco = pesertaJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, "CISCO")
                .orElseThrow();
        if(pesertaCisco.size() == 0){
            isCiscoExist = false;
        }

        List<PesertaJadwal> pesertaSAP = pesertaJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, "SAP")
                .orElseThrow();
        if(pesertaSAP.size() == 0){
            isSAPExist = false;
        }

        // isGenerated = isCiso || isSap or if isCisco = false and isSap = false isGenerated kita true in generate 2 link untuk cisco dan sap

        List<AbsensiJadwal> absensiCisco = absensiJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, "CISCO") //bagian ini kita tambahin kursus
                .orElseThrow(() -> new IllegalArgumentException("jadwal not found"));
        if(absensiCisco.size() > 0){
            isCiscoGenerated = true;
            isCiscoExist = false;
        }

        List<AbsensiJadwal> absensiSap = absensiJadwalRepository.findByJadwal_JadwalIdAndKursus(jadwalId, "SAP") //bagian ini kita tambahin kursus
                .orElseThrow(() -> new IllegalArgumentException("jadwal not found"));
        if(absensiSap.size() > 0){
            isSAPGenerated = true;
            isSAPExist = false;
        }

        if(isSAPExist == false && isCiscoExist == false){
            isGenerated = true;
        }
        model.addAttribute("jadwalId", jadwalId);
        model.addAttribute("jadwal",jadwal);
        model.addAttribute("pesertaList", pesertaJadwalList);
        model.addAttribute("batchId",batchId);
        model.addAttribute("isGenerated", isGenerated);
        model.addAttribute("isSapExist", isSAPExist);
        model.addAttribute("isCiscoExist", isCiscoExist);
        model.addAttribute("isCiscoLink", isCiscoGenerated);
        model.addAttribute("isSAPLink", isSAPGenerated);
        return "list_peserta_jadwal";
    }

    //GET ALL BATCH
    @GetMapping("/list_batch")
    public String showAllBatch(Model model){
        List<DateBatch> dateBatches = dateBatchRepository.findAll();
        model.addAttribute("dateBatches", dateBatches);
        return "list_all_batch";
    }
    //POST INPUT BATCH


    //GET LIST BATCH
    @GetMapping("list_batch/{batchId}")
    public String getListBatch(@PathVariable Long batchId,
                               Model model){
        if(jadwalRepo.findByDateBatchId(batchId).isEmpty()){
            return "redirect:/admin/input_batch";
        }

        LocalDateTime tanggal = LocalDateTime.now();
        DateBatch dateBatch = dateBatchRepository.findById(batchId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + batchId)));
        List<Jadwal> jadwalList = jadwalRepo.findByDateBatchId(batchId);
        List<FinalReport> finalReportList = finalReportRepository.findByBatchKe(dateBatch.getBatchKe())
                .orElseThrow();

        List<StaffReport> staffReportList = staffReportRepository.findByBatchKe(dateBatch.getBatchKe())
                .orElseThrow();
        Boolean reportList = false;
        if(finalReportList.size() > 0) {
            model.addAttribute("reportList", finalReportList);
            model.addAttribute("staffList", staffReportList);
            tanggal = finalReportList.get(0).getCreatedAt();
            reportList = true;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatedTanggal = tanggal.format(dtf);
        model.addAttribute("dateBatch", dateBatch);
        model.addAttribute("batch", batchId);
        model.addAttribute("jadwalList", jadwalList);
        model.addAttribute("isReported", reportList);
        model.addAttribute("tanggal", formatedTanggal);


        return "list_batch";
    }

    @GetMapping("list_batch/delete/{batchId}")
    public String deleteBatch(@PathVariable Long batchId) {
        if(dateBatchRepository.findById(batchId).isPresent()) {
            DateBatch dateBatch = dateBatchRepository.findById(batchId)
                    .orElseThrow();
            dateBatchRepository.delete(dateBatch);
        }
        return "redirect:/admin/list_batch";
    }

    @GetMapping("delete/report/{batchId}")
    public String deleteReport(@PathVariable Long batchId) {
        if(dateBatchRepository.findById(batchId).isPresent()) {
            List<StaffReport> staffReports = staffReportRepository.findByDateBatch_DateBatchId(batchId)
                    .orElseThrow();
            List<FinalReport> finalReportList = finalReportRepository.findByDateBatch_DateBatchId(batchId)
                    .orElseThrow();
            staffReportRepository.deleteAll(staffReports);
            finalReportRepository.deleteAll(finalReportList);
        }
        return "redirect:/admin/list_batch/" + batchId;
    }

    @Autowired StaffReportRepository staffReportRepository;
    @PostMapping("/report/{batchId}")
    public String finalReportGenerate(@PathVariable Long batchId){
        DateBatch dateBatch = dateBatchRepository.findById(batchId).orElseThrow();
        Integer batchKe = dateBatch.getBatchKe();
        List<Peserta> pesertaList = pesertaRepository.findByBatch(batchKe).orElseThrow();
        List<Staff> staffList = staffRepository.findAll();
        List<FinalReport> finalReportList = new ArrayList<>();
        LocalDateTime tanggal = LocalDateTime.now();

        List<StaffReport> staffReports = new ArrayList<>();
        List<Jadwal> jadwalList = jadwalRepository.findByDateBatch_DateBatchId(batchId)
                .orElseThrow();
        Integer limitMasuk = jadwalList.size() - 2;

        if(staffList.size() > 0) {
            staffList.forEach(staff -> {
                AtomicReference<Integer> jumlahJaga = new AtomicReference<>();
                StaffReport staffReport = new StaffReport();
                jumlahJaga.set(0);
                staffJadwalRepository.findByStaff_IdStaff(staff.getIdStaff()).orElseThrow().stream().forEach(staffJadwal -> {
                    jumlahJaga.set(jumlahJaga.get() + 1);
                });
                if(jumlahJaga.get() > 0) {
                    staffReport.setBatchKe(batchKe);
                    staffReport.setDateBatch(dateBatch);
                    staffReport.setStaff(staff);

                    staffReport.setJumlahJaga(jumlahJaga.get());

                    staffReport.setCreatedAt(tanggal);
                    staffReports.add(staffReport);
                }

            });
        }
        if(pesertaList.size() > 0){
            pesertaList.stream().forEach(peserta -> {
                AtomicReference<Integer> hadir = new AtomicReference<>();
                AtomicReference<Integer> tidakHadir = new AtomicReference<>();
                AtomicReference<Boolean> statusKehadiran = new AtomicReference<>();
                FinalReport finalReport = new FinalReport();
                hadir.set(0);
                tidakHadir.set(0);
                statusKehadiran.set(true);
                absensiJadwalRepository.findByBatchKeAndNpm(batchKe, peserta.getNpm()).orElseThrow().stream().forEach(absensiJadwal -> {
                    if(absensiJadwal.getStatusHadir() == true){
                        hadir.set(hadir.get() + 1);
                    }else if(absensiJadwal.getStatusHadir() == false){
                        tidakHadir.set(tidakHadir.get() + 1);
                    }
                });
                if(hadir.get() <= limitMasuk){
                    statusKehadiran.set(false);
                }
                finalReport.setBatchKe(batchKe);
                finalReport.setDateBatch(dateBatch);
                finalReport.setPeserta(peserta);
                finalReport.setJumlahHadir(hadir.get());
                finalReport.setJumlahTidakHadir(tidakHadir.get());
                finalReport.setStatusKehadiran(statusKehadiran.get());
                finalReport.setCreatedAt(tanggal);
                finalReportList.add(finalReport);
            });
        }
        staffReportRepository.saveAll(staffReports);
        finalReportRepository.saveAll(finalReportList);
        return "redirect:/admin/list_batch/" + batchId;
    }

    // DELETE BATCH ID
//    @GetMapping("list_batch/batch/delete/{batchId}")
//    public String deleteBatchById(@PathVariable long batchId){
//        DateBatch batch = dateBatchRepository.findById(batchId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + batchId)));
//
//    }

    //    DELETE JADWAL ID
    @GetMapping("list_batch/jadwal/delete/{jadwalId}")
    public String deleteJadwalById(@PathVariable long jadwalId){
        Jadwal jadwal = jadwalRepo.findById(jadwalId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + jadwalId)));
        Long batch = jadwal.getDateBatchId();
        jadwalRepo.delete(jadwal);
        return "redirect:/admin/list_batch/" + batch;
    }




    //GET PESERTA BY BATCH ID
    @GetMapping("list_batch/peserta/batch/{batchId}")
    public String showPesertaByBatchId(@PathVariable Long batchId,
                                       Model model){
//        List<PesertaJadwal> pesertaJadwalList = pesertaJadwalRepository.findByDateBatchId(batchId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + batchId)));
        DateBatch dateBatch = dateBatchRepository.findById(batchId).orElseThrow(() -> new IllegalArgumentException(("Invalid Jadwal Id" + batchId)));
        List<Peserta> pesertaList = pesertaRepository.findByBatch(dateBatch.getBatchKe())
                .orElseThrow();
        model.addAttribute("dateBatch", dateBatch);
        model.addAttribute("pesertaList", pesertaList);
        model.addAttribute("batchId", batchId);



        return "list_peserta_batch";
    }



    //GET ALL PESERTA
    @GetMapping("peserta")
    public String showAllPeserta(Model model,
                                 @RequestParam(name = "npm", required = false) String npm){
        List<Peserta> pesertaList;
        if(npm != null && npm.length() > 0){
            pesertaList = pesertaRepository.findByNpm(npm)
                    .orElseThrow();
        }else{
            pesertaList = pesertaRepository.findAll();

        }
        Peserta peserta = new Peserta();
        model.addAttribute("pesertaList", pesertaList);
        model.addAttribute("peserta", peserta);
        return "list_all_peserta";
    }

    //POST PESERTA
    @PostMapping("peserta")
    public String postPeserta(@ModelAttribute("peserta") Peserta peserta){
        String npmPeserta = peserta.getNpm();
        return "redirect:/admin/peserta?npm=" + npmPeserta;
    }
    //GET DETAIL JADWAL PESERTA
    @GetMapping("list_peserta/peserta/{pesertaNpm}")
    public String detailJadwalPeserta(@PathVariable String pesertaNpm,
                                      Model model){
        List<PesertaJadwal> listJadwal = pesertaJadwalRepository.findByPeserta_Npm(pesertaNpm)
                .orElseThrow(() -> new IllegalArgumentException());

        model.addAttribute("npm", pesertaNpm);
        model.addAttribute("jadwalList", listJadwal);

        return "list_detail_peserta";
    }

    //Staff
    @GetMapping("staff")
    public String getStaffPage(Model model){
        Staff staff = new Staff();
        List<Staff> staffList = staffRepository.findAll();
        model.addAttribute("staff", staff);
        model.addAttribute("staffList", staffList);
        return "list_staff";
    }
    @Autowired
    private StaffService staffService;
    @PostMapping("staff")
    public String addStaff(@ModelAttribute("staff") Staff staff){
        Staff newStaff = new Staff();
        newStaff.setNama(staff.getNama());
        newStaff.setEmail(staff.getEmail());
        newStaff.setPassword(staff.getPassword());

        String role = null;
        Integer roleInput = Integer.valueOf(staff.getRole());
        if(roleInput == 1) role = "ASISTEN";
        else if (roleInput == 2) role = "ASISTEN_INSTRUKTUR";
        else if (roleInput == 3) role = "INSTRUKTUR" ;
        newStaff.setRole(role);
        staffService.saveStaff(newStaff);
        return "redirect:/admin/staff";
    }

    //Edit Staff
    @GetMapping("staff/edit/{idStaff}")
    public String getEditStaff(@PathVariable("idStaff") Long idStaff,
                            Model model){
        Staff staff = staffRepository.findById(idStaff)
                .orElseThrow();

        model.addAttribute("staff", staff);
        model.addAttribute("idStaff", idStaff);

        return "edit_staff";
    }

    @PostMapping("staff/edit/{idStaff}")
    public String editStaff(@PathVariable("idStaff") Long idStaff,
                            @ModelAttribute("staff") Staff staff){
        Staff staffFound = staffRepository.findById(idStaff)
                .orElseThrow();
        String role = null;
        Integer roleInput = Integer.valueOf(staff.getRole());
        if(roleInput == 1) role = "ASISTEN";
        else if (roleInput == 2) role = "ASISTEN_INSTRUKTUR";
        else if (roleInput == 3) role = "INSTRUKTUR" ;
        staffFound.setRole(role);
        staffFound.setEmail(staff.getEmail());
        staffFound.setNama(staff.getNama());
        staffRepository.save(staffFound);
        return "redirect:/admin/staff";
    }

    @GetMapping("staff/delete/{idStaff}")
    public String deleteStaff(@PathVariable("idStaff") Long idStaff){
        Staff staff = staffRepository.findById(idStaff)
                .orElseThrow();

        staffRepository.delete(staff);
        return "redirect:/admin/staff";
    }


}

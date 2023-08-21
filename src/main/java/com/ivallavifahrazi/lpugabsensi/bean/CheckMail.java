package com.ivallavifahrazi.lpugabsensi.bean;

import com.ivallavifahrazi.lpugabsensi.email.service.EmailService;
import com.ivallavifahrazi.lpugabsensi.models.Jadwal;
import com.ivallavifahrazi.lpugabsensi.models.PesertaJadwal;
import com.ivallavifahrazi.lpugabsensi.repository.JadwalRepository;
import com.ivallavifahrazi.lpugabsensi.repository.PesertaJadwalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CheckMail {
    @Autowired
    JadwalRepository jadwalRepository;

    @Autowired
    PesertaJadwalRepository pesertaJadwalRepository;


    @Autowired
    EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(CheckMail.class);


    public void checkerMail(){
        LocalDate tanggalSkrg = LocalDate.now();
        Jadwal jadwal = jadwalRepository.findByTanggal(tanggalSkrg);
        if(jadwal != null){
            List<PesertaJadwal> pesertaJadwalList = pesertaJadwalRepository.findByJadwalId(jadwal.getJadwalId()).orElseThrow(() -> new IllegalArgumentException());
            pesertaJadwalList.forEach(pesertaJadwal -> {
                emailService.sendEmail(pesertaJadwal.getPeserta().getEmail(), "Test email absesni", jadwal.getMessage());
            });
        }else{
            logger.info("No jadwal available today");
        }
    }
}

package com.ivallavifahrazi.lpugabsensi.repository;

import com.ivallavifahrazi.lpugabsensi.models.Peserta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PesertaRepository extends JpaRepository<Peserta, Long> {
   Optional<List<Peserta>> findByNpm(String npm);

    Optional<List> findByBatch(Integer batch);
}

package com.ivallavifahrazi.lpugabsensi.service;

import com.ivallavifahrazi.lpugabsensi.models.Staff;
import com.ivallavifahrazi.lpugabsensi.models.StaffDTO;

import java.util.List;

public interface StaffService {
    void saveUser(StaffDTO staffDTO);

    void saveStaff(Staff staff);

    Staff findUserByEmail(String email);

    List<StaffDTO> findAllUsers();
}

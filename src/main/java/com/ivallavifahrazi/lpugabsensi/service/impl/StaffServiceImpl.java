package com.ivallavifahrazi.lpugabsensi.service.impl;

import com.ivallavifahrazi.lpugabsensi.models.Role;
import com.ivallavifahrazi.lpugabsensi.models.Staff;
import com.ivallavifahrazi.lpugabsensi.models.StaffDTO;
import com.ivallavifahrazi.lpugabsensi.repository.RolesRepository;
import com.ivallavifahrazi.lpugabsensi.repository.StaffRepository;
import com.ivallavifahrazi.lpugabsensi.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {
    private StaffRepository staffRepository;

    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StaffServiceImpl(StaffRepository staffRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(StaffDTO staffDTO) {
        Role role = rolesRepository.findByName("ROLE_ADMIN");
        if(role == null) {
            role = checkRoleExist();
        }

        Staff staff = new Staff(staffDTO.getNama(),
                staffDTO.getEmail(), staffDTO.getUsername(), passwordEncoder.encode(staffDTO.getPassword()),
                "ADMINISTRASI", Arrays.asList(role));

        staffRepository.save(staff);

    }

    @Override
    public void saveStaff(Staff staff) {
        Role role = rolesRepository.findByName("ROLE_STAFF");
        if(role == null) {
            role = checkStaffRole();
        }
        Staff newStaff = new Staff(staff.getNama(),
                staff.getEmail(), staff.getUsername(),
                passwordEncoder.encode(staff.getPassword()), staff.getRole(), Arrays.asList(role));
        staffRepository.save(newStaff);
    }

    @Override
    public Staff findUserByEmail(String email) {
        return staffRepository.findByEmail(email);
    }

    @Override
    public List<StaffDTO> findAllUsers() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream().map((staff -> mapToStaffDTO(staff))).collect(Collectors.toList());
    }

    private StaffDTO mapToStaffDTO(Staff staff) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setNama(staff.getNama());
        staffDTO.setEmail(staff.getEmail());
        staffDTO.setRole(staff.getRole());
        return staffDTO;
    }
    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return rolesRepository.save(role);
    }
    private Role checkStaffRole() {
        Role role = new Role();
        role.setName("ROLE_STAFF");
        return rolesRepository.save(role);
    }
}

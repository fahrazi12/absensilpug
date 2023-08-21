package com.ivallavifahrazi.lpugabsensi.controller;

import com.ivallavifahrazi.lpugabsensi.models.Staff;
import com.ivallavifahrazi.lpugabsensi.models.StaffDTO;
import com.ivallavifahrazi.lpugabsensi.repository.StaffRepository;
import com.ivallavifahrazi.lpugabsensi.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private StaffService staffService;

    public AuthController(StaffService staffService) {
        this.staffService = staffService;
    }

    @Autowired
    StaffRepository staffRepository;
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerStaff(Model model) {
        StaffDTO staffDTO = new StaffDTO();
        model.addAttribute("staff", staffDTO);
        return "register";
    }
    @PostMapping("/register")
    public String registration(
            @ModelAttribute("staff")StaffDTO staff,
            BindingResult result,
            Model model) {
        Staff existingStaff = staffRepository.findByEmail(staff.getEmail());
        if(existingStaff != null && existingStaff.getEmail() != null && !existingStaff.getEmail().isEmpty()) {
            result.rejectValue("email", null, "Email staff sudah terdaftar!");
        }
        if (result.hasErrors()) {
            model.addAttribute("staff", staff);
            return "/login";
        }
        staffService.saveUser(staff);
        return "redirect:/login";
    }
}

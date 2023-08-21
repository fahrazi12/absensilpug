package com.ivallavifahrazi.lpugabsensi.service;

import com.ivallavifahrazi.lpugabsensi.models.Role;
import com.ivallavifahrazi.lpugabsensi.models.Staff;
import com.ivallavifahrazi.lpugabsensi.repository.StaffRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private StaffRepository staffRepository;

    public CustomUserDetailService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Staff staff = staffRepository.findByEmail(email);
        if(staff != null) {
            return new org.springframework.security.core.userdetails.User(staff.getEmail(),
                    staff.getPassword(),
                    mapRolesToAuthorities(staff.getRoles()));
        }else {
            throw new UsernameNotFoundException("Invalid email!");
        }
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection< ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}

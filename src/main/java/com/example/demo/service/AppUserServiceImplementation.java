package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.model.Role;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImplementation implements AppUserService, UserDetailsService {
   private final AppUserRepository appUserRepository;
   private final RoleRepository roleRepository;
   private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user=appUserRepository.findByEmail(email);
        if(user == null){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("user found in the database: {}", email);
        }
        //collection of authorities and pass grand authority to each role
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    // authentication is verifying you are who you say you are
    //Authorization Decides if you have permission to access a resource
    //method: login form ,Http authentication,Custom auth.method
    //Access Controls URLs, Access Control List (for Authorization)

    @Override
    public AppUser saveAppUser(AppUser appUser) {
        log.info("saving new user {} to the database",appUser.getEmail());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {}to the database",role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToAppUser(String email, String name) {
        log.info("adding role {} to user {}",name,email);
        AppUser appUser=appUserRepository.findByEmail(email);
        Role role=roleRepository.findByName(name);
        appUser.getRoles().add(role);


    }

    @Override
    public AppUser getAppUser(String email) {
        log.info("fetching user {}", email);
        return appUserRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getAppUser() {
        log.info("fetching all users");
        return appUserRepository.findAll();
    }


}

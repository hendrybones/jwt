package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService {
    AppUser saveAppUser(AppUser appUser);
    Role saveRole(Role role);
    void  addRoleToAppUser(String email,String name);
    AppUser getAppUser(String email);
    List<AppUser> getAppUser();
}

package com.lfcraleigh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepo;

    public Administrator getCurrentAdmin(String username){
        return adminRepo.findAdministratorByUsername(username);
    }

    public void saveAdmin(Administrator admin){
        adminRepo.save(admin);
    }
}

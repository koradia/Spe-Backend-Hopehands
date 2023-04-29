package com.example.hopehands.service.auth;

import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.repo.auth.CredsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CredsRepo credsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Creds dc = credsRepo.findByUsername(username);
//        String dBuserName=dc.getEmail();
//        System.out.println(dBuserName+ " form DocUserDetailService ");
        if(dc == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
//        return null;
        return new org.springframework.security.core.userdetails.User(dc.getUsername(),dc.getPassword(), new ArrayList<>());
    }
}

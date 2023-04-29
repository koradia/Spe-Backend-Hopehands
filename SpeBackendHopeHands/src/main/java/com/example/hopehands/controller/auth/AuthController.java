package com.example.hopehands.controller.auth;

import com.example.hopehands.model.auth.AuthRequest;
import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.repo.auth.CredsRepo;
import com.example.hopehands.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/try/something")
    public ResponseEntity<String> welcome(@RequestHeader("Authorization") String token) throws Exception{
        if(token==null) System.out.println("Hello this is not working");;
        try{
        token= token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        System.out.println("Kall aya tha yaha par");
        return ResponseEntity.ok("hello "+uname);
        }catch (Exception e){
            throw e;
        }

    }

    @GetMapping("/only/admin")
    public String onlyAdmin() {
        return "hello";
    }

    @PostMapping("/org/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        if (authRequest.getRole() != credsRepo.findByUsername(authRequest.getUsername()).getRole())
            throw new Exception("Not Organisation");
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("Invalid Username/Password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
//        return "Nahi milega";
    }


    @PostMapping("/org/register")
    public Integer newReg(@RequestBody Creds registerRequest,@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        try{
            Creds ct = credsRepo.save(registerRequest);
            return ct.getId();
        }catch (Exception ex){
//            System.out.println(ex);
            throw new Exception(ex);
        }

    }

    @PostMapping("/admin/authenticate")
    public ResponseEntity<MyObject> generateTokenforAdmin(@RequestBody AuthRequest authRequest) throws Exception{
        if(authRequest.getRole() != credsRepo.findByUsername(authRequest.getUsername()).getRole() ) {
//            throw new Exception("Not Admin");
            MyObject obj = new MyObject();
            return new ResponseEntity<>(
                    obj,
                    HttpStatus.BAD_REQUEST);
        }
        System.out.println(authRequest.getUsername());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        String xyz=jwtUtil.generateToken(authRequest.getUsername());
        Integer temp =credsRepo.findByUsername(authRequest.getUsername()).getId();
        MyObject obj = new MyObject();
        obj.setId(temp);
        obj.setToken(xyz);
        return new ResponseEntity<>(
                obj,
                HttpStatus.OK);
    }

    @PostMapping("/user/register")
    public Integer newUserReg(@RequestBody Creds registerRequest) throws Exception{
        try{
            Creds ct = credsRepo.save(registerRequest);
            return ct.getId();
        }catch (Exception ex){
//            System.out.println(ex);
            throw new Exception(ex);
        }

    }

    @PostMapping("/user/authenticate")
    public String generateUserToken(@RequestBody AuthRequest authRequest) throws Exception{
        if(authRequest.getRole()!=credsRepo.findByUsername(authRequest.getUsername()).getRole()) throw new Exception("Not Organisation");
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }
    static class MyObject {
        private int id;
        private String token;

        // Getters and setters for the object's properties
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

package com.drm.mitra.authentication.controller;

import com.drm.mitra.authentication.entity.Roles;
import com.drm.mitra.authentication.entity.UserData;
import com.drm.mitra.authentication.exception.ResourceNotFoundException;
import com.drm.mitra.authentication.exception.RoleNameNotEmptyException;
import com.drm.mitra.authentication.exception.UserDataException;
import com.drm.mitra.authentication.exception.UserNameNotEmptyException;
import com.drm.mitra.authentication.model.JwtRequest;
import com.drm.mitra.authentication.model.JwtResponse;
import com.drm.mitra.authentication.model.UserDataModel;
import com.drm.mitra.authentication.service.UserDataService;
import com.drm.mitra.authentication.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserAuthenticateController {

    private UserDataService userDataService;

    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserAuthenticateController(UserDataService userDataService,JWTUtility jwtUtility) {
        this.userDataService = userDataService;
        this.jwtUtility = jwtUtility;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getWelcomeMsg() {
        return new ResponseEntity<>("Welcome to DRM MITRA Health Card App", HttpStatus.OK);
    }

    @PostMapping("/generateOTP")
    public ResponseEntity<Object> generateOTP(@RequestBody UserDataModel userDataModel) throws Exception {
        log.info("Inside generate otp method controller",userDataModel.getUsername(),userDataModel.getPassword());
        return userDataService.generateOTP(userDataModel);
    }

    @PostMapping("/validateOTP")
    public ResponseEntity<Object> login(@RequestBody JwtRequest jwtRequests) throws Exception {
        log.info("Inside validateOTP otp method controller",jwtRequests.getUsername(),jwtRequests.getPassword());
        ResponseEntity<Object> responseEntity = userDataService.validateOTP(jwtRequests);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                jwtRequests.getUsername(),
                                jwtRequests.getPassword()
                        )
                );
            } catch(BadCredentialsException e){
                throw new Exception("INVALID_CREDENTIALS", e);
            }
            final UserDetails userDetails
                    = userDataService.loadUserByUsername(jwtRequests.getUsername());
            final String token =
                    jwtUtility.generateToken(userDetails);
            JwtResponse response = new JwtResponse(token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return responseEntity;
        }
    }

    @GetMapping("/get-users-list")
    public ResponseEntity<List<UserData>> getAllUsers() throws ResourceNotFoundException {
        log.info("Inside getAllUsers controller method");
            return new ResponseEntity<>(userDataService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/get-users-by-name/{userName}")
    public ResponseEntity<UserData> getUserByName(@PathVariable("userName") String userName) throws UserNameNotEmptyException {
        log.info("Inside getUserByName controller method");
            return new ResponseEntity<>(userDataService.getUserByName(userName),HttpStatus.OK);
    }

    @PostMapping("/save-user")
    public ResponseEntity<UserData> saveUser(@Valid @RequestBody UserData userData) throws MethodArgumentNotValidException {
        log.info("Inside saveUser controller method");
        return new ResponseEntity<>(userDataService.saveUser(userData),HttpStatus.CREATED);
    }

    @GetMapping("/get-roles-list")
    public ResponseEntity<List<Roles>> getAllRoles() throws ResourceNotFoundException {
        log.info("Inside getAllRoles controller method");
        return new ResponseEntity<>(userDataService.getAllRoles(), HttpStatus.OK);
    }

    @GetMapping("/get-roles-by-name/{rolesName}")
    public ResponseEntity<Roles> getRolesByName(@PathVariable("rolesName") String rolesName) throws RoleNameNotEmptyException {
        log.info("Inside getRolesByName controller method");
        return new ResponseEntity<>(userDataService.getRolesByName(rolesName),HttpStatus.OK);
    }

    @PostMapping("/save-role")
    public ResponseEntity<Roles> saveRoles(@Valid @RequestBody Roles roles) throws MethodArgumentNotValidException {
        log.info("Inside saveRoles controller method");
        return new ResponseEntity<>(userDataService.saveRoles(roles),HttpStatus.CREATED);
    }
}

package com.drm.mitra.authentication.service;

import com.drm.mitra.authentication.entity.Provider;
import com.drm.mitra.authentication.entity.UserData;
import com.drm.mitra.authentication.exception.ResourceNotFoundException;
import com.drm.mitra.authentication.exception.UserNameNotEmptyException;
import com.drm.mitra.authentication.model.JwtRequest;
import com.drm.mitra.authentication.model.UserDataModel;
import com.drm.mitra.authentication.repository.UserDataRepository;
import com.drm.mitra.authentication.utility.OTPUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserDataService implements UserDetailsService {

    private UserDataRepository userDataRepository;

    private OTPUtility otpUtility;

    public UserDataService(UserDataRepository userDataRepository, OTPUtility otpUtility) {
        this.userDataRepository = userDataRepository;
        this.otpUtility = otpUtility;
    }

    public List<UserData> getAllUsers() throws ResourceNotFoundException {
        log.info("Inside getAllUsers service method");
         List<UserData> userData = userDataRepository.findAll();
         if(userData.isEmpty() || userData == null) {
             throw new ResourceNotFoundException("No data found");
         } else {
             return userData;
         }
    }

    public UserData getUserByName(String userName) throws UsernameNotFoundException, UserNameNotEmptyException {
        log.info("Inside getUserByName service method");
        if(userName == null) {
            throw new UserNameNotEmptyException("User Should not be empty or null");
        }
        UserData userData = userDataRepository.findByUserName(userName);
        if(userData == null) {
            throw new UsernameNotFoundException("No such User Found");
        } else {
            return userData;
        }
    }

    public UserData saveUser(UserData userData) {
        log.info("Inside saveUser service method");
        return userDataRepository.save(userData);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername service method",username);
        UserData userData = userDataRepository.findByUserName(username);
        return new User(userData.getUsername(), userData.getPassword(), new ArrayList<>());
    }

    public ResponseEntity<Object> generateOTP(UserDataModel userDataModel) throws Exception {
        log.info("Inside generate otp method service class",userDataModel.getUsername());
        UserData user_Data = userDataRepository.findByUserName(userDataModel.getUsername());
        if (user_Data == null) {
            UserData userData = new UserData();
            userData.setActive(true);
            userData.setDisable(false);
            userData.setRole("USER");
            userData.setUsername(userDataModel.getUsername());
            userData.setPassword(userDataModel.getUsername());
            userDataRepository.save(userData);
            return otpUtility.generateOTP(userData);
        } else {
            return otpUtility.generateOTP(user_Data);
        }
    }

    public ResponseEntity<Object> validateOTP(JwtRequest jwtRequest) throws Exception {
        log.info("Inside validateOTP method service class",jwtRequest.getUsername());
        return otpUtility.validateOTP(jwtRequest);
    }

    public void processOAuthPostLogin(String username) {
        log.info("Inside (GOOGLE) processOAuthPostLogin method service class",username);
        UserData existUser = userDataRepository.findByUserName(username);
        if (existUser == null) {
            UserData userData = new UserData();
            userData.setUsername(username);
            userData.setPassword(username);
            userData.setProvider(Provider.GOOGLE);
            userData.setActive(true);
            userData.setDisable(false);
            userData.setRole("USER");
            userDataRepository.save(userData);
        }
    }
}

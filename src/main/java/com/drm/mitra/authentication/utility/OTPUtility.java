package com.drm.mitra.authentication.utility;

import com.drm.mitra.authentication.config.ConfigProperties;
import com.drm.mitra.authentication.entity.UserData;
import com.drm.mitra.authentication.model.JwtRequest;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OTPUtility {

    @Autowired
    private ConfigProperties configProperties;

    private static final String PHONE_NUMBER_PREFIX = "+91";

    public ResponseEntity<Object> generateOTP(UserData userData) throws Exception {
        try {
            log.info("Inside generate otp method OTPUtility class",userData.getPassword(),userData.getUsername());
            Verification verification = Verification.creator(
                            configProperties.getServiceId(),
                    PHONE_NUMBER_PREFIX+userData.getUsername(), "sms").create();
            System.out.println(verification.getStatus());
            return  new ResponseEntity<>(verification,HttpStatus.OK);
        } catch (Exception  e) {
            throw new Exception("Error while sending OTP",e);
        }
    }

    public ResponseEntity<Object> validateOTP(JwtRequest jwtRequest) throws Exception {
        try {
            log.info("Inside validateOTP method OTPUtility class",jwtRequest.getUsername(),jwtRequest.getPassword());
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        configProperties.getServiceId(),
                        jwtRequest.getCode())
                .setTo(PHONE_NUMBER_PREFIX+jwtRequest.getUsername()).create();
        System.out.println(verificationCheck.getStatus());
        return new ResponseEntity<Object>(verificationCheck,HttpStatus.OK);
        } catch (Exception  e) {
            throw new Exception("Error while sending OTP",e);
        }
    }
}

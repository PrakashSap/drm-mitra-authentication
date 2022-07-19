package com.drm.mitra.authentication.config;

import com.drm.mitra.authentication.model.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import com.drm.mitra.authentication.repository.UserDataRepository;
import com.drm.mitra.authentication.service.UserDataService;
import com.drm.mitra.authentication.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserDataService userDataService;

    private String homeUrl = "http://localhost:9001/";

    @Autowired
    private JWTUtility jwtUtility;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        if (response.isCommitted()) {
            return;
        }
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        userDataService.processOAuthPostLogin(oauthUser.getEmail());
        UserDetails userDetails
                = userDataService.loadUserByUsername(oauthUser.getEmail());
        String token = jwtUtility.generateToken(userDetails);
        String redirectionUrl = UriComponentsBuilder.fromUriString(homeUrl)
                .queryParam("auth_token", token)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectionUrl);
    }
}

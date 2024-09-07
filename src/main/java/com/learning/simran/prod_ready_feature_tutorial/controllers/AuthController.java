package com.learning.simran.prod_ready_feature_tutorial.controllers;

import com.learning.simran.prod_ready_feature_tutorial.DTOs.LoginDto;
import com.learning.simran.prod_ready_feature_tutorial.DTOs.LoginResponseDTO;
import com.learning.simran.prod_ready_feature_tutorial.DTOs.SignUpDto;
import com.learning.simran.prod_ready_feature_tutorial.DTOs.UserDto;
import com.learning.simran.prod_ready_feature_tutorial.services.AuthService;
import com.learning.simran.prod_ready_feature_tutorial.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto){
        UserDto userDto = userService.signUp(signUpDto);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        LoginResponseDTO loginResponseDTO = authService.login(loginDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));  //can be used for https
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();

        String refreshToken = Arrays.stream(cookies).filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(a -> a.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found in cookies"));

        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDTO);
    }

}

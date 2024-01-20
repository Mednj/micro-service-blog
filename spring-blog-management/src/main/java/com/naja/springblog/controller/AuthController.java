package com.naja.springblog.controller;

import javax.validation.Valid;

import com.naja.springblog.payload.AppResponse;
import com.naja.springblog.payload.LoginRequest;
import com.naja.springblog.payload.SignInRequest;
import com.naja.springblog.payload.SuccessResponse;
import com.naja.springblog.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @ApiOperation(value = "Authenticate a registered User")
    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticationUser(@Valid @RequestBody LoginRequest LoginRequest) {
        return ResponseEntity.ok().body(authService.authenticateUser(LoginRequest));
    }

    @ApiOperation(value = "Sign up new User")
    @PostMapping(value = "/signup")
    public ResponseEntity<AppResponse> registerUser(@Valid @RequestBody SignInRequest signInRequest) {
        authService.saveUser(signInRequest);
        return ResponseEntity.ok().body(new SuccessResponse("User Created Successfully"));
    }

}

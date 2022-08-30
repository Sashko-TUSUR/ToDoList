package com.example.ToDoList.Controllers;

import com.example.ToDoList.Exception.TokenRefreshException;
import com.example.ToDoList.Model.RefreshToken;
import com.example.ToDoList.Repository.RoleRepository;
import com.example.ToDoList.Repository.UserRepository;
import com.example.ToDoList.Security.JwtUtils;
import com.example.ToDoList.payload.Request.LoginRequest;
import com.example.ToDoList.payload.Request.SignUpRequest;
import com.example.ToDoList.payload.Request.TokenRefreshRequest;
import com.example.ToDoList.payload.Response.JwtResponse;
import com.example.ToDoList.payload.Response.JwtResponseSignUp;
import com.example.ToDoList.payload.Response.TokenRefreshResponse;
import com.example.ToDoList.service.RefreshTokenService;
import com.example.ToDoList.service.UserDetailsImpl;
import com.example.ToDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken = jwtUtils.saveRefreshToken(userDetails.getEmail());
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(refreshToken);
        return ResponseEntity.ok()
                .body(new JwtResponse(jwt,refreshToken.getToken(),
                        userRepository.findById(userDetails.getId())));



    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return jwtUtils.findByToken(requestRefreshToken)
                .map(jwtUtils::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtTokenFromEmail(userDetails.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody SignUpRequest signUpRequest) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = jwtUtils.saveRefreshToken(userDetails.getEmail());
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(refreshToken);

        userService.createUser(signUpRequest);
        String jwt = jwtUtils.generateJwtTokenSignUp(signUpRequest.getEmail());
        System.out.print("пользователь создан");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, String.valueOf(jwtCookie))
                        .body(ResponseEntity.ok(new JwtResponseSignUp(jwt)));
    }


    /*
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = refreshTokenService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

     */





}

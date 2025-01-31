package com.evanmaulanaibrahim.backenddev.service;

import com.evanmaulanaibrahim.backenddev.dto.request.LoginRequest;
import com.evanmaulanaibrahim.backenddev.dto.request.RegisterRequest;
import com.evanmaulanaibrahim.backenddev.dto.response.ApiDataResponseBuilder;
import com.evanmaulanaibrahim.backenddev.dto.response.JwtResponse;
import com.evanmaulanaibrahim.backenddev.dto.response.MessageResponse;
import com.evanmaulanaibrahim.backenddev.model.User;
import com.evanmaulanaibrahim.backenddev.repository.UsersRepository;
import com.evanmaulanaibrahim.backenddev.security.jwt.JwtUtils;
import com.evanmaulanaibrahim.backenddev.security.service.UserDetailsImplement;
import io.github.bucket4j.Bucket;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lib.i18n.utility.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UsersService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RateLimiterService rateLimiterService;

    final HttpStatus statusOK = HttpStatus.OK;

    @Transactional
    public MessageResponse register(RegisterRequest request) {
        String key = request.getUsername();
        Bucket bucket = rateLimiterService.resolveBucket(key);

        if (!bucket.tryConsume(1)) {
            return new MessageResponse("Rate limit exceeded. Try again later.", HttpStatus.TOO_MANY_REQUESTS.value(), "ERROR");
        }

        Set<ConstraintViolation<RegisterRequest>> constraintViolations = validator.validate(request);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<RegisterRequest> firstViolation = constraintViolations.iterator().next();
            String errorMessage = firstViolation.getMessage();
            return new MessageResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), "ERROR");
        }

        log.info("Received registration request: {}", request);

        if (userRepository.existsByUsername(request.getUsername())) {
            String errorMessage = messageUtil.get("application.error.already-exist.user");
            return new MessageResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), "ERROR");
        }

        if (!request.getPassword().equals(request.getRetypePassword())) {
            String errorMessage = messageUtil.get("application.error.password-not-match.user");
            return new MessageResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), "ERROR");
        }

        if (request.getPassword().length() < 6) {
            String errorMessage = messageUtil.get("application.error.password-validation.user");
            return new MessageResponse(errorMessage, HttpStatus.BAD_REQUEST.value(), "ERROR");
        }

        User user = User.builder()
                .username(request.getUsername())
                .fullname(request.getFullname())
                .email(request.getEmail())
                .password(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()))
                .role("User")
                .isDeleted(false)
                .build();

        userRepository.save(user);

        log.info("Received user: {}", user);

        String successMessage = messageUtil.get("application.success.add.user", request.getUsername());
        return new MessageResponse(successMessage, HttpStatus.OK.value(), "OK");

    }

    public ApiDataResponseBuilder signIn(LoginRequest loginRequest) {
        String key = loginRequest.getUsername();
        Bucket bucket = rateLimiterService.resolveBucket(key);

        if (!bucket.tryConsume(1)) {
            return ApiDataResponseBuilder.builder()
                    .message("Rate limit exceeded. Try again later.")
                    .statusCode(HttpStatus.TOO_MANY_REQUESTS.value())
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .build();
        }

        if (Boolean.FALSE.equals(userRepository.existsByUsername(loginRequest.getUsername()))) {
            return ApiDataResponseBuilder.builder()
                    .message(messageUtil.get("application.error.auth.user.not-found"))
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImplement userDetails = (UserDetailsImplement) authentication.getPrincipal();
            Optional<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .findFirst();

            return ApiDataResponseBuilder.builder()
                    .data(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles.get()))
                    .message(messageUtil.get("application.success.auth.user"))
                    .statusCode(statusOK.value())
                    .status(statusOK)
                    .build();
        } catch (AuthenticationException e) {
            return ApiDataResponseBuilder.builder()
                    .message(messageUtil.get("application.error.auth.user"))
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } catch (Exception e) {
            return ApiDataResponseBuilder.builder()
                    .message(messageUtil.get("application.error.internal"))
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


}

package com.chat.chatapp.services;

import java.text.ParseException;
import java.time.Instant;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.chatapp.Exception.AppException;
import com.chat.chatapp.Exception.ErrorCode;
import com.chat.chatapp.dto.request.AuthenticationRequest;
import com.chat.chatapp.dto.request.IntrospectRequest;
import com.chat.chatapp.dto.response.AuthenticationResponse;
import com.chat.chatapp.dto.response.IntrospectResponse;
import com.chat.chatapp.entity.User;
import com.chat.chatapp.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
     
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.singer_key}")
    protected String SINGER_KEY;

    public IntrospectResponse introspectResponse(IntrospectRequest request) {
        var token = request.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(SINGER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier); 
        return IntrospectResponse.builder()
            .valid(verified && expiryTime.after(new Date()))
            .build();
        } catch (ParseException | JOSEException e) {
            return IntrospectResponse.builder()
                .valid(false)
                .build();
        }
    }
         

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByPhone(request.getPhone())
            .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        System.out.println(user.getPassword());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!authenticated) 
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user);
        
        return AuthenticationResponse.builder()
        .token(token)
        .authenticate(authenticated)
        .build();
    }

    //@SuppressWarnings("unused")
    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(user.getFullName())
        .issuer("Admin NQT")
        .issueTime(new Date())
        .expirationTime(new Date(
            Instant.now().plus(1,ChronoUnit.HOURS).toEpochMilli()
        ))
        .claim("scope", buildScope(user))
        .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

         try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch(JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!org.springframework.util.CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(stringJoiner::add);

            return stringJoiner.toString();
        }
        return " ";
    }

}

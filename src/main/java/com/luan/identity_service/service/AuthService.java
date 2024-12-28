package com.luan.identity_service.service;

import com.luan.identity_service.dto.request.AuthRequest;
import com.luan.identity_service.dto.request.IntrospectRequest;
import com.luan.identity_service.dto.request.RefreshTokenRequest;
import com.luan.identity_service.dto.response.IntrospectResponse;
import com.luan.identity_service.dto.response.RefreshTokenResponse;
import com.luan.identity_service.entity.InvalidToken;
import com.luan.identity_service.entity.Role;
import com.luan.identity_service.entity.User;
import com.luan.identity_service.repository.InvalidTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    UserService userService;
    @Autowired
    InvalidTokenRepository invalidTokenRepository;
    @Value("${jwt.signerKey}")
    protected String signerKey;
    @Value("${jwt.valid-duration}")
    protected long valid_duration;
    @Value("${jwt.refreshable-duration}")
    protected long refreshable_duration;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);


    public boolean isAbleToRefresh(RefreshTokenRequest request) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(request.getToken());
        return Instant.now()
                .isBefore(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(refreshable_duration, ChronoUnit.SECONDS));
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws Exception {
        String token = request.getToken();
        SignedJWT signedJWT = SignedJWT.parse(token);
        String tokenId= signedJWT.getJWTClaimsSet().getJWTID();
        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userService.getUserByUsername(username);
        String newToken = this.generateToken(user);
        //chuyen token cu thanh invalid
        invalidTokenRepository.save(new InvalidToken().builder()
                .tokenId(tokenId)
                .build());
        return new RefreshTokenResponse().builder()
                .token(newToken)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        return new IntrospectResponse().builder()
                .token(request.getToken())
                .isValid(isValid(request.getToken()))
                .build();
    }

    public boolean isValid(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = null;
        Date expiryTime;
        boolean isExpiry;
        try {
            signedJWT = SignedJWT.parse(token);
            expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            isExpiry = expiryTime.after(new Date());
        } catch (ParseException e) {
            isExpiry = false;
        }
        String tokenId= signedJWT.getJWTClaimsSet().getJWTID();

        boolean isCorrectToken = signedJWT.verify(verifier);
        return isCorrectToken && isExpiry && !invalidTokenRepository.existsById(tokenId);
    }

    public boolean isAnUser(AuthRequest request) throws Exception {
        User user = userService.getUserByUsername(request.getUsername());
        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    public String generateToken(User user) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("com.luan")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now().plus(valid_duration, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new JOSEException(e);
        }
    }

    public String buildScope(User user) {
        List<Role> roles = user.getRoles();
        List<String> rolesName = new ArrayList<>();
        for (int i = 0; i < roles.size(); i++) {
            rolesName.add(roles.get(i).getName());
        }
        return String.join(" ", rolesName);
    }


}

package com.hatchways.finance.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hatchways.finance.exception.CredentialsException;

public class AuthUtil {

  public static String generateToken(String email, String secret) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create().withClaim("sub", email).sign(algorithm);
  }

  public static String getToken(String authorization) {
    String[] parts = authorization.split(" ");
    if (!parts[0].equals("Bearer") || parts.length > 2) {
      throw new CredentialsException();
    }

    return parts[1];
  }

  public static DecodedJWT decodeToken(String token, String secret) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    JWTVerifier verifier = JWT.require(algorithm).build();

    try {
      return verifier.verify(token);
    } catch (JWTVerificationException exception) {
      throw new CredentialsException();
    }
  }

  public static String getCurrentUserEmail(String authorization, String secret) {
    String token = AuthUtil.getToken(authorization);
    if (token == null || token.length() == 0) {
      throw new CredentialsException();
    }

    DecodedJWT decodedJWT = AuthUtil.decodeToken(token, secret);
    String email = decodedJWT.getSubject();
    if (email == null) {
      throw new CredentialsException();
    }

    return email;
  }
}

package com.bjs.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BPwdEncoderUtil {

  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String bCryptPassword(String password) {
    return encoder.encode(password);
  }

  public static boolean matches(CharSequence rawPassword, String encodedPassword) {
    return encoder.matches(rawPassword, encodedPassword);
  }
}

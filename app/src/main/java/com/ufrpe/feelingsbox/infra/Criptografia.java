package com.ufrpe.feelingsbox.infra;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Criptografia {
    private static final int HEXADECIMAL1 = 0xff;
    private static final int HEXADECIMAL2 = 0x10;
    private static final int HEXADECIMAL3 = 0xFF;


    public String criptografarSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(senha.getBytes());
        byte[] hash = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            if ((HEXADECIMAL1 & aHash) < HEXADECIMAL2) {
                hexString.append("0").append(Integer.toHexString((HEXADECIMAL3 & aHash)));
            } else {
                hexString.append(Integer.toHexString(HEXADECIMAL3 & aHash));
            }

        }
        return hexString.toString();
    }
}
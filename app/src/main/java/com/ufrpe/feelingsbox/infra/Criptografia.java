package com.ufrpe.feelingsbox.infra;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Criptografia {

    public String criptografarSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(senha.getBytes());
        byte[] hash = md.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }

        }
        return hexString.toString();
    }
}

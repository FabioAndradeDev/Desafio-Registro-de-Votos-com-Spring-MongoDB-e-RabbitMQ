package com.bbb_consumidor.app.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {

    /**
     * Gera um hash SHA-256 para a string fornecida.
     * Este hash é determinístico: a mesma entrada sempre produzirá a mesma saída.
     * @param text A string a ser hasheada (ex: CPF, email).
     * @return O hash SHA-256 em formato Base64.
     */
    public static String generateSha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes());
            return Base64.getEncoder().encodeToString(hash); // Converte bytes para string Base64
        } catch (NoSuchAlgorithmException e) {
            // Isso não deveria acontecer em JVMs padrão, mas é bom tratar.
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
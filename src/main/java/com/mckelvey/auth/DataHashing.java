package com.mckelvey.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class DataHashing {

    // Hashing Function
    public static String hashStringData(String stringData) {
        return BCrypt.withDefaults().hashToString(12, stringData.toCharArray());
    }

    // Check hash function
    public static boolean checkHashString(String originalString, String hashedString) {
        BCrypt.Result result = BCrypt.verifyer().verify(originalString.toCharArray(), hashedString);
        return result.verified;
    }

}

//package com.example.security;
//
//import java.math.BigInteger;
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//
//public class Main {
//    public static void main(String[] args) {
//        // Create ECC and AES instances
//        ECC ecc = new ECC();
//        byte[] aesKeyBytes = "thisisakey123456".getBytes(StandardCharsets.UTF_8); // Example AES key
//
//        // Convert AES key to BigInteger
//        BigInteger aesKey = new BigInteger(1, aesKeyBytes);
//
//        // Initialize AES with the AES key
//        AES aes = new AES(aesKeyBytes);
//
//        // Generate ECC key pair
//        ECC.KeyPair keyPair = ecc.generateKeyPair();
//
//        // Encrypt AES key with the recipient's ECC public key
//        byte[] encryptedKey = ecc.encryptKey(aesKey, keyPair.getPublicKey());
//
//        // Decrypt AES key using the recipient's ECC private key
//        BigInteger decryptedKey = ecc.decryptKey(encryptedKey, keyPair.getPrivateKey());
//        byte[] decryptedKeyBytes = decryptedKey.toByteArray();
//
//        // Ensure the AES key is 16 bytes long for AES-128 (trim or pad if necessary)
//        if (decryptedKeyBytes.length < 16) {
//            decryptedKeyBytes = Arrays.copyOf(decryptedKeyBytes, 16);
//        } else if (decryptedKeyBytes.length > 16) {
//            decryptedKeyBytes = Arrays.copyOfRange(decryptedKeyBytes, 0, 16);
//        }
//        AES aesDecrypted = new AES(decryptedKeyBytes);
//
//        // Message to encrypt
//        String message = "A MESSAGE";
//        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
//
//        // Pad the message to make it a multiple of 16 bytes (AES block size)
//        byte[] paddedMessage = padInput(messageBytes);
//
//        // Encrypt the padded message
//        byte[] encrypted = aesDecrypted.encryptBlock(paddedMessage);
//
//        // Decrypt the encrypted message
//        byte[] decrypted = aesDecrypted.decryptBlock(encrypted);
//
//        // Print encrypted and decrypted results
//        System.out.println("Encrypted: " + Arrays.toString(encrypted)); // Encrypted as byte array
//        System.out.println("Decrypted: " + new String(decrypted, StandardCharsets.UTF_8).trim()); // Decrypted message
//    }
//
//    // Pad the message to make it a multiple of 16 bytes (AES block size)
//    private static byte[] padInput(byte[] input) {
//        int blockSize = 16; // AES block size
//        int paddedLength = ((input.length + blockSize - 1) / blockSize) * blockSize;
//        byte[] padded = Arrays.copyOf(input, paddedLength);
//        // Pad with 0x00 bytes (you may want to use a more standard padding scheme)
//        for (int i = input.length; i < paddedLength; i++) {
//            padded[i] = 0x00;
//        }
//        return padded;
//    }
//}

package com.example.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class AES {

    private static final int BLOCK_SIZE = 16; // 128 bits
    //    private static final int[] SBOX = new int[256];
    private static final int[] SBOX = {
            0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
            0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
            0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
            0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
            0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
            0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
            0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
            0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
            0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
            0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
            0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
            0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
            0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
            0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
            0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
    };

    private static final int[] INV_SBOX = {
            // Inverse S-Box values
            0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
            0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
            0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
            0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
            0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
            0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
            0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
            0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
            0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
            0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
            0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
            0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
            0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
            0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
            0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
            0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d

    };
    private static final int[] RCON = {
            0x00000000, // This is not used, so index 0 is typically 0.
            0x01000000, // Rcon[1]
            0x02000000, // Rcon[2]
            0x04000000, // Rcon[3]
            0x08000000, // Rcon[4]
            0x10000000, // Rcon[5]
            0x20000000, // Rcon[6]
            0x40000000, // Rcon[7]
            0x80000000, // Rcon[8]
            0x1B000000, // Rcon[9]
            0x36000000  // Rcon[10]
    };


    private static final int Nb = 4; // Number of columns (32-bit words) in the state
    private static final int Nk = 4; // Number of 32-bit words in the key (for AES-128)
    private static final int Nr = 10; // Number of rounds for AES-128

    private static byte[][] keySchedule;

    public AES(byte[] key) {
        keySchedule = keyExpansion(key);
    }

    // Expand a single RCON byte to a 4-byte array for XOR operation
    private static final String ALGORITHM = "AES";
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(128); // or 192, 256 bits
        return keyGenerator.generateKey();
    }
    // Key expansion to generate round keys
    private byte[][] keyExpansion(byte[] key) {
        byte[][] w = new byte[Nb * (Nr + 1)][4];
        for (int i = 0; i < Nk; i++) {
            w[i][0] = key[4 * i];
            w[i][1] = key[4 * i + 1];
            w[i][2] = key[4 * i + 2];
            w[i][3] = key[4 * i + 3];
        }

        byte[] temp = new byte[4];

        for (int i = Nk; i < Nb * (Nr + 1); i++) {
            System.arraycopy(w[i - 1], 0, temp, 0, 4);

            if (i % Nk == 0) {
                temp = subWord(rotWord(temp)); // Apply rotation and substitution
                temp[0] ^= (RCON[i / Nk] >>> 24); // XOR with RCON
            }

            for (int j = 0; j < 4; j++) {
                w[i][j] = (byte) (w[i - Nk][j] ^ temp[j]);
            }
        }
        return w;
    }

    private byte[] expandRCON(byte rconValue) {
        return new byte[]{rconValue, 0x00, 0x00, 0x00};
    }

    private byte[] subWord(byte[] word) {
        byte[] result = new byte[word.length];
        for (int i = 0; i < word.length; i++) {
            result[i] = (byte) SBOX[word[i] & 0xFF]; // Apply SBOX to each byte
        }
        return result;
    }

    // Rotate the word for key expansion
    private byte[] rotWord(byte[] word) {
        byte[] result = new byte[word.length];
        for (int i = 0; i < word.length - 1; i++) {
            result[i] = word[i + 1];
        }
        result[word.length - 1] = word[0];
        return result;
    }

    private byte[] xorWords(byte[] word1, byte[] word2) {
        byte[] result = new byte[word1.length];
        for (int i = 0; i < word1.length; i++) {
            result[i] = (byte) (word1[i] ^ word2[i]);
        }
        return result;
    }

    public static byte[] encryptBlock(byte[] inputBlock) {
        byte[][] state = new byte[4][4];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            state[i % 4][i / 4] = inputBlock[i];
        }

        addRoundKey(state, keySchedule, 0);

        for (int round = 1; round < Nr; round++) {
            subBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, keySchedule, round);
        }

        subBytes(state);
        shiftRows(state);
        addRoundKey(state, keySchedule, Nr);

        byte[] output = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            output[i] = state[i % 4][i / 4];
        }
        return output;
    }

    public byte[] decryptBlock(byte[] inputBlock) {
        byte[][] state = new byte[4][4];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            state[i % 4][i / 4] = inputBlock[i];
        }

        addRoundKey(state, keySchedule, Nr);

        for (int round = Nr - 1; round > 0; round--) {
            invShiftRows(state);
            invSubBytes(state);
            addRoundKey(state, keySchedule, round);
            invMixColumns(state);
        }

        invShiftRows(state);
        invSubBytes(state);
        addRoundKey(state, keySchedule, 0);

        byte[] output = new byte[BLOCK_SIZE];
        for (int i = 0; i < BLOCK_SIZE; i++) {
            output[i] = state[i % 4][i / 4];
        }
        return output;
    }

    // Add round key step: XOR state with key schedule
    private static void addRoundKey(byte[][] state, byte[][] w, int round) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < Nb; c++) {
                state[r][c] ^= w[round * Nb + c][r];
            }
        }
    }

    // Substitute bytes using the SBOX
    private static void subBytes(byte[][] state) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = (byte) SBOX[state[i][j] & 0xFF];
            }
        }
    }

    // Inverse substitute bytes using the inverse SBOX
    private void invSubBytes(byte[][] state) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                state[i][j] = (byte) INV_SBOX[state[i][j] & 0xFF];
            }
        }
    }

    // Shift rows transformation
    private static void shiftRows(byte[][] state) {
        for (int i = 1; i < 4; i++) {
            byte[] temp = Arrays.copyOfRange(state[i], 0, Nb);
            for (int j = 0; j < Nb; j++) {
                state[i][j] = temp[(j + i) % Nb]; // Shift by row index
            }
        }
    }

    // Inverse shift rows transformation
    private void invShiftRows(byte[][] state) {
        for (int i = 1; i < 4; i++) {
            byte[] temp = Arrays.copyOfRange(state[i], 0, Nb);
            for (int j = 0; j < Nb; j++) {
                state[i][j] = temp[(j - i + Nb) % Nb]; // Inverse shift
            }
        }
    }

    // Mix columns transformation
    private static void mixColumns(byte[][] state) {
        for (int c = 0; c < Nb; c++) {
            byte[] col = new byte[4];
            for (int i = 0; i < 4; i++) col[i] = state[i][c];

            state[0][c] = (byte) (gfMul(col[0], 2) ^ gfMul(col[1], 3) ^ col[2] ^ col[3]);
            state[1][c] = (byte) (col[0] ^ gfMul(col[1], 2) ^ gfMul(col[2], 3) ^ col[3]);
            state[2][c] = (byte) (col[0] ^ col[1] ^ gfMul(col[2], 2) ^ gfMul(col[3], 3));
            state[3][c] = (byte) (gfMul(col[0], 3) ^ col[1] ^ col[2] ^ gfMul(col[3], 2));
        }
    }

    // Inverse mix columns transformation
    private void invMixColumns(byte[][] state) {
        for (int c = 0; c < Nb; c++) {
            byte[] col = new byte[4];
            for (int i = 0; i < 4; i++) col[i] = state[i][c];

            state[0][c] = (byte) (gfMul(col[0], 14) ^ gfMul(col[1], 11) ^ gfMul(col[2], 13) ^ gfMul(col[3], 9));
            state[1][c] = (byte) (gfMul(col[0], 9) ^ gfMul(col[1], 14) ^ gfMul(col[2], 11) ^ gfMul(col[3], 13));
            state[2][c] = (byte) (gfMul(col[0], 13) ^ gfMul(col[1], 9) ^ gfMul(col[2], 14) ^ gfMul(col[3], 11));
            state[3][c] = (byte) (gfMul(col[0], 11) ^ gfMul(col[1], 13) ^ gfMul(col[2], 9) ^ gfMul(col[3], 14));
        }
    }

    // Galois field multiplication
    private static byte gfMul(byte a, int b) {
        byte p = 0;
        byte hi_bit_set;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) != 0) {
                p ^= a;
            }
            hi_bit_set = (byte) (a & 0x80);
            a <<= 1;
            if (hi_bit_set != 0) {
                a ^= 0x1b; // XOR with irreducible polynomial
            }
            b >>= 1;
        }
        return p;
    }
    // Method to encrypt a String with AES
    public static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Method to decrypt a String with AES
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData, "UTF-8");
    }
}


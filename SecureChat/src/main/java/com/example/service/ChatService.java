////package com.example.service;
////
////import com.example.model.Message;
////import com.example.repository.MessageRepository;
////import com.example.security.AES;
////import com.example.security.ECC;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import javax.crypto.SecretKey;
////import javax.crypto.spec.SecretKeySpec;
////import java.util.Base64;
////import java.util.List;
////
////@Service
////public class ChatService {
////
////    @Autowired
////    private MessageRepository messageRepository;
////
////    private ECC ecc; // ECC instance
////    private ECC.KeyPair eccKeyPair; // ECC key pair
////    private SecretKey aesKey; // AES key for encryption/decryption
////
////    public ChatService() throws Exception {
////        // Initialize AES key
////        aesKey = AES.generateKey();
////
////        // Initialize ECC
////        ecc = new ECC();
////        eccKeyPair = ecc.generateKeyPair();
////    }
////
////    public Message saveMessage(Message message) throws Exception {
////        // Encrypt message content with AES
////        String aesEncryptedContent = AES.encrypt(message.getContent(), aesKey);
////
////        // Encrypt AES key with ECC
////        byte[] encryptedAesKey = ecc.encryptKey(aesKey.getEncoded(), eccKeyPair.getPublicKey());
////
////        // Encrypt the AES-encrypted message content with ECC
////        byte[] aesEncryptedContentBytes = aesEncryptedContent.getBytes("UTF-8");
////        byte[] eccEncryptedContent = ecc.encrypt(aesEncryptedContentBytes, eccKeyPair.getPublicKey());
////
////        // Set the encrypted content and AES key in the message
////        message.setContent(Base64.getEncoder().encodeToString(eccEncryptedContent));
////        message.setEncryptedAesKey(Base64.getEncoder().encodeToString(encryptedAesKey));
////
////        // Save the message to the repository
////        return messageRepository.save(message);
////    }
////
////    public List<Message> getAllMessages() throws Exception {
////        List<Message> messages = messageRepository.findAll();
////
////        for (Message message : messages) {
////            // Decode Base64 encoded encrypted AES key
////            byte[] encryptedAesKey = Base64.getDecoder().decode(message.getEncryptedAesKey());
////
////            // Decrypt AES key using ECC
////            byte[] decryptedAesKeyBytes = ecc.decryptKey(encryptedAesKey, eccKeyPair.getPrivateKey());
////            SecretKey aesKey = new SecretKeySpec(decryptedAesKeyBytes, "AES");
////
////            // Decode Base64 encoded encrypted message content
////            byte[] eccEncryptedContent = Base64.getDecoder().decode(message.getContent());
////
////            // Decrypt message content with ECC
////            byte[] aesEncryptedContentBytes = ecc.decrypt(eccEncryptedContent, aesKey);
////
////            // Decrypt AES-encrypted message content with AES
////            String aesEncryptedContentString = new String(aesEncryptedContentBytes, "UTF-8");
////            String decryptedContent = AES.decrypt(aesEncryptedContentString, aesKey);
////
////            // Set the decrypted content back into the message
////            message.setContent(decryptedContent);
////        }
////
////        return messages;
////    }
////}
//
//
//package com.example.service;
//
//import com.example.model.Message;
//import com.example.model.User;
//import com.example.repository.MessageRepository;
//import com.example.security.AES;
//import com.example.security.ECC;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.math.BigInteger;
//import java.util.Base64;
//import java.util.List;
//
//@Service
//public class ChatService {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    private ECC ecc; // ECC instance
//    private ECC.KeyPair eccKeyPair; // ECC key pair
//
//    public ChatService() {
//        // Initialize ECC
//        ecc = new ECC();
//        eccKeyPair = ecc.generateKeyPair();
//    }
//
//    public Message saveMessage(Message message) throws Exception {
//        // Generate a new AES key for each message
//        SecretKey aesKey = AES.generateKey();
//
//        // Encrypt message content with AES
//        String encryptedContent = AES.encrypt(message.getContent(), aesKey);
//
//        // Use ECC to encrypt the AES key
//        byte[] encryptedAesKey = ecc.encryptKey(new BigInteger(aesKey.getEncoded()), eccKeyPair.getPublicKey());
//
//        // Set the encrypted content and AES key in the message
//        message.setContent(encryptedContent);
//        message.setEncryptedAesKey(Base64.getEncoder().encodeToString(encryptedAesKey));
//
//        // Save the message to the repository
//        return messageRepository.save(message);
//    }
//
//    public List<Message> getAllMessages() throws Exception {
//        List<User> messages = messageRepository.findAll();
//
//        for (User message : messages) {
//            // Decode Base64 encoded encrypted AES key
//            byte[] encryptedAesKey = Base64.getDecoder().decode(message.getEncryptedAesKey());
//
//            // Use ECC to decrypt the AES key
//            BigInteger decryptedAesKeyBigInt = ecc.decryptKey(encryptedAesKey, eccKeyPair.getPrivateKey());
//            byte[] decryptedAesKeyBytes = decryptedAesKeyBigInt.toByteArray();
//            SecretKey aesKey = new SecretKeySpec(decryptedAesKeyBytes, "AES");
//
//            // Decrypt message content with AES
//            String decryptedContent = AES.decrypt(message.getContent(), aesKey);
//
//            // Set the decrypted content back into the message
//            message.setContent(decryptedContent);
//        }
//
//        return messages;
//    }
//}

package com.example.service;

import com.example.model.Message;
import com.example.repository.MessageRepository;
import com.example.security.AES;
import com.example.security.ECC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Base64;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MessageRepository messageRepository;

    private ECC ecc; // ECC instance
    private ECC.KeyPair eccKeyPair; // ECC key pair

    public ChatService() {
        // Initialize ECC
        ecc = new ECC();
        eccKeyPair = ecc.generateKeyPair();
    }

    public Message saveMessage(Message message) throws Exception {
        // Generate a new AES key for each message
        SecretKey aesKey = AES.generateKey();

        // Encrypt message content with AES
        String encryptedContent = AES.encrypt(message.getContent(), aesKey);

        // Use ECC to encrypt the AES key
        byte[] encryptedAesKey = ecc.encryptKey(new BigInteger(1, aesKey.getEncoded()), eccKeyPair.getPublicKey());

        // Set the encrypted content and AES key in the message
        message.setContent(encryptedContent);
        message.setEncryptedAesKey(Base64.getEncoder().encodeToString(encryptedAesKey));

        // Save the message to the repository
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() throws Exception {
        List<Message> messages = messageRepository.findAll();

        for (Message message : messages) {
            // Decode Base64 encoded encrypted AES key
            byte[] encryptedAesKey = Base64.getDecoder().decode(message.getEncryptedAesKey());

            // Use ECC to decrypt the AES key
            BigInteger decryptedAesKeyBigInt = ecc.decryptKey(encryptedAesKey, eccKeyPair.getPrivateKey());
            byte[] decryptedAesKeyBytes = decryptedAesKeyBigInt.toByteArray();
            SecretKey aesKey = new SecretKeySpec(decryptedAesKeyBytes, "AES");

            // Decrypt message content with AES
            String decryptedContent = AES.decrypt(message.getContent(), aesKey);

            // Set the decrypted content back into the message
            message.setContent(decryptedContent);
        }

        return messages;
    }
}
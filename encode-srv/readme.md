# Encode service

## Purpose
- Loads private key from server-key-storage for Signing message
- Loads public key from client-key-storage for Verification of the message

## Accept parameters
- sender.key-store.password - sender key-store password
- receiver.key-store.password - receiver key-store password

## Swagger-UI
http://localhost:8080/swagger-ui/

## Required

Keystore files in the directory:
- /keys/sender_keystore.jks (primaryKey for signing)
- /keys/receiver_keystore.jks (publicKey for sign verification)

## Links
[Digital Signatures in Java article from Baeldung](https://www.baeldung.com/java-digital-signature)
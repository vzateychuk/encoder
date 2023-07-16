# Digital-signature service

## Purpose
The service to simplify hashing, signing and verify-signatures. 
It uses self-signed keys from stores:
- _sender-store.jks_ - Keystore for sender (private Key used);
- _receiver-store.jks_ - Keystore for receiver (public Key used);

The service uses SHA384withRSA algorithm for signing/verify.
Server endpoints available on Swagger-UI

## Build
```shell
./gradlew clean build
```

## Run
```shell
./gradlew run <application-parameters>
```
Expect application parameters:
- sender.key-store.password - sender key-store password
- receiver.key-store.password - receiver key-store password

## Log
Stored in folder 
<application-root>/LOGS

## Swagger-UI
http://localhost:8080/swagger-ui/index.html

## Required

Keystore files in the directory:
- /keys/sender_keystore.jks (primaryKey for signing)
- /keys/receiver_keystore.jks (publicKey for sign verification)

## Links
[Digital Signatures in Java article from Baeldung](https://www.baeldung.com/java-digital-signature)
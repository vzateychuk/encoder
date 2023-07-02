package vez.signer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vez.internal.exceptions.UnableToLoadKeyException;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * Load private and public keys from underlining key-stores (sender/receiver)
 */
@Slf4j
@Service
public class CertificateServiceImpl implements CertificateService {

    @Value("${sender.key-store.password}")
    private String senderKeyStorePwd;

    @Value("${receiver.key-store.password}")
    private String receiverKeyStorePwd;

    /**
     * Loads a Private Key for Signing from the sender-keystore on Sender-side
     */
    @Override
    public PrivateKey loadPrivateKey() {

        log.debug("--> Start load private key from sender keystore");

        // TODO keystore app-properties/env-variable
        try ( InputStream is = CertificateServiceImpl.class.getResourceAsStream("/keys/sender_keystore.jks") ) {
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = senderKeyStorePwd.toCharArray();
            keyStore.load(is, password);
            return (PrivateKey) keyStore.getKey("senderKeyPair", password);
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException |
                 UnrecoverableKeyException e) {
            log.error("<-- Unable to load private key from keystore", e);
            throw new UnableToLoadKeyException("Unable to load private key");
        }
    }

    /**
     * Loads a Public Key for Verification from the receiver-keystore on Receiver
     */
    @Override
    public PublicKey loadPublicKey() {

        log.debug("--> Start load public key from receiver keystore");

        try ( InputStream is = CertificateServiceImpl.class.getResourceAsStream("/keys/receiver_keystore.jks") ) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = receiverKeyStorePwd.toCharArray();
            // TODO keystore app-properties/env-variable
            keyStore.load(is, password);
            Certificate certificate = keyStore.getCertificate("receiverKeyPair");
            return certificate.getPublicKey();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error("<-- Unable to load public key from keystore", e);
            throw new UnableToLoadKeyException("Unable to load public key");
        }
    }

}

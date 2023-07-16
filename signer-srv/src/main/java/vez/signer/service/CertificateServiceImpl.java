package vez.signer.service;

import jakarta.annotation.PostConstruct;
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

    private PrivateKey privateKey;
    private PublicKey publicKey;

    /**
     * Loads a Private Key for Signing from the sender-keystore for Sender
     */
    @Override
    public PrivateKey getPrivateKey() {

        if (this.privateKey == null) {
            this.privateKey = loadPrivateKey("/keys/sender_keystore.jks");
        }
        return this.privateKey;
    }

    /**
     * Loads a Public Key for Verification from the receiver-keystore for Receiver
     */
    @Override
    public PublicKey getPublicKey() {

        if (this.publicKey == null) {
            this.publicKey = loadPublicKey("/keys/receiver_keystore.jks");
        }
        return this.publicKey;
    }

    //region PRIVATE

    private PrivateKey loadPrivateKey(String keystorePath) {

        log.debug("Load private key from keystore: {}", keystorePath);
        try ( InputStream is = CertificateServiceImpl.class.getResourceAsStream(keystorePath) ) {
            final KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = senderKeyStorePwd.toCharArray();
            keyStore.load(is, password);
            return (PrivateKey) keyStore.getKey("senderKeyPair", password);
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException |
                 UnrecoverableKeyException e) {
            log.error("Unable to load private key from keystore: {}", keystorePath, e);
            throw new UnableToLoadKeyException("Unable to load private key");
        }
    }

    private PublicKey loadPublicKey(String keystorePath) {

        log.debug("Loading public key from receiver keystore: {}", keystorePath);
        try ( InputStream is = CertificateServiceImpl.class.getResourceAsStream(keystorePath) ) {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            char[] password = receiverKeyStorePwd.toCharArray();
            keyStore.load(is, password);
            Certificate certificate = keyStore.getCertificate("receiverKeyPair");
            return certificate.getPublicKey();
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error("Unable to load public key from keystore: {}", keystorePath, e);
            throw new UnableToLoadKeyException("Unable to load public key");
        }
    }

    //endregion
}

package vez.signer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vez.internal.utils.ExceptionUtils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

@Slf4j
@Service
public class SignatureServiceImpl implements SignatureService {

    private static final String SHA_384_WITH_RSA = "SHA384withRSA";

    @Autowired CertificateService certificateService;

    /**
     * Generate a base64 encoded message hash from the received message
     **/
    @Override
    public String hash(String message) {
        // wrap throwable method and rethrow unchecked exception
        byte[] hash = ExceptionUtils.rethrowFunction(this::hashMessage).apply(message);
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Generate a message hash and encrypt from the received message
     **/
    @Override
    public String signMessage(String message) {
        // wrap throwable method and rethrow unchecked exception
        byte[] signature = ExceptionUtils.rethrowFunction(this::hashAndEncrypt).apply(message);
        return Base64.getEncoder().encodeToString(signature);
    }

    /**
     * When Receiver receive a message, it must verify its signature.
     *
     * @param message not blank message to be verified
     * @param signatureEncoded - encrypted, base64 encoded signature ( = encoded hash)
     * @return true if verified
     * */
    @Override
    public boolean verifySignature(String message, String signatureEncoded) {

        byte[] signatureBytes = Base64.getDecoder().decode(signatureEncoded);
        // wrap throwable method and rethrow unchecked exception
        return ExceptionUtils.rethrowBiFunction(this::verify).apply(message, signatureBytes);
    }

    //region PRIVATE

    private byte[] hashAndEncrypt(String message)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {

        // To encrypt a message, we need an algorithm and a private key.
        PrivateKey privateKey = certificateService.loadPrivateKey();

        Signature signature = Signature.getInstance(SHA_384_WITH_RSA);
        signature.initSign(privateKey);
        signature.update(message.getBytes(StandardCharsets.UTF_8));

        // return base64 encoded encrypted hash
        return signature.sign();
    }

    private boolean verify(String message, byte[] digitalSignature)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //
        PublicKey publicKey = certificateService.loadPublicKey();
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        Signature signature = Signature.getInstance(SHA_384_WITH_RSA);
        signature.initVerify(publicKey);
        signature.update(messageBytes);

        return signature.verify(digitalSignature);
    }

    private byte[] hashMessage(String message) throws NoSuchAlgorithmException {

        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(messageBytes);
    }

    //endregion
}

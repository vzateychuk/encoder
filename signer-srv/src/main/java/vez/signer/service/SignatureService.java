package vez.signer.service;

public interface SignatureService {

    String signMessage(String message);

    boolean verifySignature(String message, String signatureEncoded);

    String hash(String message);
}

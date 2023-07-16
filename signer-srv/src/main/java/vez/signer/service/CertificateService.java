package vez.signer.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface CertificateService {
    PrivateKey getPrivateKey();

    PublicKey getPublicKey();
}

package vez.encoder.service;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface CertificateService {
    PrivateKey loadPrivateKey();

    PublicKey loadPublicKey();
}

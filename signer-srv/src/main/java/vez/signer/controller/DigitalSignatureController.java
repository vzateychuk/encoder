package vez.signer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vez.signer.service.SignatureService;

@RestController
@RequestMapping("/api")
public class DigitalSignatureController {

    private final SignatureService signatureService;

    @Autowired
    public DigitalSignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @PostMapping("/hash")
    public String hash(@RequestBody String message) {
        return signatureService.hash(message);
    }

    @PostMapping("/sign")
    public String sign(@RequestBody String message) {
        return signatureService.signMessage(message);
    }

    @PostMapping("/verity")
    public ResponseEntity<String> verify(@RequestParam("signature") String signature, @RequestBody String message) {
        boolean verified = signatureService.verifySignature(message, signature);
        if (verified) {
            return ResponseEntity.ok("verified");
        } else {
            return ResponseEntity.accepted().body("not verified");
        }
    }

}

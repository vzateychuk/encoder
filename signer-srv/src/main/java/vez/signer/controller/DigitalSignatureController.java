package vez.signer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Return Base64 encoded SHA-256 hash of input message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hash generated"),
            @ApiResponse(responseCode = "400", description = "Empty/Blank input message")
    })
    @PostMapping("/hash")
    public String hash(@RequestBody String message) {
        return signatureService.hash(message);
    }

    @Operation(summary = "Create digital signature (SHA384withRSA) of input message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signature generated"),
            @ApiResponse(responseCode = "400", description = "Empty/Blank input message")
    })
    @PostMapping("/sign")
    public String sign(@RequestBody String message) {
        return signatureService.signMessage(message);
    }

    @Operation(summary = "Verify digital signature of input message by using public key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Signature verified"),
            @ApiResponse(responseCode = "202", description = "Signature NOT verified"),
            @ApiResponse(responseCode = "400", description = "Bad signature or blank input message")
    })
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

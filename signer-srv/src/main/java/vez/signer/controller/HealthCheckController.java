package vez.signer.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Spring controller for the Health checking
 * */
@RestController
public class HealthCheckController {

    /**
     * A central interface that provides configuration for this
     * microservice and is read-only while the application is running,
     */
    final ApplicationContext applicationContext;

    @Autowired
    public HealthCheckController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * A request for testing Eureka connection. Indicate the request succeeded and return the application state.
     *
     * @return The application name.
     */
    @Operation(summary = "Get liveness probe")
    @GetMapping({"/", "/actuator/info"})
    ResponseEntity<String> info() {
        return ResponseEntity.ok(applicationContext.getId()  + " is alive and running"
        );
    }

}

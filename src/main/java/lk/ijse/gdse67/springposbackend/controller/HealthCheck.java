package lk.ijse.gdse67.springposbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/healthcheck")
public class HealthCheck {
    @GetMapping
    public String healthCheck() {
        return "Server is up and running";
    }
}

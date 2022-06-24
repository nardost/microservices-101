package info.akaki.usage.api;

import info.akaki.usage.entity.ServiceType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/services")
public class ServiceController {

    @GetMapping("")
    public ResponseEntity<Collection<String>> getAllServiceCategories() {
        return new ResponseEntity<>(
                Arrays.stream(ServiceType.values()).map(ServiceType::toString).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
}

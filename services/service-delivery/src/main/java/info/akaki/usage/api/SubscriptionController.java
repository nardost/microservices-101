package info.akaki.usage.api;

import info.akaki.usage.dto.SubscriptionDTO;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<String>> getAllServiceCategories() {
        return new ResponseEntity<>(
                Arrays.stream(ServiceType.values()).map(ServiceType::toString).collect(Collectors.toSet()),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return new ResponseEntity<>(this.subscriptionService.createSubscription(subscriptionDTO), HttpStatus.CREATED);
    }
}

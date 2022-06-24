package info.akaki.subscription.api;

import info.akaki.subscription.dto.SubscriptionDTO;
import info.akaki.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(SubscriptionController.SUBSCRIPTIONS_API_URL)
public class SubscriptionController {
    public static final String SUBSCRIPTIONS_API_URL = "api/v1/subscriptions";

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<SubscriptionDTO>> getSubscriptions() {
        return new ResponseEntity<>(this.subscriptionService.getAllSubscriptions(), HttpStatus.OK);
    }

    @GetMapping("{subscriptionId}")
    public ResponseEntity<SubscriptionDTO> getSubscription(
            @NotNull(message = "{subscription.id.absent}")
            @PathVariable("subscriptionId") UUID subscriptionId) {
        return new ResponseEntity<>(this.subscriptionService.getSubscriptionById(subscriptionId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SubscriptionDTO> createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        return new ResponseEntity<>(this.subscriptionService.saveSubscription(subscriptionDTO), HttpStatus.CREATED);
    }
}

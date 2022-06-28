package info.akaki.usage.api;

import info.akaki.usage.dto.SubscriptionDTO;
import info.akaki.usage.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(value = "api/v1/subscriptions")
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<SubscriptionDTO>> getSubscriptions() {
        return new ResponseEntity<>(this.subscriptionService.getSubscriptions(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @Valid
            @RequestBody
            SubscriptionDTO subscriptionDTO) {
        return new ResponseEntity<>(
                this.subscriptionService.createSubscription(subscriptionDTO),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("{subscriptionId}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(
            @Valid
            @RequestBody
            SubscriptionDTO dto,
            @PathVariable("subscriptionId")
            @NotNull(message = "{subscription.id.absent}")
            UUID subscriptionId) {
        dto.setSubscriptionId(subscriptionId);
        return new ResponseEntity<>(this.subscriptionService.updateSubscription(dto), HttpStatus.ACCEPTED);
    }
}

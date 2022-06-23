package info.akaki.utility.api;

import info.akaki.utility.dto.PlanDTO;
import info.akaki.utility.service.PlanService;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping(PlanController.PLANS_API_URL)
@Validated
public class PlanController {
    public static final String PLANS_API_URL = "api/v1/plans";
    
    private final PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<PlanDTO>> getPlans() {
        return new ResponseEntity<>(this.planService.getAllPlans(), HttpStatus.OK);
    }

    @GetMapping("{planId}")
    public ResponseEntity<PlanDTO> getPlan(
            @NotNull(message = "{plan.id.absent}")
            @PathVariable("planId")
            UUID planId) {
        return new ResponseEntity<>(this.planService.getPlanById(planId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PlanDTO> createPlan(@Valid @RequestBody PlanDTO planDTO) {
        return new ResponseEntity<>(this.planService.savePlan(planDTO), HttpStatus.CREATED);
    }
}

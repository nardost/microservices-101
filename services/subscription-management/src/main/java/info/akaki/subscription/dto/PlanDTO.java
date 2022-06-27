package info.akaki.subscription.dto;

import info.akaki.subscription.entity.Plan;
import info.akaki.subscription.exception.SubscriptionManagementException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static info.akaki.subscription.utilities.Validators.isValidBaseUnits;
import static info.akaki.subscription.utilities.Validators.isValidMaxUnits;
import static info.akaki.subscription.utilities.Validators.isValidRatePerUnit;
import static java.lang.Boolean.FALSE;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class PlanDTO {
    private UUID id;
    @NotNull(message = "{plan.plan-name.absent}")
    private String planName;
    @NotNull(message = "{plan.base-units.absent}")
    @Positive(message = "{plan.units.negative}")
    private double baseUnits;
    @NotNull(message = "{plan.max-units.absent}")
    @Positive(message = "{plan.units.negative}")
    private double maxUnits;
    @NotNull(message = "{plan.rate.absent}")
    @Positive(message = "{plan.rate.invalid}")
    private BigDecimal ratePerUnit;

    public PlanDTO(Plan plan) {
        this.id = plan.getId();
        this.planName = plan.getServiceType();
        this.baseUnits = plan.getBaseUnits();
        this.maxUnits = plan.getMaxUnits();
        this.ratePerUnit = plan.getRatePerUnit();
    }

    public Plan toPlan() {
        Plan p = new Plan();
        p.setId(this.id);
        p.setServiceType(this.planName);
        p.setBaseUnits(this.baseUnits);
        p.setMaxUnits(this.maxUnits);
        p.setRatePerUnit(this.ratePerUnit);
        return p;
    }

    public static void validate(PlanDTO dto) {
        if(Objects.isNull(dto)) {
            throw new SubscriptionManagementException("plan.absent");
        }
        if(FALSE.equals(isValidBaseUnits(dto.getBaseUnits()))) {
            throw new SubscriptionManagementException("plan.base-units.invalid");
        }
        if(FALSE.equals(isValidMaxUnits(dto.getMaxUnits()))) {
            throw new SubscriptionManagementException("plan.max-units.invalid");
        }
        if(FALSE.equals(isValidRatePerUnit(dto.getRatePerUnit()))) {
            throw new SubscriptionManagementException("plan.rate-per-unit.invalid");
        }
    }
}

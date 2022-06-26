package info.akaki.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActionDTO {
    @NotNull(message = "{subscription-id.absent}")
    private UUID subscriptionId;
    @NotNull(message = "{action.absent}")
    private Action action;
    @FutureOrPresent(message = "{execution-timestamp.past}")
    private LocalDateTime executionTimestamp;
}

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
    private UUID subscriptionId;
    @NotNull(message = "{action.command.absent}")
    private Command command;
    @FutureOrPresent(message = "{action.execution-timestamp.past}")
    private LocalDateTime executionTimestamp;
}

package info.akaki.usage.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class UsageDTO {
    private long id;
    private UUID deviceId;
    private LocalDateTime captureTimestamp;
    private long usageDataAmount;
}

package info.akaki.usage.utilities;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.entity.DeviceState;
import info.akaki.usage.entity.ServiceType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;

public final class DevicesFileProcessor {

    private static final String UUID_PATTERN = "[\\da-fA-F]{8}(-[\\da-fA-F]{4}){3}-[\\da-fA-F]{12}";
    private static final String TIMESTAMP_PATTERN = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(.\\d{3})?[Z]?";
    private static final String SERVICE_TYPES_PATTERN = "([A-E](,[A-E])*)?";
    private static final String DEVICE_DATA_LINE_PATTERN = String.format("(^%s)\t(%s)\t(%s)$",
            UUID_PATTERN,
            TIMESTAMP_PATTERN,
            SERVICE_TYPES_PATTERN);

    private DevicesFileProcessor() {
    }

    public static Boolean isValidDeviceDataLine(String deviceDataLine) {
        return Objects.nonNull(deviceDataLine) && deviceDataLine.matches(DEVICE_DATA_LINE_PATTERN);
    }

    public static String getDeviceId(String deviceDataLine) {
        if(FALSE.equals(isValidDeviceDataLine(deviceDataLine))) return null;
        final String[] tokens = deviceDataLine.split("\t");
        return tokens[0];
    }

    public static String getEndOfLife(String deviceDataLine) {
        if(FALSE.equals(isValidDeviceDataLine(deviceDataLine))) return null;
        final String[] tokens = deviceDataLine.split("\t");
        return tokens[1];
    }

    public static Collection<ServiceType> getServiceTypes(String deviceDataLine) {
        if(FALSE.equals(isValidDeviceDataLine(deviceDataLine))) return null;
        final String[] tokens = deviceDataLine.split("\t");
        return tokens[2].isEmpty()
                ? new HashSet<>()
                : Arrays
                .stream(tokens[2].split(","))
                .map(ServiceType::valueOf)
                .collect(Collectors.toSet());
    }

    public static Optional<DeviceDTO> toDeviceDTO(String deviceDataLine) {
        if(FALSE.equals(isValidDeviceDataLine(deviceDataLine))) {
            return Optional.empty();
        }
        DeviceDTO dto = new DeviceDTO();
        dto.setDeviceId(UUID.fromString(Objects.requireNonNull(getDeviceId(deviceDataLine))));
        dto.setDeviceState(DeviceState.IDLE);
        dto.setServiceTypes(getServiceTypes(deviceDataLine));
        final Instant instant = Instant.parse(Objects.requireNonNull(getEndOfLife(deviceDataLine)));
        final LocalDateTime endOfLife = instant.atZone(ZoneOffset.UTC).toLocalDateTime();
        dto.setEndOfLife(endOfLife);
        return Optional.of(dto);
    }
}

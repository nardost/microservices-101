package info.akaki.usage.api;

import info.akaki.usage.dto.DeviceDTO;
import info.akaki.usage.entity.ServiceType;
import info.akaki.usage.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = DeviceController.DEVICES_API_URL)
public class DeviceController {
    public static final String DEVICES_API_URL = "api/v1/devices";

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<DeviceDTO>> getDevices() {
        return new ResponseEntity<>(this.deviceService.getDevices(), HttpStatus.OK);
    }

    @GetMapping("{deviceId}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("deviceId") UUID deviceId) {
        return new ResponseEntity<>(this.deviceService.getDeviceByDeviceId(deviceId), HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO) {
        return new ResponseEntity<>(this.deviceService.updateDevice(deviceDTO), HttpStatus.CREATED);
    }

    @PostMapping("")
    public ResponseEntity<Void> saveDevices(@RequestParam("devices-file") MultipartFile devicesFile) {
        this.deviceService.bulkSaveDevices(devicesFile);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

package info.akaki.usage.service;

import info.akaki.usage.entity.Usage;
import info.akaki.usage.repository.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Service(value = "usageServiceAlpha")
@Transactional
public class UsageServiceAlpha implements UsageService {

    private final UsageRepository usageRepository;

    @Autowired
    public UsageServiceAlpha(UsageRepository usageRepository) {
        this.usageRepository = usageRepository;
    }

    @Override
    public Collection<Usage> getUsageDataByDeviceId(UUID deviceId, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public Usage saveUsageData(Usage usage) {
        return null;
    }
}

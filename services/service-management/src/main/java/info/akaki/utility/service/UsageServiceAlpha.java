package info.akaki.utility.service;

import info.akaki.utility.entity.Usage;
import info.akaki.utility.repository.UsageRepository;
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
    public Collection<Usage> getAllTimeUsagesByAllSubscriptions() {
        return null;
    }

    @Override
    public Collection<Usage> getAllTimeUsagesBySubscriptionId(UUID subscriptionId) {
        return null;
    }

    @Override
    public Collection<Usage> getUsagesBySubscriptionId(UUID subscriptionId, LocalDateTime from, LocalDateTime to) {
        return null;
    }

    @Override
    public Usage saveUsage(Usage usage) {
        return null;
    }
}

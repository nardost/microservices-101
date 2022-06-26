package info.akaki.subscription.repository;

import info.akaki.subscription.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
    boolean existsByServiceType(String serviceType);
}

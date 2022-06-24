package info.akaki.subscription.service;

import info.akaki.subscription.dto.PlanDTO;

import java.util.Collection;
import java.util.UUID;

public interface PlanService {
    PlanDTO getPlanById(UUID planId);
    Collection<PlanDTO> getAllPlans();
    PlanDTO savePlan(PlanDTO planDTO);
}

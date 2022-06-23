package info.akaki.utility.service;

import info.akaki.utility.dto.PlanDTO;

import java.util.Collection;
import java.util.UUID;

public interface PlanService {
    PlanDTO getPlanById(UUID planId);
    Collection<PlanDTO> getAllPlans();
    PlanDTO savePlan(PlanDTO planDTO);
}

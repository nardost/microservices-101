package info.akaki.utility.service;

import info.akaki.utility.dto.PlanDTO;
import info.akaki.utility.exception.AkakiUtilityException;
import info.akaki.utility.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanServiceAlpha implements PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceAlpha(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public PlanDTO getPlanById(UUID planId) {
        return new PlanDTO(this.planRepository
                .findById(planId)
                .orElseThrow(() -> new AkakiUtilityException("service.plan.not-found")));
    }

    @Override
    public Collection<PlanDTO> getAllPlans() {
        return this.planRepository.findAll()
                .stream()
                .map(PlanDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PlanDTO savePlan(PlanDTO planDTO) {
        PlanDTO.validate(planDTO);
        return new PlanDTO(this.planRepository.saveAndFlush(planDTO.toPlan()));
    }
}

package com.application.service;
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.application.dto.DistributionGetTableDTO;
import com.application.entity.Distribution;
import com.application.repository.DistributionRepository;
 
@Service
public class DistributionGetTableService {
 
    @Autowired
    private DistributionRepository distributionRepository;
 
    public List<DistributionGetTableDTO>  getAllDistributionsForTable() {
        // Retrieve all distribution entities from the repository
        List<Distribution> distributions = distributionRepository.findAll();
 
        // Use Java Streams to efficiently map entities to DTOs
        return distributions.stream().map(distribution -> {
            DistributionGetTableDTO dto = new DistributionGetTableDTO();
            dto.setAppDistributionId(distribution.getAppDistributionId());
            dto.setAppStartNo(distribution.getAppStartNo());
            dto.setAppEndNo(distribution.getAppEndNo());
            dto.setTotalAppCount(distribution.getTotalAppCount());
            dto.setAmount(distribution.getAmount());
            if (distribution.getIssuedByType() != null) {
                dto.setIssued_by_type_id(distribution.getIssuedByType().getAppIssuedId());
            }
            if (distribution.getIssuedToType() != null) {
                dto.setIssued_to_type_id(distribution.getIssuedToType().getAppIssuedId());
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
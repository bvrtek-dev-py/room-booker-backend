package com.example.company.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.common.exception.PermissionDeniedException;
import com.example.company.entity.CompanyEntity;

@Component
public class CompanyGetIfUserIsOwner {
    @Autowired
    private CompanyGetById companyGetById;

    public CompanyEntity execute(Long companyId, Long userId) {
        CompanyEntity company = companyGetById.execute(companyId);

        if (!company.getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        return company;
    }
}

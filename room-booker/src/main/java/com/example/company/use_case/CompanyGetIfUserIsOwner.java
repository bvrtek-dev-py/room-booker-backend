package com.example.company.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.common.exception.PermissionDeniedException;
import com.example.company.entity.CompanyEntity;

@Component
@RequiredArgsConstructor
public class CompanyGetIfUserIsOwner {
    private final CompanyGetById companyGetById;

    public CompanyEntity execute(@NotNull Long companyId, @NotNull Long userId) {
        CompanyEntity company = companyGetById.execute(companyId);

        if (!company.getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        return company;
    }
}

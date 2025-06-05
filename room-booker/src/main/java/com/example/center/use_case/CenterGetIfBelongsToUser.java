package com.example.center.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.center.entity.CenterEntity;
import com.example.common.exception.PermissionDeniedException;

@Component
public class CenterGetIfBelongsToUser {
    @Autowired
    private CenterGetById centerGetById;

    public CenterEntity execute(Long id, Long userId) {
        CenterEntity center = centerGetById.execute(id);

        if (!center.getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        return center;
    }
}

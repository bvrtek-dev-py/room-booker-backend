package com.example.center.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.center.entity.CenterEntity;
import com.example.common.exception.PermissionDeniedException;

@Component
@RequiredArgsConstructor
public class CenterGetIfBelongsToUser {
    private final CenterGetById centerGetById;

    public CenterEntity execute(@NotNull Long id, @NotNull Long userId) {
        CenterEntity center = centerGetById.execute(id);

        if (!center.getCompany().getUser().getId().equals(userId)) {
            throw new PermissionDeniedException();
        }

        return center;
    }
}

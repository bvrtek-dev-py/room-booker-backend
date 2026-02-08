package com.example.center.use_case;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import com.example.center.entity.CenterEntity;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectNotFoundException;

@Component
@RequiredArgsConstructor
public class CenterGetById {
    private final CenterRepository centerRepository;

    public CenterEntity execute(@NotNull Long id) {
        return centerRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }
}

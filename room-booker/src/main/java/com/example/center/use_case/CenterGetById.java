package com.example.center.use_case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.center.entity.CenterEntity;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectNotFoundException;

@Component
public class CenterGetById {
    @Autowired
    private CenterRepository centerRepository;

    public CenterEntity execute(Long id) {
        return centerRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException());
    }
}

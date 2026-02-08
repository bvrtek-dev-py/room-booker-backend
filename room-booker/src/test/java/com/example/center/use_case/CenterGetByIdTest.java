package com.example.center.use_case;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.center.entity.CenterEntity;
import com.example.center.repository.CenterRepository;
import com.example.common.exception.ObjectNotFoundException;

@ExtendWith(MockitoExtension.class)
class CenterGetByIdTest {

    @Mock
    private CenterRepository centerRepository;

    @InjectMocks
    private CenterGetById centerGetById;

    @Test
    void shouldReturnCenterWhenExists() {
        CenterEntity center = new CenterEntity(1L, "Center", "Desc", null);
        given(centerRepository.findById(1L)).willReturn(Optional.of(center));

        CenterEntity result = centerGetById.execute(1L);

        assertThat(result).isEqualTo(center);
    }

    @Test
    void shouldThrowExceptionWhenCenterNotFound() {
        given(centerRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> centerGetById.execute(1L));
    }
}

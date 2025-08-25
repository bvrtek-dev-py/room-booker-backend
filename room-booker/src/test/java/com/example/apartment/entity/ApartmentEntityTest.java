package com.example.apartment.entity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import com.example.apartment.type.Facility;
import com.example.center.entity.CenterEntity;

class ApartmentEntityTest {

    @Test
    void shouldCopyApartmentWithNullableFields() {
        // given
        CenterEntity originalCenter = mock(CenterEntity.class);
        ApartmentEntity original = new ApartmentEntity(
                1L,
                "Original Name",
                4,
                "Original Description",
                100.0,
                2,
                List.of(Facility.WIFI),
                originalCenter
        );

        CenterEntity newCenter = mock(CenterEntity.class);

        // when
        ApartmentEntity copy = original.with(
                10L,
                "New Name",
                null,
                null,
                150.0,
                null,
                null,
                newCenter
        );

        // then
        assertThat(copy).isNotSameAs(original);
        assertThat(copy.getId()).isEqualTo(10L);
        assertThat(copy.getName()).isEqualTo("New Name");
        assertThat(copy.getNumberOfPeople()).isEqualTo(4);
        assertThat(copy.getDescription()).isEqualTo("Original Description");
        assertThat(copy.getPricePerNight()).isEqualTo(150.0);
        assertThat(copy.getAmount()).isEqualTo(2);
        assertThat(copy.getFacilities()).isEqualTo(List.of(Facility.WIFI));
        assertThat(copy.getCenter()).isEqualTo(newCenter);
    }
}
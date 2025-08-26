package com.example.center.entity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.example.address.entity.AddressEntity;

class CenterAddressEntityTest {
    @Test
    void shouldUpdateOnlyProvidedFieldsUsingWith() {
        // given
        CenterAddressEntity entity = new CenterAddressEntity(
                "Main St", "Springfield", "IL", "12345", "USA", 99L
        );

        // when
        AddressEntity updated = entity.with(
                "Broadway",
                null,
                "NY",
                null,
                "Canada"
        );

        // then
        assertThat(updated.getStreet()).isEqualTo("Broadway");
        assertThat(updated.getCity()).isEqualTo("Springfield");
        assertThat(updated.getState()).isEqualTo("NY");
        assertThat(updated.getZipCode()).isEqualTo("12345");
        assertThat(updated.getCountry()).isEqualTo("Canada");
        assertThat(updated.getObjectId()).isEqualTo(99L);
    }
}
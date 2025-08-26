package com.example.center.entity;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.example.address.entity.AddressEntity;

class CenterAddressEntityTest {

    @Test
    void shouldCreateCenterAddressEntity() {
        // when
        CenterAddressEntity entity = new CenterAddressEntity(
                "Main St", "Springfield", "IL", "12345", "USA", 99L
        );

        // then
        assertThat(entity.getStreet()).isEqualTo("Main St");
        assertThat(entity.getCity()).isEqualTo("Springfield");
        assertThat(entity.getState()).isEqualTo("IL");
        assertThat(entity.getZipCode()).isEqualTo("12345");
        assertThat(entity.getCountry()).isEqualTo("USA");
        assertThat(entity.getObjectId()).isEqualTo(99L);
    }

    @Test
    void shouldUpdateOnlyProvidedFieldsUsingWith() {
        // given
        CenterAddressEntity entity = new CenterAddressEntity(
                "Main St", "Springfield", "IL", "12345", "USA", 99L
        );

        // when
        AddressEntity updated = entity.with(
                "Broadway",   // changed street
                null,         // keep city
                "NY",         // changed state
                null,         // keep zipCode
                "Canada"      // changed country
        );

        // then
        assertThat(updated.getStreet()).isEqualTo("Broadway");
        assertThat(updated.getCity()).isEqualTo("Springfield"); // unchanged
        assertThat(updated.getState()).isEqualTo("NY");
        assertThat(updated.getZipCode()).isEqualTo("12345");    // unchanged
        assertThat(updated.getCountry()).isEqualTo("Canada");
        assertThat(updated.getObjectId()).isEqualTo(99L);       // unchanged
    }

    @Test
    void shouldReturnNewInstanceWhenCallingWith() {
        // given
        CenterAddressEntity entity = new CenterAddressEntity(
                "Main St", "Springfield", "IL", "12345", "USA", 99L
        );

        // when
        AddressEntity updated = entity.with("Broadway", "NYC", "NY", "54321", "Canada");

        // then
        assertThat(updated).isNotSameAs(entity);
    }
}
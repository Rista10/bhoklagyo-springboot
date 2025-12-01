package com.backend.bhoklagyo.mapper;

import com.backend.bhoklagyo.dto.address.DeliveryAddressCreateDTO;
import com.backend.bhoklagyo.dto.address.DeliveryAddressResponseDTO;
import com.backend.bhoklagyo.model.DeliveryAddress;
import com.backend.bhoklagyo.model.Order;

public class DeliveryAddressMapper {

    public static DeliveryAddress toEntity(DeliveryAddressCreateDTO dto, Order order) {
        DeliveryAddress entity = new DeliveryAddress();
        entity.setOrder(order);
        entity.setMunicipality(dto.getMunicipality());
        entity.setWardNumber(dto.getWardNumber());
        entity.setToleName(dto.getToleName());
        entity.setLandmark(dto.getLandmark());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }

    public static DeliveryAddressResponseDTO toResponseDTO(DeliveryAddress entity) {
        DeliveryAddressResponseDTO dto = new DeliveryAddressResponseDTO();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setMunicipality(entity.getMunicipality());
        dto.setWardNumber(entity.getWardNumber());
        dto.setToleName(entity.getToleName());
        dto.setLandmark(entity.getLandmark());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
}

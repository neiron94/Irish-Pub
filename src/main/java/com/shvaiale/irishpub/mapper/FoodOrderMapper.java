package com.shvaiale.irishpub.mapper;

import com.shvaiale.irishpub.database.entity.FoodOrder;
import com.shvaiale.irishpub.dto.FoodOrderDto;
import org.springframework.stereotype.Component;

@Component
public class FoodOrderMapper implements Mapper<FoodOrder, FoodOrderDto> {

    @Override
    public FoodOrderDto map(FoodOrder from) {
        return new FoodOrderDto(
                from.getId(),
                from.getCustomer().getIdPerson(),
                from.getTime(),
                from.getDishes(),
                from.getWorker().getIdPerson());
    }
}

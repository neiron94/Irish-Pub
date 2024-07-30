package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.FoodOrder;
import com.shvaiale.irishpub.database.repository.FoodOrderRepository;
import com.shvaiale.irishpub.dto.FoodOrderDto;
import com.shvaiale.irishpub.mapper.FoodOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;
    private final FoodOrderMapper foodOrderMapper;

    @Transactional(readOnly = true)
    public Optional<FoodOrderDto> findByCustomerIdAndTime(int idCustomer, LocalDateTime time) {
        Optional<FoodOrder> maybeFoodOrder = foodOrderRepository.findBy(idCustomer, time);

        maybeFoodOrder.ifPresentOrElse(
                foodOrder -> log.info("Food order made by customer with id={} at {} is found.", idCustomer, time),
                () -> log.warn("Food order made by customer with id={} at {} is not found.", idCustomer, time));

        return maybeFoodOrder.map(foodOrderMapper::map);
    }
}

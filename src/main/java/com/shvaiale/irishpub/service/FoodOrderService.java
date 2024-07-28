package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.entity.Customer;
import com.shvaiale.irishpub.database.entity.Dish;
import com.shvaiale.irishpub.database.entity.FoodOrder;
import com.shvaiale.irishpub.database.entity.Worker;
import com.shvaiale.irishpub.database.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;

    @Transactional(readOnly = true)
    public String printFoodOrderInfo(int idCustomer, LocalDateTime time) {
        Optional<FoodOrder> maybeFoodOrder = foodOrderRepository.findBy(idCustomer, time);

        return maybeFoodOrder.map(foodOrder -> {
                    Customer customer = foodOrder.getCustomer();
                    Worker worker = foodOrder.getWorker();
                    String workerLine = worker == null ? "Order was made online" :
                            String.format("Order was taken by worker: %s %s", worker.getName(), worker.getSurname());

                    StringBuilder dishesStringBuilder = new StringBuilder();
                    BigDecimal totalPrice = new BigDecimal(0);
                    for (Dish dish : foodOrder.getDishes()) {
                        dishesStringBuilder.append(String.format("\nName: %s, Price: %s", dish.getName(), dish.getPrice()));
                        totalPrice = totalPrice.add(dish.getPrice());
                    }

                    log.info("Information about order with id={} is created.", foodOrder.getId());
                    return String.format("""
                                                
                            -----------------------------------
                            FOOD ORDER INFO
                            Order ID: %d
                            Customer: %s %s
                            Order time: %s
                            %s
                            Total price: %s
                            Dishes:%s
                            -----------------------------------
                                                
                            """, foodOrder.getId(), customer.getName(), customer.getSurname(), foodOrder.getTime(), workerLine, totalPrice, dishesStringBuilder);
                })
                .orElseGet(() -> {
                    log.warn("No order found from customer with id={} with time={}.", idCustomer, time);
                    return "";
                });
    }
}

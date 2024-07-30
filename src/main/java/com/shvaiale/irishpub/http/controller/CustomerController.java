package com.shvaiale.irishpub.http.controller;

import com.shvaiale.irishpub.dto.CustomerCreateEditDto;
import com.shvaiale.irishpub.dto.CustomerReadDto;
import com.shvaiale.irishpub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("customers", customerService.findAll());

        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Integer id) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/customer";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute CustomerCreateEditDto customer) {
        CustomerReadDto result = customerService.create(customer);

        return "redirect:customers/" + result.id();
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute CustomerCreateEditDto customer,
                         @PathVariable Integer id) {
        return customerService.update(id, customer)
                .map(c -> "redirect:/customers/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!customerService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return "redirect:/customers";
    }
}

package com.shvaiale.irishpub.http.controller;

import com.shvaiale.irishpub.dto.CustomerCreateEditDto;
import com.shvaiale.irishpub.dto.CustomerFilter;
import com.shvaiale.irishpub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String findAll(Model model, @ModelAttribute("filter") CustomerFilter filter) {
        model.addAttribute("customers", customerService.findAll(filter));

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

    @GetMapping("/create")
    public String getCreateForm(@ModelAttribute("customer") CustomerCreateEditDto customer) {
        return "customer/create";
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated CustomerCreateEditDto customer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("customer", customer);
            return "redirect:/customers/create";
        }

        return "redirect:/customers/" + customerService.create(customer).id();
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Validated CustomerCreateEditDto customer,
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

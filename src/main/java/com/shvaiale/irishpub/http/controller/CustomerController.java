package com.shvaiale.irishpub.http.controller;

import com.shvaiale.irishpub.dto.*;
import com.shvaiale.irishpub.service.CustomerService;
import com.shvaiale.irishpub.service.PersonalInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PersonalInformationService personalInformationService;

    @GetMapping
    public String findAll(Model model, @ModelAttribute("filter") CustomerFilter filter, Pageable pageable) {
        Page<CustomerReadDto> page = customerService.findAll(filter, pageable);
        model.addAttribute("customers", PageResponse.of(page));

        return "customer/customers";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Integer id) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/customer";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/{id}/edit")
    public String getEditForm(Model model, @PathVariable Integer id) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    return "customer/edit";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
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
                         @ModelAttribute PersonalInformationReadCreateDto personalInformation,
                         @PathVariable Integer id) {
        CustomerCreateEditDto customerToUpdate = new CustomerCreateEditDto(
                customer.birthDate(),
                customer.name(),
                customer.surname(),
                personalInformation);
        return customerService.update(id, customerToUpdate)
                .map(c -> "redirect:/customers/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

//    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!customerService.delete(id))
            throw new ResponseStatusException(NOT_FOUND);

        return "redirect:/customers";
    }

    @GetMapping("/{id}/info/attach")
    public String getAttachInfoForm(@ModelAttribute("id") @PathVariable Integer id) {
        return "customer/attach";
    }

    @PostMapping("/{id}/info")
    public String attachInfo(PersonalInformationReadCreateDto personalInformation) {
        PersonalInformationReadCreateDto result = personalInformationService.create(personalInformation);
        return "redirect:/customers/" + result.id();
    }

    @PostMapping("/{id}/info/delete")
    public String detachInfo(@PathVariable Integer id) {
        if (!personalInformationService.delete(id))
            throw new ResponseStatusException(NOT_FOUND);

        return "redirect:/customers/{id}";
    }
}

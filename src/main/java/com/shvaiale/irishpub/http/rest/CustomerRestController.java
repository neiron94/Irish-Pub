package com.shvaiale.irishpub.http.rest;

import com.shvaiale.irishpub.dto.*;
import com.shvaiale.irishpub.service.CustomerService;
import com.shvaiale.irishpub.service.PersonalInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerRestController {

    private final CustomerService customerService;
    private final PersonalInformationService personalInformationService;

    @GetMapping
    public PageResponse<CustomerReadDto> findAll(CustomerFilter filter, Pageable pageable) {
        Page<CustomerReadDto> page = customerService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public CustomerReadDto findById(@PathVariable Integer id) {
        return customerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerReadDto create(@Validated @RequestBody CustomerCreateEditDto customer) {
        return customerService.create(customer);
    }

    @PutMapping("/{id}")
    public CustomerReadDto update(@Validated @RequestBody CustomerCreateEditDto customer,
                                  @PathVariable Integer id) {
        return customerService.update(id, customer)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        if (!customerService.delete(id))
            throw new ResponseStatusException(NOT_FOUND);
    }

    @PostMapping("/{id}/info")
    public PersonalInformationReadCreateDto attachInfo(@RequestBody PersonalInformationReadCreateDto personalInformation) {
        return personalInformationService.create(personalInformation);

    }

    @DeleteMapping("/{id}/info")
    @ResponseStatus(NO_CONTENT)
    public void detachInfo(@PathVariable Integer id) {
        if (!personalInformationService.delete(id))
            throw new ResponseStatusException(NOT_FOUND);
    }
}

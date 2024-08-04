package com.shvaiale.irishpub.http.rest;

import com.shvaiale.irishpub.dto.*;
import com.shvaiale.irishpub.exception.CustomerNotFoundException;
import com.shvaiale.irishpub.http.handler.RestControllerExceptionHandler;
import com.shvaiale.irishpub.service.CustomerService;
import com.shvaiale.irishpub.service.PersonalInformationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static com.shvaiale.irishpub.http.handler.RestControllerExceptionHandler.getErrorsMap;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.ok;

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
    public ResponseEntity<CustomerReadDto> findById(@PathVariable Integer id) {
        return customerService.findById(id)
                .map(ok()::body)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody CustomerCreateEditDto customer) {
        if (customerService.findBy(customer.birthDate(), customer.name(), customer.surname()).isPresent())
            return status(CONFLICT).body("Customer with these name, surname and birth date already exist");

        CustomerReadDto customerDto = customerService.create(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerDto.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerReadDto> update(@Validated @RequestBody CustomerCreateEditDto customer,
                                                  @PathVariable Integer id) {
        return customerService.update(id, customer)
                .map(ok()::body)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return customerService.delete(id) ? ResponseEntity.noContent().build() :
            status(GONE).body(getErrorsMap("Customer with id %d already deleted".formatted(id)));
    }

    @PostMapping("/{id}/info")
    public ResponseEntity<?> attachInfo(@RequestBody @Validated PersonalInformationReadCreateDto personalInformation) {
        if (customerService.findById(personalInformation.id()).isEmpty())
            throw new CustomerNotFoundException(personalInformation.id());

        if (personalInformationService.findById(personalInformation.id()).isPresent())
            return status(CONFLICT).body(getErrorsMap("Customer with id %d have already attached personal info".formatted(personalInformation.id())));

        personalInformationService.create(personalInformation);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return created(location).build();
    }

    @DeleteMapping("/{id}/info")
    public ResponseEntity<?> detachInfo(@PathVariable Integer id) {
        if (customerService.findById(id).isEmpty())
            throw new CustomerNotFoundException(id);

        return personalInformationService.delete(id) ? noContent().build() :
                status(GONE).body(getErrorsMap("Personal info with id %d is already detached".formatted(id)));
    }
}

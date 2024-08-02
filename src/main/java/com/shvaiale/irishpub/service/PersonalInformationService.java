package com.shvaiale.irishpub.service;

import com.shvaiale.irishpub.database.repository.PersonalInformationRepository;
import com.shvaiale.irishpub.dto.PersonalInformationReadCreateDto;
import com.shvaiale.irishpub.mapper.PersonalInformationCreateMapper;
import com.shvaiale.irishpub.mapper.PersonalInformationReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonalInformationService {

    private final PersonalInformationRepository personalInformationRepository;
    private final PersonalInformationCreateMapper personalInformationCreateMapper;
    private final PersonalInformationReadMapper personalInformationReadMapper;

    public Optional<PersonalInformationReadCreateDto> findById(Integer id) {
        return personalInformationRepository.findById(id)
                .map(personalInformationReadMapper::map);
    }

    public PersonalInformationReadCreateDto create(PersonalInformationReadCreateDto personalInfo) {
        return Optional.of(personalInfo)
                .map(personalInformationCreateMapper::map)
                .map(personalInformationRepository::save)
                .map(personalInformationReadMapper::map)
                .orElseThrow();
    }

    public Optional<PersonalInformationReadCreateDto> update(PersonalInformationReadCreateDto personalInfoDto) {
        return personalInformationRepository.findById(personalInfoDto.id())
                .map(personalInfo -> personalInformationCreateMapper.map(personalInfoDto, personalInfo))
                .map(personalInformationRepository::saveAndFlush)
                .map(personalInformationReadMapper::map);
    }

    public boolean delete(Integer id) {
        return personalInformationRepository.findById(id)
                        .map(personalInformation -> {
                            personalInformationRepository.delete(personalInformation);
                            personalInformationRepository.flush();
                            return true;
                        })
                                .orElse(false);
    }
}

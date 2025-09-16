package com.petarrelic.profileserviceskillsync.service;

import com.petarrelic.profileserviceskillsync.dto.UserProfileDTO;
import com.petarrelic.profileserviceskillsync.model.UserProfile;
import com.petarrelic.profileserviceskillsync.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    public Logger log = LoggerFactory.getLogger(UserProfileService.class);

    private final UserProfileRepository userProfileRepository;
    private final ModelMapper modelMapper;

    public List<UserProfileDTO> getAllProfiles() {
        log.info("Getting all user profiles");
        return userProfileRepository.findAll().stream()
                .map(profile -> modelMapper.map(profile, UserProfileDTO.class))
                .collect(Collectors.toList());
    }

    public UserProfileDTO createProfile(UserProfileDTO dto) {
        log.info("Creating user profile {}", dto);
        UserProfile profile = modelMapper.map(dto, UserProfile.class);
        return modelMapper.map(userProfileRepository.save(profile), UserProfileDTO.class);
    }

    public UserProfileDTO getProfileById(Long id) {
        log.info("Getting user profile {}", id);
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with ID: " + id));
        return modelMapper.map(profile, UserProfileDTO.class);
    }

    public UserProfileDTO updateProfile(Long id, UserProfileDTO dto) {
        log.info("Updating user profile {}", id);
        UserProfile existing = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with ID: " + id));

        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        existing.setBio(dto.getBio());
        existing.setAvatarUrl(dto.getAvatarUrl());

        UserProfile updated = userProfileRepository.save(existing);
        return modelMapper.map(updated, UserProfileDTO.class);
    }

    public void deleteProfile(Long id) {
        log.info("Deleting user profile {}", id);
        if (!userProfileRepository.existsById(id)) {
            log.error("UserProfile with ID {} does not exist", id);
            throw new EntityNotFoundException("Profile not found with ID: " + id);
        }
        userProfileRepository.deleteById(id);
    }

}

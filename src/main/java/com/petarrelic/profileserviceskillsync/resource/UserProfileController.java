package com.petarrelic.profileserviceskillsync.resource;

import com.petarrelic.profileserviceskillsync.dto.UserProfileDTO;
import com.petarrelic.profileserviceskillsync.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class UserProfileController {
    public Logger log = LoggerFactory.getLogger(UserProfileController.class);

    private final UserProfileService userProfileService;

    @GetMapping
    public List<UserProfileDTO> getUserProfiles() {
        List<UserProfileDTO> allProfiles = userProfileService.getAllProfiles();
        log.info("All users fetched.");
        return allProfiles;
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createProfile(@RequestBody UserProfileDTO dto) {
        UserProfileDTO newProfile = userProfileService.createProfile(dto);
        log.info("New profile created.");
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfile);
    }

    @GetMapping("/{id}")
    public UserProfileDTO getProfile(@PathVariable Long id) {
        UserProfileDTO profileById = userProfileService.getProfileById(id);
        log.info("User profile with id {} fetched.", id);
        return profileById;
    }

    @PutMapping("/{id}")
    public UserProfileDTO updateProfile(@PathVariable Long id, @RequestBody UserProfileDTO dto) {
        UserProfileDTO userProfile = userProfileService.updateProfile(id, dto);
        log.info("User profile with id {} updated.", id);
        return userProfile;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        userProfileService.deleteProfile(id);
        log.info("User profile with id {} deleted.", id);
        return ResponseEntity.noContent().build();
    }
}

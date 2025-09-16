package com.petarrelic.profileserviceskillsync.service;

import com.petarrelic.profileserviceskillsync.dto.UserProfileDTO;
import com.petarrelic.profileserviceskillsync.model.UserProfile;
import com.petarrelic.profileserviceskillsync.repository.UserProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {

    @Mock private UserProfileRepository repo;
    @Mock private ModelMapper mapper;

    private UserProfileService service;

    @BeforeEach
    void setUp() {
        service = new UserProfileService(repo, mapper);
    }

    @Test
    void testGetAllProfiles() {
        List<UserProfile> profiles = List.of(new UserProfile(1L, "petar", "petar@mail.com", "bio", "url"));
        when(repo.findAll()).thenReturn(profiles);
        when(mapper.map(any(), eq(UserProfileDTO.class))).thenReturn(new UserProfileDTO());

        List<UserProfileDTO> result = service.getAllProfiles();
        assertEquals(1, result.size());
    }

    @Test
    void testCreateProfile() {
        UserProfileDTO dto = new UserProfileDTO(null, "petar", "petar@mail.com", "bio", "url");
        UserProfile entity = new UserProfile(1L, "petar", "petar@mail.com", "bio", "url");

        when(mapper.map(dto, UserProfile.class)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.map(entity, UserProfileDTO.class)).thenReturn(new UserProfileDTO(1L, "petar", "petar@mail.com", "bio", "url"));

        UserProfileDTO result = service.createProfile(dto);

        assertNotNull(result);
        assertEquals("petar", result.getUsername());
    }

    @Test
    void testGetProfileById_found() {
        UserProfile entity = new UserProfile(1L, "petar", "petar@mail.com", "bio", "url");

        when(repo.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.map(entity, UserProfileDTO.class)).thenReturn(new UserProfileDTO(1L, "petar", "petar@mail.com", "bio", "url"));

        UserProfileDTO result = service.getProfileById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetProfileById_notFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.getProfileById(99L));
        assertEquals("Profile not found with ID: 99", ex.getMessage());
    }

    @Test
    void testUpdateProfile_found() {
        UserProfile existing = new UserProfile(1L, "old", "old@mail.com", "old bio", "old url");
        UserProfileDTO dto = new UserProfileDTO(null, "new", "new@mail.com", "new bio", "new url");
        UserProfile updated = new UserProfile(1L, "new", "new@mail.com", "new bio", "new url");

        when(repo.findById(1L)).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(updated);
        when(mapper.map(updated, UserProfileDTO.class)).thenReturn(new UserProfileDTO(1L, "new", "new@mail.com", "new bio", "new url"));

        UserProfileDTO result = service.updateProfile(1L, dto);

        assertEquals("new", result.getUsername());
        assertEquals("new@mail.com", result.getEmail());
    }

    @Test
    void testUpdateProfile_notFound() {
        UserProfileDTO dto = new UserProfileDTO(null, "new", "new@mail.com", "new bio", "new url");
        when(repo.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.updateProfile(99L, dto));
        assertEquals("Profile not found with ID: 99", ex.getMessage());
    }

    @Test
    void testDeleteProfile_found() {
        when(repo.existsById(1L)).thenReturn(true);

        service.deleteProfile(1L);

        verify(repo).deleteById(1L);
    }

    @Test
    void testDeleteProfile_notFound() {
        when(repo.existsById(99L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.deleteProfile(99L));
        assertEquals("Profile not found with ID: 99", ex.getMessage());
    }

}

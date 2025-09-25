package com.demoProject.demo.service.impl;

import com.demoProject.demo.model.dto.request.AdminUserRequest;
import com.demoProject.demo.model.dto.response.AdminUserResponse;
import com.demoProject.demo.model.entity.User;
import com.demoProject.demo.repository.AdminUserRepository;
import com.demoProject.demo.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository userRepository;

    /**
     * Retrieves a paginated list of users. If a keyword is provided, it searches
     * for users whose first name, last name, or email contains the keyword (case-insensitive).
     *
     * @param keyword the search keyword (nullable)
     * @param page    the page number (0-based)
     * @param size    the number of records per page
     * @return a page of {@link AdminUserResponse}
     */
    @Override
    public Page<AdminUserResponse> getUsers(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<User> users;

        if (keyword != null && !keyword.isEmpty()) {
            users = userRepository
                    .findByUserInfo_FirstNameContainingIgnoreCaseOrUserInfo_LastNameContainingIgnoreCaseOrUserInfo_EmailContainingIgnoreCase(
                            keyword, keyword, keyword, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(this::mapToResponse);
    }

    /**
     * Retrieves a paginated list of users filtered by their role.
     *
     * @param roleName the name of the role (e.g., "ADMIN")
     * @param page     the page number (0-based)
     * @param size     the number of records per page
     * @return a page of {@link AdminUserResponse}
     */
    @Override
    public Page<AdminUserResponse> getUsersByRole(String roleName, int page, int size) {
        Page<User> users = userRepository.findByRoleName(roleName, PageRequest.of(page, size));
        return users.map(this::mapToResponse);
    }

    /**
     * Retrieves the details of a specific user by ID.
     *
     * @param id the user ID
     * @return an {@link Optional} containing {@link AdminUserResponse} if found, empty otherwise
     */
    @Override
    public Optional<AdminUserResponse> getUserDetail(String id) {
        return userRepository.findById(id).map(this::mapToResponse);
    }

    /**
     * Updates a user's information such as phone number, address, and enabled status.
     *
     * @param id      the user ID
     * @param request the update request containing new values
     * @return an {@link Optional} containing the updated {@link AdminUserResponse} if found, empty otherwise
     */
    @Override
    public Optional<AdminUserResponse> updateUser(String id, AdminUserRequest request) {
        return userRepository.findById(id).map(user -> {
            if (request.getPhoneNumber() != null) {
                user.getUserInfo().setPhoneNumber(request.getPhoneNumber());
            }
            if (request.getAddress() != null) {
                user.getUserInfo().setAddress(request.getAddress());
            }
            user.setEnabled(request.isEnabled());
            userRepository.save(user);
            return mapToResponse(user);
        });
    }

    /**
     * Bans or unbans a user by updating their locked status.
     *
     * @param id  the user ID
     * @param ban true to ban (lock), false to unban (unlock)
     * @return true if the user exists and was updated, false otherwise
     */
    @Override
    public boolean banUser(String id, boolean ban) {
        return userRepository.findById(id).map(user -> {
            user.setLocked(ban);
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    /**
     * Maps a {@link User} entity to an {@link AdminUserResponse}.
     *
     * @param user the user entity
     * @return the corresponding {@link AdminUserResponse}
     */
    private AdminUserResponse mapToResponse(User user) {
        AdminUserResponse res = new AdminUserResponse();
        res.setId(user.getId());
        res.setAddress(user.getUserInfo().getAddress());
        res.setFullName(user.getUserInfo().getFullName());
        res.setEmail(user.getUserInfo().getEmail());
        res.setPhoneNumber(user.getUserInfo().getPhoneNumber());
        res.setGender(user.getUserInfo().getGender());
        res.setCreatedAt(user.getUserInfo().getCreatedAt());
        res.setEnabled(user.isEnabled());
        res.setLocked(user.isLocked());
        return res;
    }
}

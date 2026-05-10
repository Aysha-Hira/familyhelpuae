package com.familyhelpuae.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.familyhelpuae.exception.ResourceNotFound;
import com.familyhelpuae.user.model.User;
import com.familyhelpuae.user.model.UserRelationship;
import com.familyhelpuae.user.repository.UserRepository;
import com.familyhelpuae.user.service.UserRelationshipService;
import com.mongodb.lang.NonNull;

@Service
@Validated
public class UserRelationshipServiceImpl implements UserRelationshipService {

	private final UserRepository userRepository;

	public UserRelationshipServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<UserRelationship> getAllRelationships(@NonNull String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		return user.getRelationships();
	}

	@Override
	public List<UserRelationship> getRelationshipsByType(@NonNull String userId, @NonNull String relationshipType) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		return user.getRelationshipsByType(relationshipType);
	}

	public User addRelationships(User user) {
		for (UserRelationship relationship : user.getRelationships()) {
			if (relationship.getUserId() != null) {
				// related to registered user
				user = addRelationship(user.getUserID(), relationship.getUserId(), relationship.getRelationshipType());
			} else {
				// related to non-registered user
				user = addRelationship(user.getUserID(), relationship.getName(), relationship.getEmail(),
						relationship.getRelationshipType());
			}

		}
		return user;
	}

	@Override
	public User addRelationship(@NonNull String userId, @NonNull String anotherUserID,
			@NonNull String relationshipType) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		User anotherUser = userRepository.findById(anotherUserID)
				.orElseThrow(() -> new ResourceNotFound("User", "id", anotherUserID));

		if (user.equals(anotherUser))
			throw new IllegalArgumentException("Relationship cannot be added to self");
		
		if (user.hasRelationship(anotherUserID))
			throw new IllegalArgumentException(
					"Relationship already exists between user " + userId + " and user " + anotherUserID);

		// add relationship to user
		user.getRelationships().add(new UserRelationship(anotherUserID, relationshipType));

		// save updated user
		return userRepository.save(user);
	}

	public User addRelationship(@NonNull String userId, @NonNull String name, @NonNull String email,
			@NonNull String relationshipType) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		if (user.hasRelationship(name, email))
			throw new IllegalArgumentException(
					"Relationship already exists between user " + user.getFirstName() + " " + user.getLastName()
							+ " and non-registered user with name " + name + " and email " + email);

		// add relationship to user
		user.getRelationships().add(new UserRelationship(name, email, relationshipType));

		// save updated user
		return userRepository.save(user);
	}

	@Override
	public User updateRelationship(@NonNull String userId, @NonNull String anotherUserID,
			@NonNull String relationshipType) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		userRepository.findById(anotherUserID).orElseThrow(() -> new ResourceNotFound("User", "id", anotherUserID));

		if (!user.hasRelationship(anotherUserID))
			throw new IllegalArgumentException(
					"Relationship does not exist between user " + user.getFirstName() + " " + user.getLastName()
							+ " and user " + anotherUserID);

		// update relationship
		user.updateRelationship(anotherUserID, relationshipType);
		// save updated user
		return userRepository.save(user);

	}

	public User updateRelationship(@NonNull String userId, @NonNull String name, @NonNull String email,
			@NonNull String relationshipType) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		if (!user.hasRelationship(name, email))
			throw new IllegalArgumentException(
					"Relationship does not exist between user " + user.getFirstName() + " " + user.getLastName()
							+ " and non-registered user with name " + name + " and email " + email);

		// update relationship
		user.updateRelationship(name, email, relationshipType);
		// save updated user
		return userRepository.save(user);

	}

	@Override
	public void removeRelationship(@NonNull String userId, @NonNull String anotherUserID) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		if (!user.hasRelationship(anotherUserID)) {
			throw new IllegalArgumentException(
					"Relationship does not exist between user " + user.getFirstName() + " " + user.getLastName()
							+ " and user " + anotherUserID);
		}

		user.deleteRelationship(anotherUserID);

		userRepository.save(user);
	}

	public void removeRelationship(@NonNull String userId, @NonNull String name, @NonNull String email) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));

		if (!user.hasRelationship(name, email)) {
			throw new IllegalArgumentException(
					"Relationship does not exist between user " + user.getFirstName() + " " + user.getLastName()
							+ " and non-registered user with name " + name + " and email " + email);
		}

		user.deleteRelationship(name, email);

		userRepository.save(user);
	}

	public boolean hasRelationshipWithRegisteredUser(@NonNull String userId, @NonNull String anotherUserId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
		return user.hasRelationship(anotherUserId);
	}

	public boolean hasRelationshipWithNonRegisteredUser(@NonNull String userId, @NonNull String name,
			@NonNull String email) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
		return user.hasRelationship(name, email);
	}

}

/**
 * Section: 104
 * Group number: 4
 * Student IDs and names: 
 * Laisa Sanjida Isra: 1089635
 * Fatima Syed Wasti: 1095190
 * Aysha Hira: 1088000
 */

package com.familyhelpuae.request.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Document(collection = "request")
public class Request {
	@Id
	String requestId; // auto-generated unique identifier by mongodb

	@Field(name = "requestTitle")
	@NotBlank(message = "Title is required")
	String requestTitle;

	@Field(name = "requestingFamilyId")
	String requestingFamilyId; // the family that made the request

	@Field(name = "requestingUserId")
	String requestingUserId; // the user that made the request

	@Field(name = "requestDescription")
	String requestDescription; // details about the help needed

	@Field(name = "location")
	String location;

	@Field(name = "requestType")
	@NotBlank(message = "Request type is required")
	String requestType;

	@Field(name = "urgencyLevel")
	String urgencyLevel; // e.g., "low", "medium", "high"

	@Field(name = "requestStatus")
	String requestStatus; // e.g., "open", "matched", "completed", "cancelled"

	@Field(name = "createdAt")
	LocalDateTime createdAt; // when the request was made

	@Field(name = "updatedAt")
	LocalDateTime updatedAt; // when the request record was last updated

	@Field(name = "linkedOfferIds")
	List<String> linkedOfferIds = new ArrayList<>(); // offers that have responded to this request

	@Field(name = "linkedOfferId")
	String linkedOfferId; // set when this request is made FOR a specific offer

}
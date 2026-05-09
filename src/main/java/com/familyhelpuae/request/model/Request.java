package com.familyhelpuae.request.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.*;

import lombok.Data;

@Data
@Document(collection="request")
public class Request {
	@Id
    String requestId; // auto-generated unique identifier by mongodb
	
	@Field(name="requestTitle")
	String requestTitle;
	
	@Field(name="requestingFamilyId")
    String requestingFamilyId; // the family that made the request
	
	@Field(name="requestingUserId")
    String requestingUserId; // the user that made the request
	
	@Field(name="requestDescription")
    String requestDescription; // details about the help needed
	
	@Field(name="location")
    String location;
	
	@Field(name="requestType")
    String requestType;
	
	@Field(name="urgencyLevel")
    String urgencyLevel; // e.g., "low", "medium", "high"
	
	@Field(name="requestStatus")
    String requestStatus; // e.g., "open", "matched", "completed", "cancelled"
	
	@Field(name="createdAt")
    LocalDateTime createdAt; // when the request was made
	
	@Field(name="updatedAt")
	LocalDateTime updatedAt; // when the request record was last updated
}
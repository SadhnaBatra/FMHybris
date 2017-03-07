package com.fmo.wom.integration.sap.action;

import com.fmo.wom.domain.ProblemBO.ProblemStatus;

public enum SapReturnStatus {

  SUCCESS						(0,  true, true, 	"SUCCESS", 						false, ProblemStatus.PART_FOUND),
	OBSOLETE_USE_TO_COMPLETION	(50, true, true, 	"OBSOLETE_USE_TO_COMPLETION",   false, ProblemStatus.PART_FOUND),
	NOT_ALLOWED					(10, false, false, 	"NOT_ALLOWED_TO_ORDER",		 	true, ProblemStatus.PART_FOUND),
	PART_NOT_FOUND				(30, false, true, 	"PART_NOT_FOUND", 				true, ProblemStatus.PART_NOT_FOUND),
	PART_IS_PRERELEASE			(40, false, false, 	"PART_IS_PRERELEASE", 			true, ProblemStatus.PART_FOUND),
	OBSOLETE_RETURN_ALLOWED		(60, false, false, 	"OBSOLETE_RETURN_ALLOWED",      true, ProblemStatus.PART_FOUND),
	OBSOLETE_NO_RETURN_ALLOWED	(70, false, false, 	"OBSOLETE_NO_RETURN_ALLOWED",   true, ProblemStatus.PART_FOUND),
	MISSING_STANDARD_COST		(20, false, false, 	"MISSING_STANDARD_COST",		true, ProblemStatus.PART_SETUP_ERROR),
	MISSING_PRICE				(80, false, false, 	"MISSING_PRICE",				true, ProblemStatus.PART_SETUP_ERROR),
	NO_SOURCE_SPECIFIED			(85, false, false, 	"NO_SOURCE_SPECIFIED",			true, ProblemStatus.PART_SETUP_ERROR),
	NO_VALID_BOM				(90, false, false, 	"NO_VALID_BOM",	                true, ProblemStatus.PART_SETUP_ERROR),
	NO_ITEM_CATEGORY_AVAILABLE	(95, false, false, 	"NO_ITEM_CATEGORY_AVAILABLE",	true, ProblemStatus.PART_SETUP_ERROR),
	BAD_ACCOUNT					(99, false, false, 	"BAD_ACCOUNT", 			        true, ProblemStatus.PART_NOT_FOUND);
	
	private int sapStatus;
	private boolean canBeAdded;
	private boolean redisplay;
	private String descriptionKey;
	private boolean createProblem;
	private ProblemStatus problemStatus;
	
	SapReturnStatus(int sapStatus, boolean canReAdded, boolean redisplay, 
	                String description, boolean createProblem, ProblemStatus problemStatus) {
		this.sapStatus = sapStatus;
		this.canBeAdded = canReAdded;
		this.redisplay = redisplay;
		this.descriptionKey = description;
		this.createProblem = createProblem;
		this.problemStatus = problemStatus;
	}

	public int getSapStatus() {
		return sapStatus;
	}
	
	public String getSapStatusAsString() {
		return sapStatus + "";
	}

	public boolean isCanBeAdded() {
		return canBeAdded;
	}

	public boolean isRedisplay() {
		return redisplay;
	}

	public String getDescriptionKey() {
		return descriptionKey;
	}

	public boolean shouldCreateProblem() {
		return createProblem;
	}

	public ProblemStatus getProblemStatus() {
        return problemStatus;
    }

	
    public static SapReturnStatus getSapStatusFromCode(final int code) {

		for (SapReturnStatus aStatus : SapReturnStatus.values()) {

			if (code == aStatus.getSapStatus()) {
				return aStatus;
			}
		}

		throw new IllegalStateException("SapReturnStatus " + code + " does not exist.");
	}
	
	public static SapReturnStatus getSapStatusFromCode(final String codeAsString ) {

		int code = Integer.parseInt(codeAsString);
		return getSapStatusFromCode(code);
	}
	
}

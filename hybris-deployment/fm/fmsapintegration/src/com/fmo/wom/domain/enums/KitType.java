package com.fmo.wom.domain.enums;

public enum KitType {

    PRESELECT  ("P", "Preselect", false),
    COMPONENT  ("C", "Component", true);
    
    private String code;
    private String description;
    private boolean configurationRequired;
    
    KitType(String code, String description, boolean configurationRequired) {
        this.code = code;
        this.description = description;
        this.configurationRequired = configurationRequired;
    }

    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isConfigurationRequired() {
        return configurationRequired;
    }

    public static KitType getKitTypeFromCode(String code) {

        for (KitType aKitType : KitType.values()) {

            if (code == aKitType.getCode()) {
                return aKitType;
            }
        }

        throw new IllegalStateException("KitType " + code + " does not exist.");
    }

}

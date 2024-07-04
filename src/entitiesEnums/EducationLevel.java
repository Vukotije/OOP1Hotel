package entitiesEnums;

public enum EducationLevel {
    NO_EDUCATION(0.0, "No education"),
    PRIMARY_SCHOOL(1.0, "Primary school"),
    HIGH_SCHOOL(1.5, "High school"),
    BACHELORS_DEGREE(2.0, "Bachelor's degree"),
    MASTERS_DEGREE(3.0, "Master's degree"),
    DOCTORATE_DEGREE(4.0, "Doctorate degree");

    private final double value;
    private final String displayName;

    EducationLevel(double value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public double getValue() {
        return this.value;
    }

//	==============================================================================================
//											STRING
//	==============================================================================================
	
    @Override
    public String toString() {
        return this.displayName;
    }
    
    public static EducationLevel fromString(String value) {
		for (EducationLevel educationLevel : EducationLevel.values()) {
			if (educationLevel.toString().equals(value)) {
				return educationLevel;
			}
		}
		throw new IllegalArgumentException("No enum constant Education Level with value " + value);
	}
}
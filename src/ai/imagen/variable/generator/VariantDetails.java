package ai.imagen.variable.generator;

public class VariantDetails {
    private String variantName;
    private String fileDirectory;
    private String sdVersion;
    private String description;
    private String activationText;
    private String negativeText;
    private String notes;
    private double preferredWeight;
    private int variantCounter;  // Declaración de la variable

    // Constructor con los parámetros adecuados
    public VariantDetails(String variantName, String fileDirectory, String sdVersion, String description, 
                          String activationText, String negativeText, String notes, double preferredWeight) {
        this.variantName = variantName;
        this.fileDirectory = fileDirectory;
        this.sdVersion = sdVersion;
        this.description = description;
        this.activationText = activationText;
        this.negativeText = negativeText;
        this.notes = notes;
        this.preferredWeight = preferredWeight;
    }
    
    // Constructor con valores predeterminados
    public VariantDetails(String variantName, String fileDirectory) {
        this.variantName = variantName;
        this.fileDirectory = fileDirectory;
        this.sdVersion = "Unknown";  // Asignamos un valor predeterminado
        this.description = "";  // Asignamos un valor predeterminado
        this.activationText = "";  // Asignamos un valor predeterminado
        this.negativeText = "";  // Asignamos un valor predeterminado
        this.notes = "";  // Asignamos un valor predeterminado
        this.preferredWeight = 1.0;  // Asignamos un valor predeterminado
    }

    public VariantDetails() {
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
    public int getVariantCounter() {
        return variantCounter;
    }

    public void setVariantCounter(int variantCounter) {
        this.variantCounter = variantCounter;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getSdVersion() {
        return sdVersion;
    }

    public void setSdVersion(String sdVersion) {
        this.sdVersion = sdVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivationText() {
        return activationText;
    }

    public void setActivationText(String activationText) {
        this.activationText = activationText;
    }

    public String getNegativeText() {
        return negativeText;
    }

    public void setNegativeText(String negativeText) {
        this.negativeText = negativeText;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getPreferredWeight() {
        return preferredWeight;
    }

    public void setPreferredWeight(double preferredWeight) {
        this.preferredWeight = preferredWeight;
    }

	public String getVARIANT_NAME() {
		return this.variantName;
	}

	public String getFILE_DIRECTORY() {
		return this.fileDirectory;
	}

}

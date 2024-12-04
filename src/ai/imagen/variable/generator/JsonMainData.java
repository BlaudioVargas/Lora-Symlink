package ai.imagen.variable.generator;

public class JsonMainData {
    private String SD_VERSION;
    private String DESCRIPTION;
    private String ACTIVITATION_TEXT;
    private String NEGATIVE_TEXT;
    private String NOTES;
    private double PREFERRED_WEIGHT;

    // Getters y Setters
    public String getSD_VERSION() {
        return SD_VERSION;
    }

    public void setSD_VERSION(String SD_VERSION) {
        this.SD_VERSION = SD_VERSION;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getACTIVITATION_TEXT() {
        return ACTIVITATION_TEXT;
    }

    public void setACTIVITATION_TEXT(String ACTIVITATION_TEXT) {
        this.ACTIVITATION_TEXT = ACTIVITATION_TEXT;
    }

    public String getNEGATIVE_TEXT() {
        return NEGATIVE_TEXT;
    }

    public void setNEGATIVE_TEXT(String NEGATIVE_TEXT) {
        this.NEGATIVE_TEXT = NEGATIVE_TEXT;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public double getPREFERRED_WEIGHT() {
        return PREFERRED_WEIGHT;
    }

    public void setPREFERRED_WEIGHT(double PREFERRED_WEIGHT) {
        this.PREFERRED_WEIGHT = PREFERRED_WEIGHT;
    }
}

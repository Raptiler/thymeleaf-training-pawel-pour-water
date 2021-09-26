package pl.pzprojects.thymeleaftrainingpawel;

public class GlassPourDto {
    private String glassId;
    private String targetId;
    private String pourQuantity;

    public GlassPourDto(String glassId, String targetId, String quantity) {
        this.glassId = glassId;
        this.targetId = targetId;
        this.pourQuantity = quantity;
    }

    public GlassPourDto() {
    }

    public String getGlassId() {
        return glassId;
    }

    public void setGlassId(String glassId) {
        this.glassId = glassId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getPourQuantity() {
        return pourQuantity;
    }

    public void setPourQuantity(String pourQuantity) {
        this.pourQuantity = pourQuantity;
    }
}

package top.atstudy.component.enums;

public enum EnumRequestMethod {
    GET("GET"),
    POST("POST");

    private String code;

    private EnumRequestMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

package top.atstudy.component.enums;

public enum EnumFileType {

    IMAGE("image", "图片"),
    VOICE("voice", "语言"),
    VIDEO("video", "视频"),
    FILE("file", "普通文件");

    private String code;
    private String label;

    private EnumFileType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

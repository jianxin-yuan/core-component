package com.yuan.ocr.enums;

/**
 * @author yuan
 */
public enum SideTypeEnum {

    /**
     * 正面
     */
    FACE("face"),
    /**
     * 反面
     */
    BACK("back");

    private final String value;

    SideTypeEnum(String value) {
        this.value = value;
    }


    public String getValue() {
        return value;
    }
}

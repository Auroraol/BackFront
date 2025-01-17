package com.zhang.musicrs.exception;

/**
 * @author: Zhang Chaojie
 * @comments:
 * @since Date： 2023/04/19 10:24
 */
public class FieldErrorVM {
    private final String objectName;

    private final String field;

    private final String errorMsg;

    public FieldErrorVM(String dto, String field, String errorMsg) {
        this.objectName = dto;
        this.field = field;
        this.errorMsg = errorMsg;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}

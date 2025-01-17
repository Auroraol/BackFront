package com.zhang.musicrs.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zhang Chaojie
 * @comments:
 * @since Date： 2023/04/19 10:24
 */
public class ErrorVM implements Serializable {

    private final String errorCode;
    private final String resultMsg;
    private boolean isSuccess;

    private List<FieldErrorVM> fieldErrors;

    public ErrorVM(String errorCode) {
        this(errorCode, null);
    }

    public ErrorVM(String errorCode, String resultMsg) {
        this.errorCode = errorCode;
        this.resultMsg = resultMsg;
        this.isSuccess = false;
    }

    public ErrorVM(String errorCode, String resultMsg, List<FieldErrorVM> fieldErrors) {
        this.errorCode = errorCode;
        this.resultMsg = resultMsg;
        this.fieldErrors = fieldErrors;
    }

    public void add(String objectName, String field, String errorMsg) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorVM(objectName, field, errorMsg));
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public List<FieldErrorVM> getFieldErrors() {
        return fieldErrors;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }
}

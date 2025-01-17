package com.zhang.musicrs.exception;

/**
 * @author: Zhang Chaojie
 * @comments:
 * @since Date： 2023/04/19 10:22
 */
public class ParamException extends RuntimeException {

    private final String errorCode;
    private final String errorMsg;

    public ParamException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public ParamException(String errorMsg) {
        this("error.validation", errorMsg);
    }

    public ErrorVM getErrorVM() {
        return new ErrorVM(errorCode, errorMsg);
    }

}

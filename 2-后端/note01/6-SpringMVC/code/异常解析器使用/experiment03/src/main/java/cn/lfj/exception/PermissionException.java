package cn.lfj.exception;

/**
 * @Author: LFJ
 * @Date: 2023-10-18 21:30
 */
public class PermissionException extends RuntimeException{

	public PermissionException() {
	}

	public PermissionException(String message) {
		super(message);
	}
}

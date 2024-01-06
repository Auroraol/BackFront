package cn.lfj.exception;

/**
 * @Author: LFJ
 * @Date: 2023-10-18 21:29
 */
public class LoginException extends RuntimeException{
	public LoginException() {
	}

	public LoginException(String message) {
		super(message);
	}
}

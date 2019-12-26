package com.young.elasticsearch.exception;

/*************************
 * @author: YoungLu
 * @date: 2019/12/17 14:14
 * @description: 连接异常
 **************************/
public class ConnectionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConnectionException() {
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }
}

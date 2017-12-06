package com.ywc.mgt.processcontrol.base.exception;

/**
 * 强制退出递归
 *
 * @author yanwe
 * createTime 2017-01-2017/1/18 13:43
 */
public class EndRecursionException extends Exception {

    private Object object;

    public EndRecursionException(String message) {
        super(message);
    }

    public EndRecursionException(Object object) {
        this.object = object;
    }

    public EndRecursionException(String message,Object object) {
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

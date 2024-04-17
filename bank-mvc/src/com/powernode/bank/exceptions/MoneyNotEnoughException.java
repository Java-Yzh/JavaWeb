package com.powernode.bank.exceptions;

/**
 * 余额不足异常
 * @author 老杜
 * @version 2.0
 * @since 2.0
 */
public class MoneyNotEnoughException extends Exception{

    public MoneyNotEnoughException(){

    }

    public MoneyNotEnoughException(String msg){
        super(msg);
    }
}

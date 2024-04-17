package com.powernode.bank.pojo;

/**
 * 账户实体类：封装账户信息的。
 * 一般是一张表一个。
 * pojo对象。
 * 有的人也会把这种专门封装数据的对象，称为bean对象。(javabean：咖啡豆)
 * 有的人也会把这种专门封装数据的对象，称为领域模型对象。domain对象。
 * 不同的程序员有不同的习惯。
 *
 * pojo、bean、domain.....
 *
 * @author 老杜
 * @version 1.0
 * @since 1.0
 */
public class Account { // 这种普通简单的对象被成为pojo对象。
    /**
     * 主键
     */
    // 一般这种属性不建议设计为基本数据类型，建议使用包装类。防止null带来的问题。
    //private long id;
    private Long id;

    /**
     * 账号
     */
    private String actno;

    /**
     * 余额
     */
    //private double balance;
    private Double balance;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", actno='" + actno + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActno() {
        return actno;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Account(Long id, String actno, Double balance) {
        this.id = id;
        this.actno = actno;
        this.balance = balance;
    }

    public Account() {
    }
}

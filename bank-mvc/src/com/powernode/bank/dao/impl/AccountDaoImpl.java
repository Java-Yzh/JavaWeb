package com.powernode.bank.dao.impl;

import com.powernode.bank.dao.AccountDao;
import com.powernode.bank.pojo.Account;
import com.powernode.bank.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AccountDao是负责Account数据的增删改查的。
 * 1. 什么是DAO？
 *      Data Access Object（数据访问对象）
 * 2. DAO实际上是一种设计模式，属于JavaEE的设计模式之一。（不是23种设计模式。）
 * 3. DAO只负责数据库表的CRUD，没有任何业务逻辑在里面。
 * 4. 没有任何业务逻辑，只负责表中数据增删改查的对象，有一个特殊的称谓：DAO对象。
 * 5. 为什么叫做AccountDao呢？
 *      这是因为这个DAO是专门处理t_act这张表的。
 *      如果处理t_user表的话，可以叫做：UserDao
 *      如果处理t_student表的话，可以叫做：StudentDao
 * 6. 一般情况下：一张表会对应一个DAO对象。
 * 7. DAO中的方法名很固定了，一般都是：
 *      insert
 *      deleteByXxx
 *      update
 *      selectByXxx
 *      selectAll
 *
 *
 * @author 老杜
 * @since 1.0
 * @version 1.0
 */
public class AccountDaoImpl implements AccountDao {

    /**
     * 插入账户信息
     * @param act  账户信息
     * @return 1表示插入成功
     */
    public int insert(Account act) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "insert into t_act(actno, balance) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, act.getActno());
            ps.setDouble(2, act.getBalance());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 根据主键删除账户
     * @param id 主键
     * @return
     */
    public int deleteById(Long id){
        PreparedStatement ps = null;
        int count = 0;
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "delete from t_act where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 更新账户
     * @param act
     * @return
     */
    public int update(Account act) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println(conn);
            String sql = "update t_act set balance = ? , actno = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, act.getBalance());
            ps.setString(2, act.getActno());
            ps.setLong(3, act.getId());
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    /**
     * 根据账号查询账户
     * @param actno
     * @return
     */
    public Account selectByActno(String actno){
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account act = null;
        try {
            Connection conn = DBUtil.getConnection();
            System.out.println(conn);
            String sql = "select id,balance from t_act where actno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, actno);
            rs = ps.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                Double balance = rs.getDouble("balance");
                // 将结果集封装成java对象
                act = new Account();
                act.setId(id);
                act.setActno(actno);
                act.setBalance(balance);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return act;
    }

    /**
     * 获取所有的账户
     * @return
     */
    public List<Account> selectAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "select id,actno,balance from t_act";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                // 取出数据
                Long id = rs.getLong("id");
                String actno = rs.getString("actno");
                Double balance = rs.getDouble("balance");
                // 封装对象
                /*Account account = new Account();
                account.setId(id);
                account.setActno(actno);
                account.setBalance(balance);*/
                Account account = new Account(id, actno, balance);
                // 加到List集合
                list.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return list;
    }

}























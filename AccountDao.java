package com.cashsystem.dao;

import com.cashsystem.common.AccountStatus;
import com.cashsystem.common.AccountType;
import com.cashsystem.entity.Account;
import com.cashsystem.entity.Order;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static javax.swing.UIManager.getInt;

public class AccountDao extends BaseDao{

    // 登录  操作数据库
    public Account login(String username,String password){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        ResultSet resultSet = null;//接受集合
        Account account = null;

        try{
            //和数据库建立连接
            connection = this.getConnection(true);
            String sql = "select id,username,password,name,account_type,account_status from account where" +
                    " username=?and password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, DigestUtils.md5Hex(password));

            resultSet = preparedStatement.executeQuery();
            //返回结果集到resultSet
            if (resultSet.next()){
                //解析resultSet，拿到对应的account
                account = this.extractAccount(resultSet);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }
    private static Account extractAccount(ResultSet resultSet) throws SQLException{
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setName(resultSet.getString("name"));
        account.setAccountType(AccountType.valueOf(resultSet.getInt("account_type")));
        account.setAccountStatus(AccountStatus.valueOf(resultSet.getInt("account_status")));
        return account;
    }

    //注册
    public boolean register(Account account){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean effect = false;

        try {
            connection = this.getConnection(true);
            String sql = "insert into account(username,password,name,account_type,account_status) values(?,?,?,?,?)";
            //Statement.RETURN_GENERATED_KEYS 可以获取插入这条语句的自增值
            preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,DigestUtils.md5Hex(account.getPassword()));
            preparedStatement.setString(3,account.getName());
            preparedStatement.setInt(4,account.getAccountType().getFlg());
            preparedStatement.setInt(5,account.getAccountStatus().getFlg());

            effect = (preparedStatement.executeUpdate() == 1);
            //获取自增的主键
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                account.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(resultSet,preparedStatement,connection);
        }
        return effect;
    }


}

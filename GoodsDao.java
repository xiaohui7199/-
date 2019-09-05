package com.cashsystem.dao;

import com.cashsystem.entity.Goods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDao extends BaseDao{
    public List<Goods> quarryAllGoods(){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        ResultSet resultSet = null;//接受集合
        List<Goods> list= new ArrayList<>();

        try{
            connection = this.getConnection(true);
            String sql = "select id,name,introduce,stock,unit,price,discount from goods";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Goods goods = this.extractGoods(resultSet);
                if (goods != null){
                    list.add(goods);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(resultSet,preparedStatement,connection);
        }
        return list;
    }

    public Goods extractGoods(ResultSet resultSet) throws SQLException {
        Goods goods = new Goods();
        goods.setId(resultSet.getInt("id"));
        goods.setName(resultSet.getString("name"));
        goods.setIntroduce(resultSet.getString("introduce"));
        goods.setStock(resultSet.getInt("stock"));
        goods.setUnit(resultSet.getString("unit"));
        goods.setPrice(resultSet.getInt("price"));
        goods.setDiscount(resultSet.getInt("discount"));
        return goods;




    }
    //上架商品
    public boolean putAwayGoods(Goods goods){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        ResultSet resultSet = null;//接受集合
        boolean effect = false;
        try {
            connection = this.getConnection(true);

            String sql = "insert into goods(name,introduce,stock,unit,price,discount) values(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,goods.getName());
            preparedStatement.setString(2,goods.getIntroduce());
            preparedStatement.setInt(3,goods.getStock());
            preparedStatement.setString(4,goods.getUnit());
            preparedStatement.setInt(5,goods.getPrice());
            preparedStatement.setInt(6,goods.getDiscount());

            effect = (preparedStatement.executeUpdate() == 1);
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                Integer id = resultSet.getInt(1);
                goods.setId(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(resultSet,preparedStatement,connection);
        }
        return effect;
    }

    public Goods getGoods(int goodsId){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        ResultSet resultSet = null;//接受集合
        Goods goods = null;

        try{
            connection = this.getConnection(true);
            String sql = "select*from goods where id=?";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,goodsId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                goods = this.extractGoods(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }
    //更新商品
    public boolean modifyGoods(Goods goods){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        boolean effect = false;

        try{
            connection = this.getConnection(true);
            String sql = "update goods set name=?,introduce=?,stock=?,unit=?,price=?,discount=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,goods.getName());
            preparedStatement.setString(2,goods.getIntroduce());
            preparedStatement.setInt(3,goods.getStock());
            preparedStatement.setString(4,goods.getUnit());
            preparedStatement.setInt(5,goods.getPrice());
            preparedStatement.setInt(6,goods.getDiscount());
            preparedStatement.setInt(7,goods.getId());

            effect = (preparedStatement.executeUpdate()==1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(null,preparedStatement,connection);
        }
        return effect;
    }

    //下架商品
    public boolean soldOutGoods(int goodsId){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        ResultSet resultSet = null;//接受集合
        boolean effect = false;


       try {
            connection = this.getConnection(true);
            String sql = "delete from goods where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,goodsId);
            effect = preparedStatement.executeUpdate()==1;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
           this.closeResource(null,preparedStatement,connection);
       }
        return effect;
    }


    public boolean updateAfterPay(Goods goods,int goodsBuyNum){
        Connection connection = null;//返回连接数据
        PreparedStatement preparedStatement = null;//预处理sql命令
        boolean effect = false;
        try{
            connection = this.getConnection(true);
            String sql = "update goods set stock=? where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,goods.getStock()-goodsBuyNum);
            preparedStatement.setInt(2,goods.getId());

            if(preparedStatement.executeUpdate() == 1){
                effect = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return effect;
    }
}

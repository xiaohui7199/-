package com.cashsystem.service;

import com.cashsystem.dao.GoodsDao;
import com.cashsystem.entity.Goods;

import java.util.List;

public class GoodsSerive {
    public Goods goods;
    public GoodsDao goodsDao;
    public GoodsSerive(){
        this.goodsDao = new GoodsDao();

    }

    public List<Goods> quarryAllGoods(){
        return this.goodsDao.quarryAllGoods();
    }

    //上架商品
    public boolean putAwayGoods(Goods goods){
        return this.goodsDao.putAwayGoods(goods);
    }

    //通过goodsId 拿到对应的货物
    public Goods getGoods(int goodId){
        return this.goodsDao.getGoods(goodId);
    }
    //更新商品
    public boolean modifyGoods(Goods goods){
        return this.goodsDao.modifyGoods(goods);
    }

    //下架商品

   public boolean soldOutGoods(int goodsId){
        return this.goodsDao.soldOutGoods(goodsId);
    }
    public boolean updateAfterPay(Goods goods,int goodsNum){
        return this.goodsDao.updateAfterPay(goods,goodsNum);
    }

}

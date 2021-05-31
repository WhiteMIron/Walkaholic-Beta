package com.example.demo.item.repository;

import com.example.demo.item.model.dto.ItemInfo;
import com.example.demo.item.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {



    //장착 정보
    @Query(value ="SELECT IFNULL(i.item_name,'') as ItemName, i.id as ItemId, i.item_type as ItemType,i.item_price as ItemPrice , i.item_fileName as ItemFileName from user_equip_item uei LEFT JOIN item i ON uei.item_id = i.id WHERE uei.user_id = :Id",nativeQuery=true)
    List<ItemInfo> findByUser(Long Id);

//    i.item_name as ItemName, i.id as ItemId, i.item_type as ItemType, i.item_price

    //상점에서 파는 아이템 정보
    @Query(value ="SELECT i.item_name as ItemName, i.id as ItemId, i.item_type as ItemType, i.item_price as ItemPrice, " +
            " i.item_filename as ItemFileName from  item i",nativeQuery=true)
    List<ItemInfo>  selectAll();



    //아이템 정보
    @Query(value ="SELECT i.item_name as ItemName, i.id as ItemId, i.item_type as ItemType, i.item_price as ItemPrice, i.item_filename as ItemFileName from  item i  WHERE i.id = :id",nativeQuery=true)
    ItemInfo findByItemId(String id);



}

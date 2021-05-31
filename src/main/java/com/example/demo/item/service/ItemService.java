package com.example.demo.item.service;

import com.example.demo.item.model.dto.ItemInfo;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ItemService(UserRepository userReposioty, ItemRepository itemRepository){
        this.userRepository = userReposioty;
        this.itemRepository = itemRepository;
    }
//
    @Transactional
    public List<ItemInfo> getEquipInfo(Long id){

        return itemRepository.findByUser(id);
    }

    @Transactional
    public List<ItemInfo> getAllItemInfo(){
        return itemRepository.selectAll();
    }

    @Transactional
    public ItemInfo getItemInfo(String id){
        return itemRepository.findByItemId(id);
    }
}


package com.example.demo.item.model.dto;

import io.swagger.annotations.ApiModelProperty;

public interface ItemInfo {

    Long getItemId();
    Long getItemPrice();
    String getItemType();
    String getItemName();
    String getItemFileName();
    String getItemCount();


}

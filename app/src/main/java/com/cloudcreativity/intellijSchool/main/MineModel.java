package com.cloudcreativity.intellijSchool.main;

import com.cloudcreativity.intellijSchool.entity.UserEntity;
import com.cloudcreativity.intellijSchool.utils.SPUtils;

public class MineModel {
    public UserEntity userEntity;

    MineModel() {
        userEntity = SPUtils.get().getUser();
    }
}

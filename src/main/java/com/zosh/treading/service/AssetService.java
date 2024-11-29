package com.zosh.treading.service;

import com.zosh.treading.model.Asset;
import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;

import java.util.List;

public interface AssetService {
    Asset createAsset(User user, Coin coin, double quantity);

    Asset getAssetById(Long assetId) throws Exception;

    Asset getAssetByUserIdAndId(Long UserId,Long assetId);

    List<Asset> getUsersAsets(Long userId);

    Asset updateAsset(Long assetId,double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinId(Long userId, String coinId);

    void deleteAsset(Long assetId);

}

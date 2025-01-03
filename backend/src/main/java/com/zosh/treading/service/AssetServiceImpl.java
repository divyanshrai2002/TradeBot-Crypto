package com.zosh.treading.service;

import com.zosh.treading.model.Asset;
import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService{
    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        Asset asset=new Asset();
        asset.setUser(user);
        asset.setCoin(coin);
        asset.setQuantity(quantity);
        asset.setBuyprice(coin.getCurrentPrice());
        return assetRepository.save(asset);
    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {

        return assetRepository.findById(assetId).orElseThrow(()->new Exception("asset not found"));
    }

    @Override
    public Asset getAssetByUserIdAndId(Long UserId, Long assetId) {
        return null;
    }

    @Override
    public List<Asset> getUsersAsets(Long userId) { // Spelling mistake in asset so take getUsersAset only
        return assetRepository.findByUserId(userId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Asset oldAsset=getAssetById(assetId);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());

        return assetRepository.save(oldAsset);
    }

    @Override
    public Asset findAssetByUserIdAndCoinId(Long userId, String coinId) {
        return assetRepository.findByUserIdAndCoinId(userId,coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);

    }
}

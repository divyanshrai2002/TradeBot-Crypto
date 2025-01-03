package com.zosh.treading.Controller;

import com.zosh.treading.model.Asset;
import com.zosh.treading.model.User;
import com.zosh.treading.service.AssetService;
import com.zosh.treading.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @Autowired
    private UserService userService;

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
            @PathVariable String coinId,
            @RequestHeader ("Authorization") String jwt
       ) throws Exception {
         User user=userService.findUserProfileByJwt(jwt);
         Asset asset= assetService.findAssetByUserIdAndCoinId(user.getId(),coinId);
         return ResponseEntity.ok().body(asset);

    }

    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUser(
            @RequestHeader("Authorization")String jwt
    )throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        List<Asset> assets=assetService.getUsersAsets(user.getId());//Spelling mistake in asset
        return ResponseEntity.ok().body(assets);
    }

}

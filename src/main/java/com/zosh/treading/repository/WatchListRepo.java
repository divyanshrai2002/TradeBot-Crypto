package com.zosh.treading.repository;

import com.zosh.treading.model.WatchList;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepo extends JpaRepository<WatchList,Long> {
    WatchList findByUserId(Long userid);
}

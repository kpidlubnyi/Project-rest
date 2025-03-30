package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Model.WatchList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WatchListService {
    private WatchListRepository watchlistRepository;

    public WatchListService(WatchListRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }
    @Transactional
    public List<WatchList> getWatchlistByUserId(Integer userId) {
        List<WatchList> watchList =  watchlistRepository.getWatchlistByUserId(userId);
        System.out.println(watchList == null);
        System.out.println(watchList);
        return watchList;
    }
}

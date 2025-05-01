package com.aeribmm.filmcritic.Service;

import com.aeribmm.filmcritic.DAO.UserRepository;
import com.aeribmm.filmcritic.DAO.WatchListRepository;
import com.aeribmm.filmcritic.Exception.userException.UserNotFoundException;
import com.aeribmm.filmcritic.Model.UserModel.User;
import com.aeribmm.filmcritic.Model.WatchListModel.WatchList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WatchListService {
    private final WatchListRepository watchlistRepository;
    private final UserRepository repository;

    public WatchListService(WatchListRepository watchlistRepository,UserRepository userRepo) {
        this.watchlistRepository = watchlistRepository;
        this.repository = userRepo;
    }
    @Transactional
    public List<WatchList> getWatchlistByUserId(Integer userId) {//status and exception handled
        User user = repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        return watchlistRepository.getWatchlistByUserId(userId);
    }

    public List<WatchList> getAll() {
        return watchlistRepository.findAll();
    }
}

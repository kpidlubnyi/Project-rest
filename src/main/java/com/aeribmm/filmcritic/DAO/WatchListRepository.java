package com.aeribmm.filmcritic.DAO;

import com.aeribmm.filmcritic.Model.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface WatchListRepository extends JpaRepository<WatchList,Integer> {
    @Transactional
    @Procedure(name = "b0j1ffermfvmtrwyhcti.getWatchlistByUserId")
//    @Procedure(value = "getWatchlistByUserId")
//    @Query(value = "SELECT * FROM watchlist WHERE user_id =2;", nativeQuery = true)
    List<WatchList> getWatchlistByUserId(@Param("p_user_id") Integer userId);
}

package com.nnk.services;

import com.nnk.domain.Rating;

import java.util.List;

public interface RatingService {
    List<Rating> getAllRatings();
    Rating getRatingById(Integer id);
    void addRating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber);
    void updateRating(Integer id, String moodysRating, String sandPRating, String fitchRating, Integer orderNumber);
    void deleteRating(Integer id);
}

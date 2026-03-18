package com.nnk.services;

import com.nnk.domain.Rating;
import com.nnk.repositories.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public List<Rating> getAllRatings() {
        log.debug("Retrieving all ratings");
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRatingById(Integer id) {
        log.debug("Retrieving rating with id: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID must be provided");
        }
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
    }

    @Override
    public void addRating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        log.debug("Adding rating with moodysRating: {}, sandPRating: {}, fitchRating: {}, orderNumber: {}", moodysRating, sandPRating, fitchRating, orderNumber);
        if (moodysRating == null || sandPRating == null || fitchRating == null || orderNumber == null) {
            throw new IllegalArgumentException("All parameters must be provided");
        }
        if (orderNumber < 0) {
            throw new IllegalArgumentException("Order number must be non-negative");
        }

        final Rating rating = new Rating(moodysRating, sandPRating, fitchRating, orderNumber);
        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(Integer id, String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        log.debug("Updating rating with id: {}, moodysRating: {}, sandPRating: {}, fitchRating: {}, orderNumber: {}", id, moodysRating, sandPRating, fitchRating, orderNumber);
        if (id == null) {
            throw new IllegalArgumentException("ID must be provided");
        }
        if (moodysRating == null || sandPRating == null || fitchRating == null || orderNumber == null) {
            throw new IllegalArgumentException("All parameters must be provided");
        }
        if (orderNumber < 0) {
            throw new IllegalArgumentException("Order number must be non-negative");
        }


        final Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
        rating.setMoodysRating(moodysRating);
        rating.setSandPRating(sandPRating);
        rating.setFitchRating(fitchRating);
        rating.setOrderNumber(orderNumber);
        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Integer id) {
        log.debug("Deleting rating with id: {}", id);
        if (id == null) {
            throw new IllegalArgumentException("ID must be provided");
        }
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
        ratingRepository.delete(rating);
    }


}

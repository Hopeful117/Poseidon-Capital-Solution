package com.nnk.services;

import com.nnk.domain.Rating;
import com.nnk.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class RatingServiceTest {


    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @ParameterizedTest
    @CsvSource({"1, 10, 5, 'Excellent rating'",
            "2, 20, 4, 'Good rating'",
            "3, 30, 3, 'Average rating'"
    })
    public void testAddRating(int orderNumber, int moodysRating, int sandPRating, String fitchRating) {


        // When
        ratingService.addRating(String.valueOf(moodysRating), String.valueOf(sandPRating), fitchRating, orderNumber);
        ArgumentCaptor<Rating> captor = ArgumentCaptor.forClass(Rating.class);

        // Then
        verify(ratingRepository).save(captor.capture());
        Rating savedRating = captor.getValue();
        assertEquals(savedRating.getMoodysRating(), String.valueOf(moodysRating));
        assertEquals(savedRating.getSandPRating(), String.valueOf(sandPRating));
        assertEquals(savedRating.getFitchRating(), String.valueOf(fitchRating));


    }

    @ParameterizedTest
    @CsvSource(value = {
            "null, 10, 5, 'Excellent rating'",
            "2, null, 4, 'Good rating'",
            "3, 30, null, 'Average rating'",

    }, nullValues = "null")
    public void testAddRatingShouldThrowExceptionWhenInvalidOrMissingParameters(
            Integer orderNumber,
            Integer moodysRating,
            Integer sandPRating,
            String fitchRating) {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ratingService.addRating(
                        moodysRating == null ? null : String.valueOf(moodysRating),
                        sandPRating == null ? null : String.valueOf(sandPRating),
                        fitchRating,
                        orderNumber
                )
        );

        assertTrue(
                exception.getMessage().contains("All parameters must be provided") ||
                        exception.getMessage().contains("Order number must be non-negative") ||
                        exception.getMessage().contains("Order number must be unique")
        );
    }

    @Test
    public void testGetAllRatings() {
        //Given

        List<Rating> mockRatings = List.of(
                new Rating("Aaa", "AAA", "AAA", 1),
                new Rating("Baa", "BBB", "BBB", 2)
        );
        when(ratingRepository.findAll()).thenReturn(mockRatings);
        // When
        List<Rating> ratings = ratingService.getAllRatings();


        // Then
        assert (ratings.size() == 2);
        assert (ratings.get(0).getMoodysRating().equals("Aaa"));
        assert (ratings.get(1).getSandPRating().equals("BBB"));
        assert (ratings.get(0).getFitchRating().equals("AAA"));
        assert (ratings.get(1).getOrderNumber() == 2);


    }

    @Test
    public void testGetRatingById() {
        // Given
        Rating mockRating = new Rating("Aaa", "AAA", "AAA", 1);
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.of(mockRating));

        // When
        Rating rating = ratingService.getRatingById(1);

        // Then
        assert (rating.getMoodysRating().equals("Aaa"));
        assert (rating.getSandPRating().equals("AAA"));
        assert (rating.getFitchRating().equals("AAA"));
        assert (rating.getOrderNumber() == 1);
    }

    @Test
    public void testGetRatingByIdShouldThrowExceptionWhenIdIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ratingService.getRatingById(null));
        assert (exception.getMessage().contains("ID must be provided"));
    }

    @Test
    public void testGetRatingByIdShouldThrowExceptionWhenRatingNotFound() {
        // Given
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ratingService.getRatingById(999));
        assert (exception.getMessage().contains("Rating not found with id: 999"));
    }


}

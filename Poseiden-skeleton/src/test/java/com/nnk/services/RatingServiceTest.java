package com.nnk.services;

import com.nnk.domain.Rating;
import com.nnk.exceptions.EntityNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        Rating rating = new Rating(String.valueOf(moodysRating), String.valueOf(sandPRating), fitchRating, orderNumber);
        // When
        ratingService.create(rating);
        ArgumentCaptor<Rating> captor = ArgumentCaptor.forClass(Rating.class);

        // Then
        verify(ratingRepository).save(captor.capture());
        Rating savedRating = captor.getValue();
        assertEquals(savedRating.getMoodysRating(), String.valueOf(moodysRating));
        assertEquals(savedRating.getSandPRating(), String.valueOf(sandPRating));
        assertEquals(savedRating.getFitchRating(), String.valueOf(fitchRating));


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
        List<Rating> ratings = ratingService.findAll();


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
        Rating rating = ratingService.findById(1);

        // Then
        assert (rating.getMoodysRating().equals("Aaa"));
        assert (rating.getSandPRating().equals("AAA"));
        assert (rating.getFitchRating().equals("AAA"));
        assert (rating.getOrderNumber() == 1);
    }


    @Test
    public void testGetRatingByIdShouldThrowExceptionWhenRatingNotFound() {
        // Given
        when(ratingRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> ratingService.findById(999));

    }

    @ParameterizedTest
    @CsvSource({
            "1, 'Aaa', 'AAA', 'AAA', 10, 'Updated Aaa', 'Updated AAA', 'Updated AAA'",
            "2, 'Baa', 'BBB', 'BBB', 20, 'Updated Baa', 'Updated BBB', 'Updated BBB'",
            "3, 'Caa', 'CCC', 'CCC', 30, 'Updated Caa', 'Updated CCC', 'Updated CCC'"
    })
    public void testUpdateRating(int id, String originalMoodys, String originalSandP, String originalFitch,
                                 int newOrderNumber, String newMoodys, String newSandP, String newFitch) {
        // Given
        Rating existingRating = new Rating(originalMoodys, originalSandP, originalFitch, 5);
        existingRating.setId(id);

        Rating updatedRating = new Rating(newMoodys, newSandP, newFitch, newOrderNumber);
        updatedRating.setId(id);

        when(ratingRepository.findById(id)).thenReturn(java.util.Optional.of(existingRating));

        // When
        ratingService.update(updatedRating);

        // Then
        ArgumentCaptor<Rating> captor = ArgumentCaptor.forClass(Rating.class);
        verify(ratingRepository).save(captor.capture());
        Rating savedRating = captor.getValue();

        assertEquals(newMoodys, savedRating.getMoodysRating());
        assertEquals(newSandP, savedRating.getSandPRating());
        assertEquals(newFitch, savedRating.getFitchRating());
        assertEquals(newOrderNumber, savedRating.getOrderNumber());
        assertEquals(id, savedRating.getId());
    }

    @Test
    public void testUpdateRatingShouldThrowExceptionWhenRatingNotFound() {
        // Given
        Rating ratingToUpdate = new Rating("Aaa", "AAA", "AAA", 1);
        ratingToUpdate.setId(999);

        when(ratingRepository.findById(999)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> ratingService.update(ratingToUpdate));
    }

    @Test
    public void testDeleteRating() {
        // Given
        when(ratingRepository.existsById(1)).thenReturn(true);

        // When
        ratingService.deleteById(1);

        // Then
        verify(ratingRepository).deleteById(1);
    }

    @Test
    public void testDeleteRatingShouldThrowExceptionWhenRatingNotFound() {
        // Given
        when(ratingRepository.existsById(999)).thenReturn(false);

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> ratingService.deleteById(999));
    }

}

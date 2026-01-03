package com.ms.reviewms.reviews;

import java.util.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.ms.reviewms.reviews.messaging.ReviewMessageProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;
    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> addReview(@RequestParam Long companyId, @RequestBody Review review) {
        boolean isreviewAdded = reviewService.addReview(companyId, review);  
        if(!isreviewAdded){
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
        }
        reviewMessageProducer.sendReview(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        return new ResponseEntity<Review>(reviewService.getReviewById(reviewId),HttpStatus.OK);
    }
    
    @PutMapping("/{reviewid}")
    public ResponseEntity<String> updateReview(@PathVariable String reviewid, @PathVariable String companyId, @RequestBody Review review) {
        boolean isReviewUpdated = reviewService.updateReview(Long.parseLong(reviewid), review);
        if(!isReviewUpdated){
            return new ResponseEntity<>("Company or Review not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@RequestParam Long companyId, @PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if(!isReviewDeleted){  
            return new ResponseEntity<>("Company or Review not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);
        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }
    
}

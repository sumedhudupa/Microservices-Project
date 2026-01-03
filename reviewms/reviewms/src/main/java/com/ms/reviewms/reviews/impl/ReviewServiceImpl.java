package com.ms.reviewms.reviews.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.reviewms.reviews.Review;
import com.ms.reviewms.reviews.ReviewRepository;
import com.ms.reviewms.reviews.ReviewService;



@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId); 
        return reviews;
    }
    @Override
    public boolean addReview(Long companyId, Review review) {
        if(companyId!=null && review!=null){
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }
    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }
    @Override
    public boolean updateReview(Long reviewId, Review review) {
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if(existingReview!=null){
            existingReview.setTitle(review.getTitle());
            existingReview.setDescription(review.getDescription());
            existingReview.setRating(review.getRating());
            existingReview.setCompanyId(review.getCompanyId()); 
            reviewRepository.save(existingReview);
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteReview(Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId).orElse(null);
        if(existingReview!=null){
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
    
    
}

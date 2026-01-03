package com.ms.jobms.job.dto;

import java.util.List;

import com.ms.jobms.job.Job;
import com.ms.jobms.job.external.Company;
import com.ms.jobms.job.external.Review;

import lombok.Data;
@Data
public class JobDTO{
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
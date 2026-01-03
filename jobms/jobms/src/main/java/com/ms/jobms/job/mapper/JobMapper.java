package com.ms.jobms.job.mapper;

import java.util.List;

import com.ms.jobms.job.Job;
import com.ms.jobms.job.dto.JobDTO;
import com.ms.jobms.job.external.Company;
import com.ms.jobms.job.external.Review;

public class JobMapper {
    public static JobDTO maptoJobWithCompanyDTO(Job job, Company company, List<Review> reviews){
        JobDTO jobdto = new JobDTO();
        jobdto.setMaxSalary(job.getMaxSalary());
        jobdto.setMinSalary(job.getMinSalary());
        jobdto.setId(job.getId());
        jobdto.setTitle(job.getTitle());
        jobdto.setDescription(job.getDescription());
        jobdto.setLocation(job.getLocation());
        jobdto.setCompany(company);
        jobdto.setReviews(reviews);
        return jobdto;
    }
}

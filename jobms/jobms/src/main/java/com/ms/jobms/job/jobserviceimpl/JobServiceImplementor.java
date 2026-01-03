package com.ms.jobms.job.jobserviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.ms.jobms.job.Job;
import com.ms.jobms.job.JobRepository;
import com.ms.jobms.job.JobService;
import com.ms.jobms.job.clients.CompanyClient;
import com.ms.jobms.job.clients.ReviewClient;
import com.ms.jobms.job.dto.JobDTO;
import com.ms.jobms.job.external.Company;
import com.ms.jobms.job.external.Review;
import com.ms.jobms.job.mapper.JobMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImplementor implements JobService {

    //private  List<Job> jobs = new ArrayList<>();
    //private Long nextID = 1L;
    JobRepository jobRepository;
    @Autowired
    RestTemplate restTemplate;
    private  CompanyClient companyClient;
    private ReviewClient reviewClient;
    public JobServiceImplementor(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }//because jobRepository is a bean(managed by spring), and will be autowired at runtime thnx to this constructor

    @Override
    @CircuitBreaker(name="companyBreaker")
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobWithCompanyDTOs = new ArrayList<>();
        //RestTemplate resttemplate = new RestTemplate();
        return jobs.stream().map(this::convertToDto).toList();
    }
    private JobDTO convertToDto(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        System.out.println(company);
        JobDTO jobdto = JobMapper.maptoJobWithCompanyDTO(job, company,reviews);
        return jobdto;
    }
    @Override
    public void createJob(Job job) {
        //job.setId(nextID++);
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobByID(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try{
        jobRepository.deleteById(id);
        return true;}catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;//Ofcourse
        }

        return false;
    }

}

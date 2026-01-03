package com.ms.jobms.job;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ms.jobms.job.dto.JobDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JobController {
    private JobService jobservice;

    public JobController(JobService jobservice) {
        this.jobservice = jobservice;
    }

    //same as
    //@RequestMapping(value = "/jobs",method = RequestMethod.GET)
    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> findAll(){

        return new ResponseEntity<>(jobservice.findAll(),HttpStatus.OK);
        /*
        or do this
        return ResponseEntity.ok(jobservice.findAll());
        */
    }

    @PostMapping("/jobs")
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobservice.createJob(job);
        //return new ResponseEntity<String>("Job added successfully.",HttpStatus.OK);
        return ResponseEntity.ok("Job added successfully.");
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobdto = jobservice.getJobByID(id);
        if(jobdto != null) return new ResponseEntity<>(jobdto,HttpStatus.OK);
        return new ResponseEntity<>(jobdto,HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean deleted = jobservice.deleteJobById(id);
        if(deleted) return ResponseEntity.ok("Job posting is deleted successfully.");
        return new ResponseEntity<>("Job posting was not found to be deleted.",HttpStatus.NOT_FOUND);
    }


    @PutMapping("/jobs/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
        boolean updated = jobservice.updateJobById(id,updatedJob);
        if(updated) return  ResponseEntity.ok("Job posting is updated.");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /*
    * could have used @RequestMapping at class level with "/jobs" at line 11 just below @RestController
    * since all APIs contain the same path, like @RequestMapping("/jobs")(making /jobs a parent or a base URL)
    * and then removing "/jobs" from every mapping and simply using @Get and @Post mapping without any ()
    * Simple as that :)
    * */
}

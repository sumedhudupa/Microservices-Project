package com.ms.jobms.job;

import org.springframework.data.jpa.repository.JpaRepository;//can also use .data.repository.CrudRepository,
                                                             // but this is extended by JpaRepository,
                                                             // giving us flush and save and flush

public interface JobRepository extends JpaRepository<Job, Long> {

}

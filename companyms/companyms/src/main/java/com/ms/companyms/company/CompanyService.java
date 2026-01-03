package com.ms.companyms.company;

import java.util.List;
import com.ms.companyms.company.dto.ReviewMessage;
public interface CompanyService {
    List<Company> getAllCompanies();
    boolean updateCompany(Company updatedCompany, Long id);
    void createCompany(Company company);
    boolean deleteCompany(Long id);
    Company getCompanyById(Long companyId);
    void updateCompanyRating(ReviewMessage reviewMessage);
}

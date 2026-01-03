package com.ms.companyms.company.companyserviceimpl;

import org.springframework.stereotype.Service;

import com.ms.companyms.company.Company;
import com.ms.companyms.company.CompanyRepository;
import com.ms.companyms.company.CompanyService;
import com.ms.companyms.company.clients.ReviewClient;
import com.ms.companyms.company.dto.ReviewMessage;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImplementor implements CompanyService {

    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImplementor(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company updatedCompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if(companyOptional.isPresent()){
            Company company = companyOptional.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
            companyRepository.save(company);
            return true;//Ofcourse
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
         companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try{
            companyRepository.deleteById(id);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println("Received review message: " + reviewMessage.getDescription());
        Company company = getCompanyById(reviewMessage.getCompanyId());
        Double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

}

package com.thoughtmechanix.licenses.services;

import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.config.ServiceConfig;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService
{
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    ServiceConfig config;

    public License getLicense(String organizationId, String licenseId)
    {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(
                organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }

    public License getLicense(String organizationId, String licenseId, String clientType)
    {
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        Organization organization = retrieveOrgInfo(organizationId, clientType);

        return license
                .withOrganizationName( organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId)
    {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license)
    {
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void updateLicense(License license)
    {
        licenseRepository.save(license);
    }

    public void deleteLicense(License license)
    {
        licenseRepository.delete(license);
    }

    private Organization retrieveOrgInfo(String organizationId, String clientType)
    {
        Organization organization = null;

        switch(clientType)
        {
            case "feign":
                System.out.println("I am using the Feign client.");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the Rest Template Client");
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the Discovery Client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
        }

        return organization;
    }
}

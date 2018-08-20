package com.thoughtmechanix.organization.services;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrganizationService
{
    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId)
    {
        return organizationRepository.findById(organizationId);
    }

    public void saveOrg(Organization organization)
    {
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);
    }

    public void updateOrg(Organization org){
        organizationRepository.save(org);
    }

    public void deleteOrg(Organization org){
        organizationRepository.delete( org.getId());
    }
}

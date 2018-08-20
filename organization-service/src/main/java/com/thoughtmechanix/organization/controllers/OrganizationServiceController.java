package com.thoughtmechanix.organization.controllers;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organizations")
public class OrganizationServiceController
{
    @Autowired
    private OrganizationService organizationService;

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.GET)
    public Organization getOrganization(@PathVariable("organizationId") String organizationId)
    {
        return organizationService.getOrg(organizationId);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.POST)
    public void saveOrganization(
            @PathVariable("organizationId") String organizationId,
            @RequestBody Organization organization
    )
    {
        organizationService.saveOrg(organization);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.PUT)
    public void updateOrganization(
            @PathVariable("organizationId") String organizationId,
            @RequestBody Organization organization
    )
    {
        organizationService.updateOrg(organization);
    }

    @RequestMapping(value = "/{organizationId}", method = RequestMethod.DELETE)
    public void deleteOrganization(
            @PathVariable("organizationId") String organizationId,
            @RequestBody Organization organization
    )
    {
        organizationService.deleteOrg(organization);
    }
}

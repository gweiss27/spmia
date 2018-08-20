package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organizationservice")
public interface OrganizationFeignClient
{
    @RequestMapping(
            value="/v1/organizations/{organizationId}",
            method= RequestMethod.GET,
            consumes = "application/json"
    )
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}

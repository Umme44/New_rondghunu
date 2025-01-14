package com.bits.hr.repository;

import com.bits.hr.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Organization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {}

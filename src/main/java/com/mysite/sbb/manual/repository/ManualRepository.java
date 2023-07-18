package com.mysite.sbb.manual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.manual.entity.Manual;

import java.util.Optional;

public interface ManualRepository extends JpaRepository<Manual, Integer>{
   Optional<Manual> findById(Long manualId);
}
package com.agencyplatformclonecoding.repository;

import com.agencyplatformclonecoding.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

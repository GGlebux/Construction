package com.construction.repository;

import com.construction.domain.Unit;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Unit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {
    @Override
    @EntityGraph(attributePaths = { "photos" })
    List<Unit> findAll();
}

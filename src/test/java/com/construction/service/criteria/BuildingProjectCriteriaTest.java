package com.construction.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class BuildingProjectCriteriaTest {

    @Test
    void newBuildingProjectCriteriaHasAllFiltersNullTest() {
        var buildingProjectCriteria = new BuildingProjectCriteria();
        assertThat(buildingProjectCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void buildingProjectCriteriaFluentMethodsCreatesFiltersTest() {
        var buildingProjectCriteria = new BuildingProjectCriteria();

        setAllFilters(buildingProjectCriteria);

        assertThat(buildingProjectCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void buildingProjectCriteriaCopyCreatesNullFilterTest() {
        var buildingProjectCriteria = new BuildingProjectCriteria();
        var copy = buildingProjectCriteria.copy();

        assertThat(buildingProjectCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(buildingProjectCriteria)
        );
    }

    @Test
    void buildingProjectCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var buildingProjectCriteria = new BuildingProjectCriteria();
        setAllFilters(buildingProjectCriteria);

        var copy = buildingProjectCriteria.copy();

        assertThat(buildingProjectCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(buildingProjectCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var buildingProjectCriteria = new BuildingProjectCriteria();

        assertThat(buildingProjectCriteria).hasToString("BuildingProjectCriteria{}");
    }

    private static void setAllFilters(BuildingProjectCriteria buildingProjectCriteria) {
        buildingProjectCriteria.id();
        buildingProjectCriteria.name();
        buildingProjectCriteria.type();
        buildingProjectCriteria.address();
        buildingProjectCriteria.description();
        buildingProjectCriteria.minPrice();
        buildingProjectCriteria.completionDate();
        buildingProjectCriteria.unitsId();
        buildingProjectCriteria.photosId();
        buildingProjectCriteria.distinct();
    }

    private static Condition<BuildingProjectCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getAddress()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getMinPrice()) &&
                condition.apply(criteria.getCompletionDate()) &&
                condition.apply(criteria.getUnitsId()) &&
                condition.apply(criteria.getPhotosId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<BuildingProjectCriteria> copyFiltersAre(
        BuildingProjectCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getAddress(), copy.getAddress()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getMinPrice(), copy.getMinPrice()) &&
                condition.apply(criteria.getCompletionDate(), copy.getCompletionDate()) &&
                condition.apply(criteria.getUnitsId(), copy.getUnitsId()) &&
                condition.apply(criteria.getPhotosId(), copy.getPhotosId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

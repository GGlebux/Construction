package com.construction.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class UnitCriteriaTest {

    @Test
    void newUnitCriteriaHasAllFiltersNullTest() {
        var unitCriteria = new UnitCriteria();
        assertThat(unitCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void unitCriteriaFluentMethodsCreatesFiltersTest() {
        var unitCriteria = new UnitCriteria();

        setAllFilters(unitCriteria);

        assertThat(unitCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void unitCriteriaCopyCreatesNullFilterTest() {
        var unitCriteria = new UnitCriteria();
        var copy = unitCriteria.copy();

        assertThat(unitCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(unitCriteria)
        );
    }

    @Test
    void unitCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var unitCriteria = new UnitCriteria();
        setAllFilters(unitCriteria);

        var copy = unitCriteria.copy();

        assertThat(unitCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(unitCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var unitCriteria = new UnitCriteria();

        assertThat(unitCriteria).hasToString("UnitCriteria{}");
    }

    private static void setAllFilters(UnitCriteria unitCriteria) {
        unitCriteria.id();
        unitCriteria.location();
        unitCriteria.price();
        unitCriteria.description();
        unitCriteria.area();
        unitCriteria.floor();
        unitCriteria.type();
        unitCriteria.status();
        unitCriteria.completionDate();
        unitCriteria.photosId();
        unitCriteria.bookingsId();
        unitCriteria.projectId();
        unitCriteria.distinct();
    }

    private static Condition<UnitCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getLocation()) &&
                condition.apply(criteria.getPrice()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getArea()) &&
                condition.apply(criteria.getFloor()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getStatus()) &&
                condition.apply(criteria.getCompletionDate()) &&
                condition.apply(criteria.getPhotosId()) &&
                condition.apply(criteria.getBookingsId()) &&
                condition.apply(criteria.getProjectId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<UnitCriteria> copyFiltersAre(UnitCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getLocation(), copy.getLocation()) &&
                condition.apply(criteria.getPrice(), copy.getPrice()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getArea(), copy.getArea()) &&
                condition.apply(criteria.getFloor(), copy.getFloor()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getStatus(), copy.getStatus()) &&
                condition.apply(criteria.getCompletionDate(), copy.getCompletionDate()) &&
                condition.apply(criteria.getPhotosId(), copy.getPhotosId()) &&
                condition.apply(criteria.getBookingsId(), copy.getBookingsId()) &&
                condition.apply(criteria.getProjectId(), copy.getProjectId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}

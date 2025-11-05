package com.construction.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.construction.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuildingProjectDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingProjectDTO.class);
        BuildingProjectDTO buildingProjectDTO1 = new BuildingProjectDTO();
        buildingProjectDTO1.setId(1L);
        BuildingProjectDTO buildingProjectDTO2 = new BuildingProjectDTO();
        assertThat(buildingProjectDTO1).isNotEqualTo(buildingProjectDTO2);
        buildingProjectDTO2.setId(buildingProjectDTO1.getId());
        assertThat(buildingProjectDTO1).isEqualTo(buildingProjectDTO2);
        buildingProjectDTO2.setId(2L);
        assertThat(buildingProjectDTO1).isNotEqualTo(buildingProjectDTO2);
        buildingProjectDTO1.setId(null);
        assertThat(buildingProjectDTO1).isNotEqualTo(buildingProjectDTO2);
    }
}

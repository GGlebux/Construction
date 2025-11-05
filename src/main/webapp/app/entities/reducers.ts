import buildingProject from 'app/entities/building-project/building-project.reducer';
import unit from 'app/entities/unit/unit.reducer';
import photo from 'app/entities/photo/photo.reducer';
import client from 'app/entities/client/client.reducer';
import booking from 'app/entities/booking/booking.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  buildingProject,
  unit,
  photo,
  client,
  booking,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;

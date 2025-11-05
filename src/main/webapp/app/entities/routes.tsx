import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuildingProject from './building-project';
import Unit from './unit';
import Photo from './photo';
import Client from './client';
import Booking from './booking';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="building-project/*" element={<BuildingProject />} />
        <Route path="unit/*" element={<Unit />} />
        <Route path="photo/*" element={<Photo />} />
        <Route path="client/*" element={<Client />} />
        <Route path="booking/*" element={<Booking />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};

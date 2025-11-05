import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuildingProject from './building-project';
import BuildingProjectDetail from './building-project-detail';
import BuildingProjectUpdate from './building-project-update';
import BuildingProjectDeleteDialog from './building-project-delete-dialog';

const BuildingProjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BuildingProject />} />
    <Route path="new" element={<BuildingProjectUpdate />} />
    <Route path=":id">
      <Route index element={<BuildingProjectDetail />} />
      <Route path="edit" element={<BuildingProjectUpdate />} />
      <Route path="delete" element={<BuildingProjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuildingProjectRoutes;

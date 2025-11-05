import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBuildingProject } from 'app/shared/model/building-project.model';
import { BuildingType } from 'app/shared/model/enumerations/building-type.model';
import { getEntity, updateEntity, createEntity, reset } from './building-project.reducer';

export const BuildingProjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buildingProjectEntity = useAppSelector(state => state.buildingProject.entity);
  const loading = useAppSelector(state => state.buildingProject.loading);
  const updating = useAppSelector(state => state.buildingProject.updating);
  const updateSuccess = useAppSelector(state => state.buildingProject.updateSuccess);
  const buildingTypeValues = Object.keys(BuildingType);

  const handleClose = () => {
    navigate('/building-project' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.minPrice !== undefined && typeof values.minPrice !== 'number') {
      values.minPrice = Number(values.minPrice);
    }
    values.completionDate = convertDateTimeToServer(values.completionDate);

    const entity = {
      ...buildingProjectEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          completionDate: displayDefaultDateTime(),
        }
      : {
          type: 'MULTI_APARTMENT',
          ...buildingProjectEntity,
          completionDate: convertDateTimeFromServer(buildingProjectEntity.completionDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="strOyKakApp.buildingProject.home.createOrEditLabel" data-cy="BuildingProjectCreateUpdateHeading">
            <Translate contentKey="strOyKakApp.buildingProject.home.createOrEditLabel">Create or edit a BuildingProject</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="building-project-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('strOyKakApp.buildingProject.name')}
                id="building-project-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('strOyKakApp.buildingProject.type')}
                id="building-project-type"
                name="type"
                data-cy="type"
                type="select"
              >
                {buildingTypeValues.map(buildingType => (
                  <option value={buildingType} key={buildingType}>
                    {translate('strOyKakApp.BuildingType.' + buildingType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('strOyKakApp.buildingProject.address')}
                id="building-project-address"
                name="address"
                data-cy="address"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('strOyKakApp.buildingProject.description')}
                id="building-project-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('strOyKakApp.buildingProject.minPrice')}
                id="building-project-minPrice"
                name="minPrice"
                data-cy="minPrice"
                type="text"
              />
              <ValidatedField
                label={translate('strOyKakApp.buildingProject.completionDate')}
                id="building-project-completionDate"
                name="completionDate"
                data-cy="completionDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/building-project" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BuildingProjectUpdate;

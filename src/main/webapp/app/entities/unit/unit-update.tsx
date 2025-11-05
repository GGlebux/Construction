import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBuildingProject } from 'app/shared/model/building-project.model';
import { getEntities as getBuildingProjects } from 'app/entities/building-project/building-project.reducer';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitType } from 'app/shared/model/enumerations/unit-type.model';
import { UnitStatus } from 'app/shared/model/enumerations/unit-status.model';
import { getEntity, updateEntity, createEntity, reset } from './unit.reducer';

export const UnitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const buildingProjects = useAppSelector(state => state.buildingProject.entities);
  const unitEntity = useAppSelector(state => state.unit.entity);
  const loading = useAppSelector(state => state.unit.loading);
  const updating = useAppSelector(state => state.unit.updating);
  const updateSuccess = useAppSelector(state => state.unit.updateSuccess);
  const unitTypeValues = Object.keys(UnitType);
  const unitStatusValues = Object.keys(UnitStatus);

  const handleClose = () => {
    navigate('/unit' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getBuildingProjects({}));
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
    if (values.price !== undefined && typeof values.price !== 'number') {
      values.price = Number(values.price);
    }
    if (values.area !== undefined && typeof values.area !== 'number') {
      values.area = Number(values.area);
    }
    if (values.floor !== undefined && typeof values.floor !== 'number') {
      values.floor = Number(values.floor);
    }
    values.completionDate = convertDateTimeToServer(values.completionDate);

    const entity = {
      ...unitEntity,
      ...values,
      project: buildingProjects.find(it => it.id.toString() === values.project?.toString()),
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
          type: 'STUDIO',
          status: 'AVAILABLE',
          ...unitEntity,
          completionDate: convertDateTimeFromServer(unitEntity.completionDate),
          project: unitEntity?.project?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="strOyKakApp.unit.home.createOrEditLabel" data-cy="UnitCreateUpdateHeading">
            <Translate contentKey="strOyKakApp.unit.home.createOrEditLabel">Create or edit a Unit</Translate>
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
                  id="unit-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('strOyKakApp.unit.location')}
                id="unit-location"
                name="location"
                data-cy="location"
                type="text"
              />
              <ValidatedField
                label={translate('strOyKakApp.unit.price')}
                id="unit-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('strOyKakApp.unit.description')}
                id="unit-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('strOyKakApp.unit.area')}
                id="unit-area"
                name="area"
                data-cy="area"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('strOyKakApp.unit.floor')}
                id="unit-floor"
                name="floor"
                data-cy="floor"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField label={translate('strOyKakApp.unit.type')} id="unit-type" name="type" data-cy="type" type="select">
                {unitTypeValues.map(unitType => (
                  <option value={unitType} key={unitType}>
                    {translate('strOyKakApp.UnitType.' + unitType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label={translate('strOyKakApp.unit.status')} id="unit-status" name="status" data-cy="status" type="select">
                {unitStatusValues.map(unitStatus => (
                  <option value={unitStatus} key={unitStatus}>
                    {translate('strOyKakApp.UnitStatus.' + unitStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('strOyKakApp.unit.completionDate')}
                id="unit-completionDate"
                name="completionDate"
                data-cy="completionDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="unit-project"
                name="project"
                data-cy="project"
                label={translate('strOyKakApp.unit.project')}
                type="select"
                required
              >
                <option value="" key="0" />
                {buildingProjects
                  ? buildingProjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/unit" replace color="info">
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

export default UnitUpdate;

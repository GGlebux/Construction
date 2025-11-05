import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './building-project.reducer';

export const BuildingProjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buildingProjectEntity = useAppSelector(state => state.buildingProject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buildingProjectDetailsHeading">
          <Translate contentKey="strOyKakApp.buildingProject.detail.title">BuildingProject</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{buildingProjectEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="strOyKakApp.buildingProject.name">Name</Translate>
            </span>
          </dt>
          <dd>{buildingProjectEntity.name}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="strOyKakApp.buildingProject.type">Type</Translate>
            </span>
          </dt>
          <dd>{buildingProjectEntity.type}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="strOyKakApp.buildingProject.address">Address</Translate>
            </span>
          </dt>
          <dd>{buildingProjectEntity.address}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="strOyKakApp.buildingProject.description">Description</Translate>
            </span>
          </dt>
          <dd>{buildingProjectEntity.description}</dd>
          <dt>
            <span id="minPrice">
              <Translate contentKey="strOyKakApp.buildingProject.minPrice">Min Price</Translate>
            </span>
          </dt>
          <dd>{buildingProjectEntity.minPrice}</dd>
          <dt>
            <span id="completionDate">
              <Translate contentKey="strOyKakApp.buildingProject.completionDate">Completion Date</Translate>
            </span>
          </dt>
          <dd>
            {buildingProjectEntity.completionDate ? (
              <TextFormat value={buildingProjectEntity.completionDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/building-project" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/building-project/${buildingProjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuildingProjectDetail;

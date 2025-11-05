import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './unit.reducer';

export const UnitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const unitEntity = useAppSelector(state => state.unit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="unitDetailsHeading">
          <Translate contentKey="strOyKakApp.unit.detail.title">Unit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{unitEntity.id}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="strOyKakApp.unit.location">Location</Translate>
            </span>
          </dt>
          <dd>{unitEntity.location}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="strOyKakApp.unit.price">Price</Translate>
            </span>
          </dt>
          <dd>{unitEntity.price}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="strOyKakApp.unit.description">Description</Translate>
            </span>
          </dt>
          <dd>{unitEntity.description}</dd>
          <dt>
            <span id="area">
              <Translate contentKey="strOyKakApp.unit.area">Area</Translate>
            </span>
          </dt>
          <dd>{unitEntity.area}</dd>
          <dt>
            <span id="floor">
              <Translate contentKey="strOyKakApp.unit.floor">Floor</Translate>
            </span>
          </dt>
          <dd>{unitEntity.floor}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="strOyKakApp.unit.type">Type</Translate>
            </span>
          </dt>
          <dd>{unitEntity.type}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="strOyKakApp.unit.status">Status</Translate>
            </span>
          </dt>
          <dd>{unitEntity.status}</dd>
          <dt>
            <span id="completionDate">
              <Translate contentKey="strOyKakApp.unit.completionDate">Completion Date</Translate>
            </span>
          </dt>
          <dd>
            {unitEntity.completionDate ? <TextFormat value={unitEntity.completionDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="strOyKakApp.unit.project">Project</Translate>
          </dt>
          <dd>{unitEntity.project ? unitEntity.project.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/unit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/unit/${unitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UnitDetail;

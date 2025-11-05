import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './booking.reducer';

export const BookingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bookingEntity = useAppSelector(state => state.booking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookingDetailsHeading">
          <Translate contentKey="strOyKakApp.booking.detail.title">Booking</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.id}</dd>
          <dt>
            <span id="bookingDate">
              <Translate contentKey="strOyKakApp.booking.bookingDate">Booking Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingEntity.bookingDate ? <TextFormat value={bookingEntity.bookingDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="status">
              <Translate contentKey="strOyKakApp.booking.status">Status</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.status}</dd>
          <dt>
            <span id="expirationDate">
              <Translate contentKey="strOyKakApp.booking.expirationDate">Expiration Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingEntity.expirationDate ? <TextFormat value={bookingEntity.expirationDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="note">
              <Translate contentKey="strOyKakApp.booking.note">Note</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.note}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="strOyKakApp.booking.price">Price</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.price}</dd>
          <dt>
            <Translate contentKey="strOyKakApp.booking.client">Client</Translate>
          </dt>
          <dd>{bookingEntity.client ? bookingEntity.client.id : ''}</dd>
          <dt>
            <Translate contentKey="strOyKakApp.booking.unit">Unit</Translate>
          </dt>
          <dd>{bookingEntity.unit ? bookingEntity.unit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/booking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/booking/${bookingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookingDetail;

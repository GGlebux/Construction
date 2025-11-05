import dayjs from 'dayjs';
import { IClient } from 'app/shared/model/client.model';
import { IUnit } from 'app/shared/model/unit.model';
import { BookingStatus } from 'app/shared/model/enumerations/booking-status.model';

export interface IBooking {
  id?: number;
  bookingDate?: dayjs.Dayjs;
  status?: keyof typeof BookingStatus;
  expirationDate?: dayjs.Dayjs | null;
  note?: string | null;
  price?: number;
  client?: IClient;
  unit?: IUnit;
}

export const defaultValue: Readonly<IBooking> = {};

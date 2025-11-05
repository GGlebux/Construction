import { IUser } from 'app/shared/model/user.model';

export interface IClient {
  id?: number;
  fullName?: string;
  email?: string;
  user?: IUser | null;
}

export const defaultValue: Readonly<IClient> = {};

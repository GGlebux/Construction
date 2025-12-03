import { IUser } from 'app/shared/model/user.model';

export interface IClient {
  id?: number;
  user?: IUser | null;
}

export const defaultValue: Readonly<IClient> = {};

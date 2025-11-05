import dayjs from 'dayjs';
import { BuildingType } from 'app/shared/model/enumerations/building-type.model';

export interface IBuildingProject {
  id?: number;
  name?: string;
  type?: keyof typeof BuildingType;
  address?: string;
  description?: string | null;
  minPrice?: number | null;
  completionDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IBuildingProject> = {};

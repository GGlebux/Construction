import dayjs from 'dayjs';
import { IBuildingProject } from 'app/shared/model/building-project.model';
import { UnitType } from 'app/shared/model/enumerations/unit-type.model';
import { UnitStatus } from 'app/shared/model/enumerations/unit-status.model';

export interface IUnit {
  id?: number;
  location?: string | null;
  price?: number;
  description?: string | null;
  area?: number;
  floor?: number;
  type?: keyof typeof UnitType;
  status?: keyof typeof UnitStatus;
  completionDate?: dayjs.Dayjs | null;
  project?: IBuildingProject;
}

export const defaultValue: Readonly<IUnit> = {};

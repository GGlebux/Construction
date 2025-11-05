import { IBuildingProject } from 'app/shared/model/building-project.model';
import { IUnit } from 'app/shared/model/unit.model';

export interface IPhoto {
  id?: number;
  url?: string;
  project?: IBuildingProject | null;
  unit?: IUnit | null;
}

export const defaultValue: Readonly<IPhoto> = {};

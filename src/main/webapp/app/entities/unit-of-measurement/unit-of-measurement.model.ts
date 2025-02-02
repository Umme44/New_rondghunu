import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';

export interface IUnitOfMeasurement {
  id: number;
  name?: string | null;
  remarks?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;

  // createdBy?: Pick<IUser, 'id' | 'login'> | null;
  // updatedBy?: Pick<IUser, 'id' | 'login'> | null;

  createdByLogin?: string;
  createdById?: number;
  updatedByLogin?: string;
  updatedById?: number;
}

export type NewUnitOfMeasurement = Omit<IUnitOfMeasurement, 'id'> & { id: null };

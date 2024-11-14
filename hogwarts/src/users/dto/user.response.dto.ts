import { User } from '../entities/user.entity';

export class UserResponseDto {
  constructor(entity: User) {
    this.id = entity._id;
    this.name = entity.name;
    this.role = entity.role;
    if (entity.isHidden != null) this.isHidden = entity.isHidden;
    if (entity.isArrested != null) this.isArrested = entity.isArrested;
    if (entity.isCatch != null) this.isCatch = entity.isCatch;
  }
  id: string;

  name: string;

  role: string;

  isHidden?: boolean;

  isArrested?: boolean;

  isCatch?: boolean;
}

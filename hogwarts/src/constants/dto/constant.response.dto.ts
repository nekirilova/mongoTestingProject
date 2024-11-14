import { Constant } from '../entities/constant.entity';

export class ConstantResponseDto {
  constructor(entity: Constant) {
    this.id = entity._id;
    this.name = entity.name;
    this.value = entity.value;
  }
  id: string;
  name: string;
  value: string;
}

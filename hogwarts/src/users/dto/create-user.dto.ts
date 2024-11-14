export class CreateUserDto {
  name: string;

  role: string;

  isHidden?: boolean;

  isArrested?: boolean;

  isCatch?: boolean;
}

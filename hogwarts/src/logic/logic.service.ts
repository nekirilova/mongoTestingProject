import { Injectable } from '@nestjs/common';
import { ConstantsService } from 'src/constants/constants.service';
import {
  ConstantName,
  owlAddressMinistry,
  owlAddressOrder,
  VeritaserumType,
} from 'src/constants/enum/constants.enum';
import { UserResponseDto } from 'src/users/dto/user.response.dto';
import { UsersService } from 'src/users/users.service';

@Injectable()
export class LogicService {
  constructor(
    private readonly userService: UsersService,
    private readonly constantService: ConstantsService,
  ) {}

  async hide() {
    const isLightOn = await this.constantService.getConstantByName(ConstantName.isLightOn);

    if (isLightOn === 'false') await this.userService.hideStudents(true);
    if (isLightOn === 'true') await this.userService.hideStudents(false);

    return this.userService.getStudents();
  }

  async catch() {
    return this.userService.catchStudents();
  }

  async getStudentList() {
    const veritaserum = await this.constantService.getConstantByName(ConstantName.veritaserum);

    return this.userService.getStudentList(veritaserum as VeritaserumType);
  }

  async sendOwl() {
    const owlAddress = await this.constantService.getConstantByName(ConstantName.owlAddress);

    const owlTimeout = await this.constantService.getConstantByName(ConstantName.owlTimeout);

    return this.getOwlResponse(owlAddress, Number(owlTimeout));
  }

  async cast() {
    const owlAddress = await this.constantService.getConstantByName(ConstantName.owlAddress);

    const owlTimeout = await this.constantService.getConstantByName(ConstantName.owlTimeout);

    await this.getOwlResponse(owlAddress, Number(owlTimeout));

    let winner: UserResponseDto;
    let loser: UserResponseDto;
    if (Number(owlTimeout) >= 3) {
      loser = await this.userService.arrestUserByName('Долорес Амбридж');
      winner = await this.userService.setUserRoleDirectorUserByName('Альбус Дамблдор');
    } else {
      loser = await this.userService.arrestUserByName('Альбус Дамблдор');
      winner = await this.userService.setUserRoleDirectorUserByName('Долорес Амбридж');
    }

    return [winner, loser];
  }

  private async getOwlResponse(owlAddress: string, owlTimeout: number) {
    const sleep = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

    await sleep(owlTimeout * 1000);

    if (owlAddress === owlAddressMinistry)
      return this.constantService.getConstantByName(ConstantName.owlAddressMinistryResponse);
    if (owlAddress === owlAddressOrder)
      return this.constantService.getConstantByName(ConstantName.owlPhoenixOrderResponse);
  }
}

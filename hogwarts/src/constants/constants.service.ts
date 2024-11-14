import { Injectable } from '@nestjs/common';
import { CreateConstantDto } from './dto/create-constant.dto';
import { UpdateConstantDto } from './dto/update-constant.dto';
import { Constant } from './entities/constant.entity';
import { MongoRepository } from 'typeorm';
import { InjectRepository } from '@nestjs/typeorm';
import { ConstantName, owlAddressMinistry, VeritaserumType } from './enum/constants.enum';
import { ConstantResponseDto } from './dto/constant.response.dto';
import { ObjectId } from 'mongodb';

@Injectable()
export class ConstantsService {
  constructor(
    @InjectRepository(Constant)
    private readonly constantRepository: MongoRepository<Constant>,
  ) {}

  async create(createConstantDto: CreateConstantDto) {
    const constant = new Constant();
    constant.name = createConstantDto.name;
    constant.value = createConstantDto.value;

    await this.constantRepository.save(constant);

    return constant;
  }

  async findAll() {
    const constants = await this.constantRepository.find();
    return constants?.map((x) => new ConstantResponseDto(x));
  }

  async findOne(id: string) {
    const constant = await this.constantRepository.findOne({ where: { _id: new ObjectId(id) } });
    if (!constant) return null;
    return new ConstantResponseDto(constant);
  }

  async update(id: string, updateConstantDto: UpdateConstantDto) {
    const constant = await this.constantRepository.findOne({ where: { _id: new ObjectId(id) } });
    if (!constant) return null;
    constant.name = updateConstantDto.name;
    constant.value = updateConstantDto.value;

    await this.constantRepository.save(constant);

    return new ConstantResponseDto(constant);
  }

  async remove(id: string) {
    const constant = await this.constantRepository.findOne({ where: { _id: new ObjectId(id) } });
    if (!constant) return false;
    return !!(await this.constantRepository.delete(constant));
  }

  async populate() {
    const allConstants = await this.constantRepository.find();
    await this.constantRepository.remove(allConstants);

    const data: CreateConstantDto[] = [
      { name: ConstantName.isLightOn, value: 'true' },
      { name: ConstantName.veritaserum, value: VeritaserumType.everything },
      { name: ConstantName.owlAddress, value: owlAddressMinistry },
      { name: ConstantName.owlTimeout, value: '2' },
      { name: ConstantName.owlAddressMinistryResponse, value: 'Bad response' },
      { name: ConstantName.owlPhoenixOrderResponse, value: 'Good response' },
    ];

    for (const item of data) {
      await this.create(item);
    }
    return this.findAll();
  }

  async getConstantByName(constantName: ConstantName) {
    const constant = await this.constantRepository.findOne({ where: { name: constantName } });
    return constant?.value;
  }
}

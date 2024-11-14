import { Controller, Get, Post, Body, Patch, Param, Delete } from '@nestjs/common';
import { ConstantsService } from './constants.service';
import { CreateConstantDto } from './dto/create-constant.dto';
import { UpdateConstantDto } from './dto/update-constant.dto';

@Controller('constants')
export class ConstantsController {
  constructor(private readonly constantsService: ConstantsService) {}

  @Post()
  create(@Body() createConstantDto: CreateConstantDto) {
    return this.constantsService.create(createConstantDto);
  }

  @Get()
  async findAll() {
    return this.constantsService.findAll();
  }

  @Post('populate')
  async populate() {
    return this.constantsService.populate();
  }

  @Get(':id')
  async findOne(@Param('id') id: string) {
    return this.constantsService.findOne(id);
  }

  @Patch(':id')
  async update(@Param('id') id: string, @Body() updateConstantDto: UpdateConstantDto) {
    return this.constantsService.update(id, updateConstantDto);
  }

  @Delete(':id')
  async remove(@Param('id') id: string) {
    return this.constantsService.remove(id);
  }
}

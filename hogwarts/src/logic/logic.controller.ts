import { Controller, Get, Post, Put } from '@nestjs/common';
import { LogicService } from './logic.service';

@Controller('logic')
export class LogicController {
  constructor(private readonly logicService: LogicService) {}

  @Put('student/hide')
  async hide() {
    return this.logicService.hide();
  }

  @Put('student/catch')
  async catch() {
    return this.logicService.catch();
  }

  @Get('student/list')
  async getStudentList() {
    return this.logicService.getStudentList();
  }

  @Post('sendOwl')
  async sendOwl() {
    return this.logicService.sendOwl();
  }

  @Post('cast')
  async cast() {
    return this.logicService.cast();
  }
}

import { Module } from '@nestjs/common';
import { ConstantsService } from './constants.service';
import { ConstantsController } from './constants.controller';
import { Constant } from './entities/constant.entity';
import { TypeOrmModule } from '@nestjs/typeorm';

@Module({
  imports: [TypeOrmModule.forFeature([Constant])],
  controllers: [ConstantsController],
  providers: [ConstantsService],
  exports: [ConstantsService],
})
export class ConstantsModule {}

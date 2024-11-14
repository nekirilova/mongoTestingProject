import { Module } from '@nestjs/common';
import { LogicController } from './logic.controller';
import { LogicService } from './logic.service';
import { UsersModule } from 'src/users/users.module';
import { ConstantsModule } from 'src/constants/constants.module';

@Module({
  imports: [UsersModule, ConstantsModule],
  controllers: [LogicController],
  providers: [LogicService],
})
export class LogicModule {}

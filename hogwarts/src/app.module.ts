import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { resolve } from 'path';
import { UsersModule } from './users/users.module';
import { User } from './users/entities/user.entity';
import { ConstantsModule } from './constants/constants.module';
import { Constant } from './constants/entities/constant.entity';
import { LogicModule } from './logic/logic.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      envFilePath: [resolve(__dirname, '../.development.env'), resolve(__dirname, '../.env')],
      isGlobal: true,
    }),
    TypeOrmModule.forFeature([User, Constant]),
    TypeOrmModule.forRootAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (configService: ConfigService) => ({
        type: 'mongodb',
        host: configService.get('DB_HOST'),
        port: parseInt(configService.get('DB_PORT')),
        username: configService.get('DB_USERNAME'),
        password: configService.get('DB_PASSWORD'),
        database: configService.get('DB_DATABASE'),
        autoLoadEntities: true,
        // Only enable this option if your application is in development,
        // otherwise use TypeORM migrations to sync entity schemas:
        // https://typeorm.io/#/migrations
        synchronize: true,
      }),
    }),
    UsersModule,
    ConstantsModule,
    LogicModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}

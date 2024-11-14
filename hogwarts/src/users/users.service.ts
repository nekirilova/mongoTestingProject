import { Injectable } from '@nestjs/common';
import { CreateUserDto } from './dto/create-user.dto';
import { UpdateUserDto } from './dto/update-user.dto';
import { User } from './entities/user.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { MongoRepository } from 'typeorm';
import { userRole } from './enum/users.enum';
import { UserResponseDto } from './dto/user.response.dto';
import { VeritaserumType } from 'src/constants/enum/constants.enum';
import { ObjectId } from 'mongodb';

@Injectable()
export class UsersService {
  constructor(
    @InjectRepository(User)
    private readonly userRepository: MongoRepository<User>,
  ) {}

  async create(createUserDto: CreateUserDto) {
    const user = new User();
    user.name = createUserDto.name;
    user.role = createUserDto.role;
    user.isHidden = createUserDto.isHidden;
    user.isArrested = createUserDto.isArrested;
    user.isCatch = createUserDto.isCatch;

    await this.userRepository.save(user);

    return new UserResponseDto(user);
  }

  async findAll() {
    const users = await this.userRepository.find();

    return users.map((x) => new UserResponseDto(x));
  }

  async findOne(id: string) {
    const user = await this.userRepository.findOne({ where: { _id: new ObjectId(id) } });
    if (!user) return null;
    return new UserResponseDto(user);
  }

  async update(id: string, updateUserDto: UpdateUserDto) {
    const user = await this.userRepository.findOne({ where: { _id: new ObjectId(id) } });
    if (!user) return null;
    user.name = updateUserDto.name;
    user.role = updateUserDto.role;
    user.isHidden = updateUserDto.isHidden;
    user.isArrested = updateUserDto.isArrested;
    user.isCatch = updateUserDto.isCatch;

    await this.userRepository.save(user);

    return new UserResponseDto(user);
  }

  async remove(id: string) {
    const user = await this.userRepository.findOne({ where: { _id: new ObjectId(id) } });
    if (!user) return false;
    return !!(await this.userRepository.delete(user));
  }

  async populate() {
    const allUsers = await this.userRepository.find();
    await this.userRepository.remove(allUsers);
    const data: CreateUserDto[] = [
      {
        name: 'Долорес Амбридж',
        role: userRole.director,
      },
      {
        name: 'Альбус Дамблдор',
        role: userRole.unemployed,
      },
      {
        name: 'Гарри Поттер',
        role: userRole.student,
        isHidden: false,
      },
      {
        name: 'Рон Уизли',
        role: userRole.student,
        isHidden: false,
      },
      {
        name: 'Гермиона Грейнджер',
        role: userRole.student,
        isHidden: false,
      },
      {
        name: 'Невилл Долгопупс',
        role: userRole.student,
        isHidden: false,
      },
    ];

    for (const item of data) {
      await this.create(item);
    }
    return await this.findAll();
  }

  async getStudents() {
    const students = await this.userRepository.find({ where: { role: userRole.student } });
    return students.map((x) => new UserResponseDto(x));
  }

  async hideStudents(hide: boolean) {
    const students = await this.userRepository.find({ where: { role: userRole.student } });

    students.forEach((x) => (x.isHidden = hide));

    await this.userRepository.save(students);

    return students.map((x) => new UserResponseDto(x));
  }

  async catchStudents() {
    let students: User[] = [];

    students = await this.userRepository.find({
      where: { role: userRole.student, isHidden: false },
    });

    if (!students?.length) {
      const allStudents = await this.userRepository.find({
        where: { role: userRole.student },
      });
      const rndStudent = Math.floor(Math.random() * allStudents.length);
      students.push(allStudents[rndStudent]);
    }

    students.forEach((x) => this.setStudentCatch(x));

    await this.userRepository.save(students);

    return students.map((x) => new UserResponseDto(x));
  }

  private setStudentCatch(student: User) {
    student.isCatch = true;
    student.isHidden = false;
    return student;
  }

  async getStudentList(veritaserum: VeritaserumType) {
    const students = await this.userRepository.find({ where: { role: userRole.student } });

    switch (veritaserum) {
      case VeritaserumType.everything:
        return students?.map((x) => new UserResponseDto(x));
      case VeritaserumType.half:
        const halfLength = Math.ceil(students.length / 2);
        return students.slice(0, halfLength)?.map((x) => new UserResponseDto(x));
      case VeritaserumType.nothing:
        return [];
    }

    return [];
  }

  async arrestUserByName(userName: string) {
    const user = await this.userRepository.findOne({ where: { name: userName } });

    if (!user) return null;
    user.isArrested = true;
    user.role = userRole.unemployed;

    await this.userRepository.save(user);

    return new UserResponseDto(user);
  }

  async setUserRoleDirectorUserByName(userName: string) {
    const user = await this.userRepository.findOne({ where: { name: userName } });

    if (!user) return null;

    user.isArrested = false;
    user.role = userRole.director;

    await this.userRepository.save(user);

    return new UserResponseDto(user);
  }
}

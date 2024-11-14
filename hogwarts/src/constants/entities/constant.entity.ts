import { Entity, ObjectIdColumn, Column } from 'typeorm';

@Entity()
export class Constant {
  @ObjectIdColumn()
  _id: string;

  @Column()
  name: string;

  @Column()
  value: string;
}

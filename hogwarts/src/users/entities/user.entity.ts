import { Entity, ObjectIdColumn, Column } from 'typeorm';

@Entity()
export class User {
  @ObjectIdColumn()
  _id: string;

  @Column()
  name: string;

  @Column()
  role: string;

  @Column({ default: false })
  isHidden: boolean;

  @Column({ default: false })
  isArrested: boolean;

  @Column({ default: false })
  isCatch: boolean;
}

db = db.getSiblingDB('hogwarts');
db.createUser(
  {
    user: 'admin',
    pwd: 'phKx3UnU6svK8U9C',
    roles: [{ role: 'readWrite', db: 'tech_support' }],
  },
);
db.createCollection('users');
db.createCollection('addresses');
db.addresses.createIndex({ location: '2dsphere' });

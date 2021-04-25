db.createCollection('addresses');
db.adresses.createIndex({ location: '2dsphere' });

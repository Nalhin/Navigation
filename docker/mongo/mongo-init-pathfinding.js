db.createCollection('streetConnections');
db.createCollection('streetNodes');
db.streetNodes.createIndex({ location: '2dsphere' });

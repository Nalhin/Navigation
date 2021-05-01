db.createCollection('streetConnections');
db.streetConnections.createIndex({ fromId: 1 });
db.streetConnections.createIndex({ ToId: 1 });
db.createCollection('streetNodes');
db.streetNodes.createIndex({ location: '2dsphere' });

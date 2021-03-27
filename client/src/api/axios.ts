import defaultAxios from 'axios';

const instance = defaultAxios.create({ baseURL: '/api' });

export { instance as axios };

import defaultAxios from 'axios';

const instance = defaultAxios.create({ baseURL: '/com.navigation.reversegeocodingapi.api' });

export { instance as axios };

import '@testing-library/jest-dom';
import 'jest-extended';
import { setLogger } from 'react-query';

setLogger({
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  log: () => {},
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  warn: () => {},
  // eslint-disable-next-line @typescript-eslint/no-empty-function
  error: () => {},
});
jest.setTimeout(30000);

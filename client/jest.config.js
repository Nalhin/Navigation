module.exports = {
  transform: {
    '^.+\\.[t|j]sx?$': 'babel-jest',
  },
  reporters: ['default'],
  collectCoverageFrom: [
    'src/**/*.{ts,tsx}',
    '!src/report-web-vitals.ts',
    '!src/index.tsx',
  ],
  setupFilesAfterEnv: ['./test/setup.ts'],
  moduleNameMapper: {
    '\\.(jpg|ico|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2|mp4|webm|wav|mp3|m4a|aac|oga)$':
      '<rootDir>/test/mocks/file-mock.ts',
  },
};

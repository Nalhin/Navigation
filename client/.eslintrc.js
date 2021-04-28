module.exports = {
  env: {
    browser: true,
    commonjs: true,
    es2020: true,
    jest: true,
    serviceworker: true,
  },
  extends: [
    'plugin:@typescript-eslint/eslint-recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:react/recommended',
    'plugin:jest/recommended',
    'plugin:testing-library/react',
    'plugin:prettier/recommended',
  ],
  parser: '@typescript-eslint/parser',
  root: true,
  rules: {
    '@typescript-eslint/explicit-module-boundary-types': 0,
    '@typescript-eslint/no-extra-non-null-assertion': 0,
    'react/prop-types': 0,
  },
  settings: {
    react: {
      version: 'latest',
    },
  },
};

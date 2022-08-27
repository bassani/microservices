export const environment = {
  production: false,
  BASE_URL: 'https://api-dev.example.com.br/v1/api/example',
  KC: {
    URL: 'https://kckdev.example.com.br/auth',
    CLIENT: 'frontend',
    REALM: 'jornada-de-compras',
  },
  /** o sistema utiliza somente mocks se o forceHttp não for true
   * para mas detalhes acessar o api.service.ts
   */
  forceMocks: false,
  /** o sistema utiliza somente request http
   * tem precendencia sobre o forceMocks
   * para mas detalhes acessar o api.service.ts
   */
  forceHttp: false,
};

import { of, throwError } from 'rxjs';
import { delay } from 'rxjs/operators';

export const MOCK_BLOB_CSV = new Blob(['123;123'], { type: 'application/csv' });
export const MOCK_BLOB_IMG = new Blob(['123;123'], { type: 'application/png' });
export const MOCK_BLOB_XLS = new Blob(['123;123'], { type: 'application/xls' });
export const MOCK_BLOB_JSON = new Blob(['123;123'], {
  type: 'application/json',
});
export const SEARCH_ITEM_LIST = of(
  [...Array(10)].map((e, i) => ({ id: i + 1, name: `Teste ${i + 1}` }))
);
export const MOCK_SEARCH_PAGED = {
  content: [...Array(10)].map((e, i) => ({
    id: i + 1,
    name: `Teste ${i + 1}`,
  })),
  page: 0,
  size: 25,
  numberOfElements: 10,
};

export const PAGED_BASE = {
  page: 0,
  size: 25,
  numberOfElements: 0,
  content: [],
};

export const CD_MOCK = [
  { id: 900, name: 'CD EMBU - SP' },
  { id: 908, name: 'CD RIBEIRAO PRETO - SP' },
  { id: 903, name: 'CD RIO JANEIRO' },
  { id: 2376, name: 'CD BAHIA' },
  { id: 905, name: 'CD PARANA' },
  { id: 3212, name: 'CD RIO GRANDE DO SUL' },
  { id: 1445, name: 'CD MINAS GERAIS' },
  { id: 1446, name: 'CD GOIAS' },
  { id: 2023, name: 'CD PERNAMBUCO' },
  { id: 2847, name: 'CD CEARA' },
  { id: 2605, name: 'CD GUARULHOS - SP' },
];
export const MANUFACTURER_MOCK = [
  { id: 1853, name: 'JOHNSON - OTC - SP' },
  { id: 5828, name: 'SANTA CRUZ - RP' },
  { id: 101745, name: 'JOHNSON NEUTROGENA SUNFRESH' },
  { id: 101780, name: 'ABBOTT NUTRICIONAL' },
  { id: 103159, name: 'ONTEX' },
];

export const PAYMENT_TERM_MOCK = [
  { id: 1, conditionPaymentDescription: '30 Dias', daysQuantityPayment: 30 },
  { id: 2, conditionPaymentDescription: '60 Dias', daysQuantityPayment: 60 },
  { id: 3, conditionPaymentDescription: '90 Dias', daysQuantityPayment: 90 },
];
export const SALES_CALCULATION_MOCK = [
    {
      name: 'Vendas dos últimos',
      id: 0,
      options: [
        {
          name: '7 Dias',
          parent: 0,
          value: 1,
        },
        {
          name: '15 Dias',
          parent: 0,
          value: 2,
        },
        {
          name: '30 Dias',
          parent: 0,
          value: 3,
        },
        {
          name: '60 Dias',
          parent: 0,
          value: 4,
        },
        {
          name: '90 Dias',
          parent: 0,
          value: 5,
        },
      ],
    },
    {
      id: 1,
      name: 'Forecast Semanal',
    },
    {
      id: 2,
      name: 'Forecast Mensal',
    },
  ];
export const TYPE_PRODUCT_MOCK = [
  { id: 1, name: 'Somente inativos' },
  { id: 2, name: 'Desconsiderar Promopacks' },
  { id: 3, name: 'Somente Promopacks' },
  { id: 4, name: 'Desconsiderar Cotados' },
  { id: 5, name: 'Somente Cotados' },
];
export const PRODUCTS_MOCK = [
  { id: 1, name: 'Dorflex com 50 comprimidos' },
  { id: 2, name: 'Buscopan Composto com 20 Comprimidos' },
  { id: 3, name: 'Targifor C com 16 comprimidos' },
  { id: 4, name: 'AAS Infantil 100mg com 30 Comprimidos' },
  { id: 5, name: 'Acertil 10MG com 60 Comprimidos' },
  { id: 6, name: 'Drenison 0,125mg/G' },
];

export const DISCOUNT_OPTIONS_MOCK = [
  { id: 1, name: 'Todos os Produtos' },
  { id: 2, name: 'Por Produto' },
];

export const CATEGORY_MOCK = [
  { id: 1, name: 'ANALGESICOS E ANTITERMICOS' },
  { id: 2, name: 'ANTI TABAGISMO' },
  { id: 3, name: 'PROTEÇÃO SOLAR' },
  { id: 4, name: 'COMPLEMENTOS ALIMENTARES' },
];
export const SUBCATEGORY_MOCK = [
  { id: 1, name: 'ANALGESICO', categoryId: 1 },
  { id: 2, name: 'ANTI TABAGISMO', categoryId: 2 },
  { id: 3, name: 'SOLAR MASSIVO CORPORAL', categoryId: 3 },
  { id: 4, name: 'CAIXA', categoryId: 4 },
];
export const SUPPLIER_MOCK = [
  { id: 1, name: 'FABRICANTE PAI 1' },
  { id: 2, name: 'FABRICANTE PAI 2' },
  { id: 3, name: 'FABRICANTE PAI 3' },
  { id: 4, name: 'FABRICANTE PAI 4' },
];
export const STATUS_MOCK = [
  { id: 1, name: 'Processando' },
  { id: 2, name: 'Finalizado' },
  { id: 3, name: 'Erro' },
];
export const CLASSIFICATION_MOCK = [
  {
    id: 1,
    name: 'BLACK FRIDAY',
    description: 'Classificação para as compras de black friday anual',
    active: true,
  },
  {
    id: 2,
    name: 'BONIFICAÇÃO',
    description: 'Classificação da bonificação',
    active: true,
  },
  {
    id: 3,
    name: 'COLABORATIVA',
    description: 'Classificação colaborativa',
    active: true,
  },
  {
    id: 4,
    name: 'COVID',
    description: 'Classificação para as compras relacioadas com o COVID',
    active: true,
  },
  {
    id: 5,
    name: 'E-COMMERCE',
    description: 'Classificação para demandas relacionadas ao e-commerce',
    active: true,
  },
  {
    id: 6,
    name: 'LANÇAMENTO',
    description: 'Classificação para novos lançamentos de produtos',
    active: true,
  },
];
export const SIMULATION_TYPE_MOCK = [{ id: 1, name: 'Antecipação' }];
export const ORDER_TYPE_MOCK = [
  { id: 1, name: 'Normal' },
  { id: 11, name: 'Negociação Regular' },
];
export const ACTIVE_MOCK = [
  { id: 1, name: 'Sim', description: 'Sim', active: true },
  { id: 2, name: 'Não', description: 'teste', active: false },
];
export const MOCK_DETAILING_OPTIONS = [
  {
    id: 1,
    items: [
      {
        id: 1,
        name: 'Somente itens inativos',
        inactive: false,
        details: 'inactive',
      },
    ],
  },
  {
    id: 2,
    items: [
      {
        id: 1,
        name: 'Desconsiderar promopacks',
        inactive: false,
        details: 'promoPacks',
      },
      {
        id: 2,
        name: 'Somente promopacks',
        inactive: false,
        details: 'promoPacks',
      },
    ],
  },
  {
    id: 3,
    items: [
      {
        id: 1,
        name: 'Desconsiderar cotados',
        inactive: true,
        details: 'quoted',
      },
      { id: 2, name: 'Somente cotados', inactive: true, details: 'quoted' },
    ],
  },
];
export const MOCK_NOTIFICATIONS_READ = [
  {

    id: 6,
    message: "Teste",
    notificationDate: "13/06/2022",
    keycloakUserId: "123-4fea",
    read: 0
  }
];
export const MOCK_NOTIFICATIONS = [
  {
    content: [
      {
            id: 121,
            message: {
                simulationStatus: "REPROVADO",
                simulationId: 3224,
                parentSupplierName: "MARCA PROPRIA",
                classification: {
                    id: 8,
                    name: "NEGOCIACAO"
                },
                completedBy: {
                    code: 123,
                    keycloakUserId: "123-4fea",
                    name: "purchase teste",
                    areaCode: null,
                    positionCode: null,
                    email: "email@example.com.br"
                },
                reason: "Teste"
            },
            notificationDate: "2022-05-17T18:17:40",
            keycloakUserId: "123-4fea",
            read: false
        },
        {
            id: 6,
            message: {
                simulationStatus: "REPROVADO",
                simulationId: 3225,
                parentSupplierName: "MARCA PROPRIA",
                classification: {
                    id: 8,
                    name: "NEGOCIACAO"
                },
                completedBy: {
                    code: 123,
                    keycloakUserId: "123-4fea",
                    name: "seconcisas",
                    areaCode: null,
                    positionCode: null,
                    email: null
                },
                reason: "Teste"
            },
            notificationDate: "2022-03-30T18:45:02",
            keycloakUserId: "123-4fea",
            read: true
        }
    ],
    pageable: {
        sort: {
            sorted: false,
            unsorted: true,
            empty: true
        },
        pageSize: 1,
        pageNumber: 2,
        offset: 2,
        unpaged: false,
        paged: true
    },
    totalElements: 2,
    totalPages: 1,
    last: true,
    sort: {
        sorted: false,
        unsorted: true,
        empty: true
    },
    first: false,
    number: 0,
    numberOfElements: 0,
    size: 2,
    empty: false
}
];

export const USERS_MOCK = [
    {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 1,
      email: "email@example.com.br",
      phoneNumber: 99999999999,
      accessProfile: 1,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: null,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: 2,
      activationStatus: false,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: null,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: 3,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 1,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: 1,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: 253211,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 1,
      email: "email@example.com.br",
      phoneNumber: "11982733000",
      accessProfile: 2,
      activationStatus: true,
      vacationReturnDate: "2022-01-01",
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 2,
      email: "cezar.filho@radixeng.com.br",
      phoneNumber: null,
      accessProfile: null,
      activationStatus: false,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 1,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: 3,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 1,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: 3,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: 2,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: 1,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
      registrationNumber: null,
      keycloakUserId: "123-4fea",
      firstName: "Andre",
      lastName: "Gomes",
      areaCode: null,
      email: "email@example.com.br",
      phoneNumber: null,
      accessProfile: null,
      activationStatus: true,
      vacationReturnDate: null,
      fullName: "Andre Gomes"
  },
  {
    registrationNumber: null,
    keycloakUserId: "123-4fea",
    firstName: "Andre",
    lastName: "Gomes",
    areaCode: null,
    email: "email@example.com.br",
    phoneNumber: null,
    accessProfile: null,
    activationStatus: true,
    vacationReturnDate: null,
    fullName: "Andre Gomes"
}
]

export const POSITIONS_MOCK = [
  {
    id: 0,
    name: 'Analista'
  },
  {
    id: 1,
    name: 'Adminstrador'
  },
  {
    id: 0,
    name: 'Farmacéutico'
  },
  {
    id: 0,
    name: 'Teste'
  },
];

export const BASIC = {
  cd: { ...PAGED_BASE, content: CD_MOCK },
  manufacturers: { ...PAGED_BASE, content: MANUFACTURER_MOCK },
  category: { ...PAGED_BASE, content: CATEGORY_MOCK },
  subcategory: { ...PAGED_BASE, content: SUBCATEGORY_MOCK },
  classification: { ...PAGED_BASE, content: CLASSIFICATION_MOCK },
  notificationMock: { ...PAGED_BASE, content: MOCK_NOTIFICATIONS },
  simulationType: { ...PAGED_BASE, content: SIMULATION_TYPE_MOCK },
  orderType: { ...PAGED_BASE, content: ORDER_TYPE_MOCK },
  paymentTerm: { ...PAGED_BASE, content: PAYMENT_TERM_MOCK },
  salesCalculation: { ...PAGED_BASE, content: SALES_CALCULATION_MOCK },
  typeProduct: { ...PAGED_BASE, content: TYPE_PRODUCT_MOCK },
  product: { ...PAGED_BASE, content: PRODUCTS_MOCK },
  active: { ...PAGED_BASE, content: ACTIVE_MOCK },
  optionsDetailing: { ...PAGED_BASE, content: MOCK_DETAILING_OPTIONS },
  supplier: { ...PAGED_BASE, content: SUPPLIER_MOCK },
  status: { ...PAGED_BASE, content: STATUS_MOCK },
  positions: {...PAGED_BASE, content: POSITIONS_MOCK},
  users: {...PAGED_BASE, content: USERS_MOCK}
};

export const MOCK_SIDEBAR = [
  {
    id: 1,
    name: 'Simulador de Pedidos',
    icon: 'pi-sliders-h',
    items: [
      {
        id: 1,
        name: 'Simular',
        url: '/simulador/simular',
        active: true,
        icon: 'pi-sliders-h',
      },
      {
        id: 2,
        name: 'Acompanhar Simulação',
        url: '/simulador/acompanhar',
        active: true,
        icon: 'pi-list',
      },
    ],
  },
  {
    id: 2,
    name: 'Aprovação | Envio',
    icon: 'pi-user',
    items: [
      {
        id: 3,
        name: 'Pendente de aprovação',
        url: '/alcada/pendentes',
        active: true,
        icon: 'pi-clock',
      },
      {
        id: 4,
        name: 'Aprovadas para envio',
        url: 'alcada/aprovadas',
        active: true,
        icon: 'pi-check-circle',
      },
    ],
  },
  {
    id: 3,
    name: 'Relatórios',
    icon: 'pi-file',
    items: [
      {
        id: 5,
        name: 'Relatório de Vendas',
        url: '/relatorio/vendas',
        active: true,
        icon: 'pi-dollar',
      },
    ],
  },
  {
    id: 4,
    name: 'Cadastro',
    icon: 'pi-cog',
    items: [
      {
        id: 8,
        name: 'Classificação',
        url: '/parametros/classificacao',
        active: true,
        icon: 'pi-tags',
      },
      {
        id: 9,
        name: 'Tempo de Expiração',
        url: '/parametros/expiracao',
        active: true,
        icon: 'pi-tags',
      },
      {
        id: 8,
        name: 'Usuários',
        url: '/parametros/cadastro/usuarios',
        active: true,
        icon: 'pi-tags',
      },
    ],
  },
];


/** Any object with key and values
 * ```typescript
 *  let myObject: AnyObject = {name: 'test'}; // works
 *  let myObject: AnyObject = {id: 1, items: [], complexObject: {name: 'TEST'}}; // works
 *  let wrongUsage: AnyObject = ''; // Will throw compile error as it is a string not an object
 *  let wrongUsage: AnyObject = 0; // Will throw compile error as it is a number not an object
 *  let wrongUsage: AnyObject = []; // Will throw compile error as it is an array not an object
 * ```
 */
type AnyObject = {
  [key: string]: any;
};
/**
 * Creates a mock with paging metadata and a content array filled with copies of the model param
 * use it to create a mock where a pages list is needed
 * ```typescript
 * let myMock = {name: 'tester', title: 'Mr'};
 * let pagedMock = MOCK_CUSTOM_PAGED(myMock, 10);
 * let pagedMockFiftyItems = MOCK_CUSTOM_PAGED(myMock, 50);
 *
 * let wrongUsage = MOCK_CUSTOM_PAGED('string test', 20); // will throw error as the first param only accepts objects
 * ```
 */
export function MOCK_CUSTOM_PAGED(model: AnyObject, times: number) {
  return {
    page: 0,
    number: 0,
    size: 25,
    numberOfElements: times,
    content: [...Array(times)].fill(0).map<any>((v, i) => ({ ...model, id: i })),
  };
}

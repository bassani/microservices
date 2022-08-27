/**
 * Modelo de request do front para consultas de search
 */
export interface ISearchRequest {
  /** Search requests must allways be GET */
  method: 'GET';

  /** Query params including paging and searching params */
  queryParams: { page: number; size: number; q: string };

  /** Search requests must have an url */
  url: string;
}

/**
 * Server response for a paged search request
 */
export interface ISearchResponse<T> {
  /** lista de itens resultado da busca */
  content: T[];
  /** number of the current page */
  page: number;
  /** amount of items per page */
  size: number;
  /** total amount of items in the query */
  numberOfElements: number;
}


/** Supplier
 * Interface para representar os fonecedores/fabr  icantes
 * 
*/
export interface ISupplier {
  /** numeric id of the suplier */
  id: number;
  /** name of the supplier */
  name: string;
  /** Id of the parent supplier */
  parentSupplierId: number;
}

export interface IDistributionCenter {
  id: number;
  name: string;
}

export interface IOrderType {
  id: number;
  name: string;
}

export interface ISimulationType {
  key: string;
  id: number;
  title: string;
  description: string;
  icon: string;
}

export interface IProductCategory {
  id: number;
  name: string;
}

export interface IProduct {
  id: number;
  name: string;
}

export interface IActive {
  id: number;
  name: string;
  description: string;
  active: boolean;
}
export interface IDetailingDialog {
  id: number;
  name: string;
  items: any[];
  active: boolean;
}

/**
 * Interface extendendo a interface base de ISearchResultItem
 */
export interface IProductSubcategory extends ISearchResultItem {
  categoryId: number;
}

/**
 * Interface base para as respostas de pesquisas
 */
export interface ISearchResultItem {
  id: number;
  name: string;
}

/**
 * Payment term object interface
 * represents the payment terms, for example 1 payment in 60 days, or 2 installments paid every 30 days
 *  @todo should extend a idName interface
 */
export interface IPaymentTermItem {
  id: number;
  conditionPaymentDescription: string;
  daysQuantityPayment: string;
}

/**
 * Interface to represent the calculations basis
 *  @todo should extend a idName interface and update its name to represent its purpose
 */
export interface ISalesCalculationItem {
      name: string;
      id: number;
      /** Options list object for the cascade component to understand */
      options?: {name: string, value: number, parent: number}[];
}

/**
 * Used represent the product type in filters, ex inactives, promopacks and such
 * @todo interface name should be updated to reflect its purpose and removed where it is used wrongly
 */
export interface ITypeProductsItem {
  id: number;
  name: string;
}

export interface IPaymentTerm {
  newPaymentTermCode: number;
  newPaymentTermDescription: string;
  daysQuantityPayment: number;

}

export interface IDiscountOptionsItem {
  id: number;
  name: string;
}

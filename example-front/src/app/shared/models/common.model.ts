export interface IPaged<T> {
  number: number;
  page: number;
  size: number;
  numberOfElements: number;
  content: T[];
  status?: string;
  totalElements?: number;
}

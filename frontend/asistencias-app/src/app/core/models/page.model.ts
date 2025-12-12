//Para cualquiera que quiera paginar
export interface Page<T>{
  content :[];
  totalElements: number;
  size:number;
  number:number;
  first:boolean;
  last:boolean;
  empty:boolean;
}

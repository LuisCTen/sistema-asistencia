export interface HistorialResponse {
  idAsistencia: number;
  fecAsistencia:string;
  horaEntrada:string;
  horaSalida:string |null;
  estadoAsistencia:string;
}

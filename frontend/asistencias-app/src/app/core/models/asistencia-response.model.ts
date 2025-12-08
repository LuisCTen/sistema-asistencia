export interface AsistenciaResponse{

  mensaje: string;
  idAsistencia?:number;
  idUsuario:number;
  nombreUsuario?:string;
  fecAsistencia?:string;
  horaEntrada?:string;
  horaSalida?:string;
  estadoAsistencia?:string;
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface JustificacionPendiente {
  idJustificacion: number;
  nombreEmpleado: string;
  fechaIncidencia: string;
  motivo: string;
  fechaSolicitud: string;
}

export interface RespuestaAdmin {
  mensaje: string;
}

@Injectable({ providedIn: 'root' })
export class AdminJustificacionService {
  private readonly URL = environment.api;

  constructor(private http: HttpClient) {}

  //Listar Pendientes (GET)
  listarPendientes(): Observable<JustificacionPendiente[]> {
    return this.http.get<JustificacionPendiente[]>(
      `${this.URL}/justificaciones/pendientes`
    );
  }

  // Atender Solicitud (PATCH)
  atender(
    id: number,
    estado: 'APROBADO' | 'RECHAZADO'
  ): Observable<RespuestaAdmin> {
    return this.http.patch<RespuestaAdmin>(
      `${this.URL}/justificaciones/${id}/atender`,
      { estado }
    );
  }
}

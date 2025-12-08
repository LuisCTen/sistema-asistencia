import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AsistenciaResponse } from 'src/app/core/models/asistencia-response.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AsistenciaService {
  private readonly URL = environment.api; //localhost:8080

  constructor(private http: HttpClient) {}

  registrarEntrada(): Observable<AsistenciaResponse> {
    return this.http.post<AsistenciaResponse>(
      `${this.URL}/asistencias/marcar-asistencia`,
      {}
    );
  }

  registrarSalida(): Observable<AsistenciaResponse> {
    return this.http.post<AsistenciaResponse>(
      `${this.URL}/asistencias/marcar-salida`,
      {}
    );
  }
}

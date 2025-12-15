import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface DashboardStats {
  totalEmpleados: number;
  asistenciasHoy: number;
  tardanzasHoy: number;
  justificacionesPendientes: number;
}

@Injectable({ providedIn: 'root' })
export class DashboardKpiService {
  private readonly URL = environment.api;
  constructor(private http: HttpClient) {}

  getStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(`${this.URL}/dashboard/stats`);
  }
}

import { Component, OnInit } from '@angular/core';
import { AsistenciaResponse } from 'src/app/core/models/asistencia-response.model';
import { AsistenciaService } from '../../services/asistencia.service';
import {
  DashboardKpiService,
  DashboardStats,
} from '../../services/dashboard-kpi.service';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css'],
})
export class DashboardPageComponent implements OnInit {
  usuario = { name: 'Admin Indra' };
  ultimaRespuesta: AsistenciaResponse | null = null;
  isError: boolean = false;

  // --- NUEVAS VARIABLES PARA ADMIN ---
  rol: string = '';
  isAdmin: boolean = false;
  stats: DashboardStats | null = null;

  constructor(
    private asistenciaService: AsistenciaService,
    private kpiService: DashboardKpiService // Inyectamos el servicio de KPIs
  ) {}

  // --- NUEVA LÓGICA DE INICIALIZACIÓN ---
  ngOnInit(): void {
    // 1. Obtener usuario y rol del Storage
    this.usuario.name = localStorage.getItem('username') || 'Usuario';
    this.rol = localStorage.getItem('rol') || '';

    // 2. Validar si es ADMIN
    this.isAdmin = this.rol === 'ROLE_ADMIN' || this.rol === 'ADMIN';

    // 3. Si es Admin, cargar estadísticas
    if (this.isAdmin) {
      this.cargarKpis();
    }
  }

  cargarKpis() {
    this.kpiService.getStats().subscribe({
      next: (data) => (this.stats = data),
      error: (err) => console.error('Error cargando KPIs', err),
    });
  }

  // --- MÉTODOS EXISTENTES (EMPLEADO) ---
  marcarEntrada() {
    this.asistenciaService.registrarEntrada().subscribe({
      next: (res) => this.procesarExito(res),
      error: (err) => this.procesarError(err),
    });
  }

  marcarSalida() {
    this.asistenciaService.registrarSalida().subscribe({
      next: (res) => this.procesarExito(res),
      error: (err) => this.procesarError(err),
    });
  }

  //------------------------------------------------------------------
  procesarError(err: any) {
    this.isError = true;

    const mensajeError = err.error?.mensaje || 'Ocurrio un error desconocido';

    this.ultimaRespuesta = {
      mensaje: mensajeError,
      idUsuario: 0,
      fecAsistencia: '',
    };
  }

  private procesarExito(res: AsistenciaResponse) {
    this.isError = false;
    this.ultimaRespuesta = res;

    if (res.mensaje.toUpperCase().includes('Ya existe')) {
      this.isError = true;
    }
  }
}

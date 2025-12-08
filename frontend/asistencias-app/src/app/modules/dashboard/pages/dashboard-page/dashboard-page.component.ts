import { Component } from '@angular/core';
import { AsistenciaResponse } from 'src/app/core/models/asistencia-response.model';
import { AsistenciaService } from '../../services/asistencia.service';

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrls: ['./dashboard-page.component.css'],
})
export class DashboardPageComponent {
  usuario = { name: 'Admin Indra' };
  ultimaRespuesta: AsistenciaResponse | null = null;
  isError: boolean = false;

  constructor(private asistenciaService: AsistenciaService) {}
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

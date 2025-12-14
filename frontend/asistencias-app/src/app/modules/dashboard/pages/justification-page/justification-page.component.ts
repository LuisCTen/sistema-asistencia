import { Component, OnInit } from '@angular/core';
import { JustificacionRequest } from 'src/app/core/models/justificacion.model';
import { JustificacionService } from '../../services/justificacion.service';

@Component({
  selector: 'app-justification-page',
  templateUrl: './justification-page.component.html',
  styleUrls: ['./justification-page.component.css'],
})
export class JustificationPageComponent implements OnInit {
  solicitud: JustificacionRequest = {
    fechaIncidencia: '',
    motivo: '',
  };

  mensajeResultado: string = '';
  esError: boolean = false;
  cargando: boolean = false;

  listaIncidencias: any[] = [];

  constructor(private justificacionService: JustificacionService) {}

  ngOnInit(): void {
    this.cargarIncidencias();
  }

  cargarIncidencias() {
    this.justificacionService.getIncidencias().subscribe({
      next: (data) => (this.listaIncidencias = data),
      error: (err) => console.error('Error cargando incidencias', err),
    });
  }
  enviarSolicitud() {
    this.cargando = true;
    this.mensajeResultado = '';

    this.justificacionService.registrarJustificacion(this.solicitud).subscribe({
      next: (resp) => {
        this.cargando = false;
        this.esError = false;
        this.mensajeResultado = resp.mensaje;

        if (!resp.mensaje.startsWith('ERROR')) {
          this.solicitud = { fechaIncidencia: '', motivo: '' }; //limpiamos el form
        } else {
          this.esError = true;
        }
      },
      error: (err) => {
        this.cargando = false;
        this.esError = true;
        if (err.error && err.error.mensaje) {
          this.mensajeResultado = err.error.mensaje;
        } else {
          this.mensajeResultado = 'Error de conexion con el servidor';
        }
      },
    });
  }
}

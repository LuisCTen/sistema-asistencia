import { Component, OnInit } from '@angular/core';
import {
  AdminJustificacionService,
  JustificacionPendiente,
} from '../../services/admin-justificacion.service';

@Component({
  selector: 'app-admin-inbox-page',
  templateUrl: './admin-inbox-page.component.html',
  styleUrls: ['./admin-inbox-page.component.css'],
})
export class AdminInboxPageComponent implements OnInit {
  pendientes: JustificacionPendiente[] = [];
  cargando: boolean = false;
  mensajeFeedback: string = '';

  constructor(private adminService: AdminJustificacionService) {}

  ngOnInit(): void {
    this.cargarPendientes();
  }

  cargarPendientes() {
    this.cargando = true;
    this.adminService.listarPendientes().subscribe({
      next: (data) => {
        this.pendientes = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      },
    });
  }

  atender(id: number, estado: 'APROBADO' | 'RECHAZADO') {
    if (!confirm(`¿Estás seguro de marcar como ${estado}?`)) return;

    this.adminService.atender(id, estado).subscribe({
      next: (res) => {
        this.mensajeFeedback = res.mensaje;
        this.cargarPendientes(); // Recarga la lista para que desaparezca la fila que fue atendida

        // Limpiar mensaje después de 3 seg
        setTimeout(() => (this.mensajeFeedback = ''), 3000);
      },
      error: (err) => alert('Error al procesar: ' + err.error?.mensaje),
    });
  }
}

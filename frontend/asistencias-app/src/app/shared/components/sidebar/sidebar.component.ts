import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
})
export class SidebarComponent implements OnInit {
  // Estructura del proyecto modelo
  mainMenu: { defaultOptions: Array<any>; accessLink: Array<any> } = {
    defaultOptions: [],
    accessLink: [],
  };

  username: string = '';
  rol: string = '';

  constructor() {}

  ngOnInit(): void {
    //  Obtener datos del usuario
    this.rol = localStorage.getItem('rol') || '';

    // Definir menús según el Rol
    if (this.rol === 'ROLE_ADMIN' || this.rol === 'ADMIN') {
      // --- MENÚ ADMINISTRADOR ---
      this.mainMenu.defaultOptions = [
        {
          name: 'Dashboard',
          icon: 'uil uil-estate',
          router: ['/', 'dashboard'], // Pantalla principal
        },
        {
          name: 'Gestión Personal', // CRUD Usuarios
          icon: 'uil uil-users-alt',
          router: ['/', 'dashboard', 'admin', 'users'],
        },
        {
          name: 'Aprobaciones', // Bandeja de Justificaciones
          icon: 'uil uil-check-circle',
          router: ['/', 'dashboard', 'admin', 'inbox'],
        },
        {
          name: 'Historial de asistencias',
          icon: 'uil uil-analytics',
          router: ['/', 'dashboard', 'history'], // Reutilizamos historial
        },
      ];
    } else {
      // --- MENÚ EMPLEADO ---
      this.mainMenu.defaultOptions = [
        {
          name: 'Marcar Asistencia',
          icon: 'uil uil-clock',
          router: ['/', 'dashboard'],
        },
        {
          name: 'Mis Asistencias',
          icon: 'uil uil-history',
          router: ['/', 'dashboard', 'history'],
        },
        {
          name: 'Generar Justificacion',
          icon: 'uil uil-comment-alt-exclamation',
          router: ['/', 'dashboard', 'justification'],
        },
      ];
    }
  }
}

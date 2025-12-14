import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../services/usuario.service';
import { UserResponse } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-users-page',
  templateUrl: './users-page.component.html',
  styleUrls: ['./users-page.component.css'],
})
export class UsersPageComponent implements OnInit {
  usuarios: UserResponse[] = [];
  cargando: boolean = false;
  // Variables para controlar el formulario
  mostrarFormulario: boolean = false;
  usuarioSeleccionado: UserResponse | null = null;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.cargando = true;
    this.usuarioService.getAll().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar usuarios', err);
        this.cargando = false;
      },
    });
  }

  eliminar(user: UserResponse) {
    if (!confirm(`¿Estás seguro de dar de baja a ${user.username}?`)) return;

    this.usuarioService.delete(user.idUsuario).subscribe({
      next: (res) => {
        // Recargar toda la lista
        // this.cargarUsuarios();

        //  Actualizar solo la fila en memoria
        user.estado = 0;
        alert('Usuario dado de baja correctamente');
      },
      error: (err) => alert('Error al eliminar: ' + err.message),
    });
  }

  // Placeholders para el siguiente paso (Modal)
  abrirModalCrear() {
    console.log('Abrir modal crear');
  }

  abrirModalEditar(user: UserResponse) {
    console.log('Abrir modal editar', user);
  }
  // Abrir para CREAR
  abrirFormularioCrear() {
    this.usuarioSeleccionado = null; // Null indica creación
    this.mostrarFormulario = true;
  }

  // Abrir para EDITAR
  abrirFormularioEditar(usuario: UserResponse) {
    this.usuarioSeleccionado = usuario; // Pasamos el objeto a editar
    this.mostrarFormulario = true;
  }

  // Cerrar el formulario
  cerrarFormulario(seGuardoDatos: boolean) {
    this.mostrarFormulario = false;
    if (seGuardoDatos) {
      this.cargarUsuarios(); // Refrescamos la tabla solo si hubo cambios
    }
  }
  activar(user: UserResponse) {
    if (!confirm(`¿Deseas reactivar al usuario ${user.username}?`)) return;

    // Para enviar al Update (UserRequest)
    //  mapear de Response a Request
    const requestActivacion = {
      username: user.username,
      email: user.email,
      nombreCompleto: user.nombreCompleto,
      idRol: user.nombreRol === 'ADMIN' ? 1 : 2,
      estado: 1,
      password: '',
    };

    this.usuarioService.update(user.idUsuario, requestActivacion).subscribe({
      next: () => {
        user.estado = 1;
        alert('Usuario reactivado correctamente ');
      },
      error: (err) => alert('Error al activar: ' + err.message),
    });
  }
}

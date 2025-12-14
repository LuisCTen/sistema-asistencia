import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserRequest, UserResponse } from 'src/app/core/models/user.model';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit {
  @Input() userToEdit: UserResponse | null = null; // Si llega data, es Edición
  @Output() onClose = new EventEmitter<boolean>(); // true = se guardó algo, false = cancelar

  // Modelo para mi formulario
  userForm: UserRequest = {
    username: '',
    password: '',
    email: '',
    nombreCompleto: '',
    idRol: 2, // 2 = Empleado por defecto
    estado: 1,
  };

  titulo: string = 'Nuevo Empleado';
  cargando: boolean = false;
  mensajeError: string = '';

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    // Si recibimos un usuario, llenamos el formulario (Modo Edición)
    if (this.userToEdit) {
      this.titulo = 'Editar Empleado';
      this.userForm = {
        username: this.userToEdit.username,
        email: this.userToEdit.email,
        nombreCompleto: this.userToEdit.nombreCompleto,
        // Detectamos el rol por el nombre que viene del DTO
        idRol: this.userToEdit.nombreRol === 'ADMIN' ? 1 : 2,
        estado: this.userToEdit.estado,
        password: '', // se deja vacía para no cambiarla accidentalmente
      };
    }
  }

  guardar() {
    this.cargando = true;
    this.mensajeError = '';

    if (this.userToEdit) {
      // --- EDITAR ---
      this.usuarioService
        .update(this.userToEdit.idUsuario, this.userForm)
        .subscribe({
          next: () => this.cerrar(true),
          error: (err) => this.manejarError(err),
        });
    } else {
      // --- CREAR ---
      this.usuarioService.create(this.userForm).subscribe({
        next: () => this.cerrar(true),
        error: (err) => this.manejarError(err),
      });
    }
  }

  cerrar(seGuardo: boolean = false) {
    this.onClose.emit(seGuardo);
  }

  private manejarError(err: any) {
    this.cargando = false;
    this.mensajeError =
      err.error?.mensaje || 'Ocurrió un error al procesar la solicitud.';
  }
}
